package cn.hexing.ami.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import sense.com.EliteIV;
import sense.com.EliteIVConstants;
import sense.com.S4OPENINFO;
import sense.com.SENSE4_CONTEXT;
import sense.com.SWIGTYPE_p_unsigned_long;
import sense.com.ucharArray;

import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

public class HardwareUtil {
	private static Logger logger = Logger.getLogger(HardwareUtil.class
			.getName());

	/**
	 * 通过获取资源的形式将驱动文件加载到操作系统的某个目录
	 * 1.通过getResource()函数获取Jar中的驱动文件
	 * 2.通过File的方式新建驱动文件，将文件数据写入该文件
	 * 3.通过正常的System.loadLibrary()方式正常的载入驱动
	 */
	static {
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		// 获取JDK版本位数
		String arch = System.getProperty("sun.arch.data.model");
		// 操作系统名称
		String osName = System.getProperty("os.name");

		// 动态链接库名称
		String libName = "Sense";
		if (osName.toLowerCase().indexOf("windows") != -1) {
			libName += arch + ".dll";
		} else {
			libName = "lib" + libName + arch + ".so";
		}

		try {
			// 获取系统类路径
			String libPath = System.getProperty("java.library.path");
			if (StringUtil.isEmptyString(libPath)) {
				logger.error("Get the classpath fail!");
				throw new RuntimeException("java.library.path is null!");
			}
			// 默认取第一个类路径
			StringTokenizer st = new StringTokenizer(libPath,
					System.getProperty("path.separator"));
			String path = st.nextToken();
			// 获取动态链接库源文件
			URI uri = new URI(HardwareUtil.class.getResource("/res/" + libName)
					.getFile());
			inputStream = new FileInputStream(uri.getPath());
			// 将动态链接库源文件存放到类路径
			File dllFile = new File(new File(path), libName);
			if (!dllFile.exists()) {
				outputStream = new FileOutputStream(dllFile);
				byte[] array = new byte[8192];
				for (int i = inputStream.read(array); i != -1; i = inputStream
						.read(array)) {
					outputStream.write(array, 0, i);
				}
			}
		} catch (Exception e) {
			logger.error("Load " + libName + " fail:" + e.getMessage());
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				logger.error("File " + libName + " inputStream close fail:"
						+ e.getMessage());
			}
			try {
				if (outputStream != null)
					outputStream.close();
			} catch (IOException e) {
				logger.error("File " + libName + " outputStream close fail:"
						+ e.getMessage());
			}
		}
	}

	/**
	 * 获取硬件信息
	 * @return
	 */
	public static Map<String, String> getHardwareInfo() {
		Map<String, String> info = new HashMap<String, String>();
		String softDogId = "";
		//操作系统名称
		String osName = System.getProperty("os.name");
		//windows操作系统
		if (osName.toLowerCase().indexOf("windows") != -1) {
			// 获取JDK版本位数
			String arch = System.getProperty("sun.arch.data.model");
			//加载动态链接库
			HardwareJna INSTANCE = (HardwareJna) Native.loadLibrary("Sense" + arch, HardwareJna.class);

			//1获取加密狗设备
			HardwareJna.SENSE4_CONTEXT ctxList[] = new HardwareJna.SENSE4_CONTEXT[10];
			for (int i = 0; i < ctxList.length; i++)
				ctxList[i] = new HardwareJna.SENSE4_CONTEXT();
			IntByReference pSize = new IntByReference(0);
			int ret = INSTANCE.S4Enum(ctxList[0], pSize);
			ret = INSTANCE.S4Enum(ctxList[0], pSize);
			if (INSTANCE.S4_SUCCESS != ret) {
				logger.error("SoftDog get failed!");
				return info;
			}

			//2打开加密狗
			HardwareJna.SENSE4_CONTEXT ctx = ctxList[0];
			ret = INSTANCE.S4Open(ctx);
			if (INSTANCE.S4_SUCCESS != ret) {
				logger.error("SoftDog open failed!");
				INSTANCE.S4Close(ctx);
				return info;
			}
			
			//3获取加密狗的序列号
			byte[] lpOutBuffer = new byte[20];
			ret = INSTANCE.S4Control(ctx, HardwareJna.S4_GET_SERIAL_NUMBER, null,0, lpOutBuffer, 8, pSize);
			if (INSTANCE.S4_SUCCESS != ret){
				logger.error("SoftDog get serial number failed!");
				INSTANCE.S4Close(ctx);
				return info;
			}else{
				for (int i = 0; i < 8; i++) {
					softDogId += Integer.toString((lpOutBuffer[i] & 0xff) + 0x100, 16).substring(1);
				}
			}
			
			//4关闭加密狗
			ret = INSTANCE.S4Close(ctx);
			if (INSTANCE.S4_SUCCESS != ret) {
				logger.error("SoftDog close failed!");
				INSTANCE.S4Close(ctx);
				return info;
			}
		} else {
			// 获取JDK版本位数
			String arch = System.getProperty("sun.arch.data.model");
			//加载动态链接库
			System.loadLibrary("Sense" + arch);
			SWIGTYPE_p_unsigned_long size = EliteIV.new_ulongp();
			long ret = 0;
			SENSE4_CONTEXT s4_context = new SENSE4_CONTEXT();
			//1获取加密狗设备
			ret = EliteIV.S4Enum(null, size);
			ret = EliteIV.S4Enum(s4_context, size);
			if (EliteIVConstants.S4_SUCCESS != ret) {
				logger.error("SoftDog get failed!");
				return info;
			}
			
			//2打开加密狗
			S4OPENINFO s4_OpenInfo = new S4OPENINFO();
			s4_OpenInfo.setDwS4OpenInfoSize(16);
			s4_OpenInfo.setDwShareMode(EliteIV.S4_EXCLUSIZE_MODE);
			ret = EliteIV.S4OpenEx(s4_context, s4_OpenInfo);
			if (EliteIVConstants.S4_SUCCESS != ret) {
				EliteIV.S4Close(s4_context);
				logger.error("SoftDog open failed!" + ret);
				return info;
			}

			//3获取加密狗的序列号
			SWIGTYPE_p_unsigned_long lpBytesReturned = EliteIV.new_ulongp();
			ucharArray outbuff = new ucharArray(8);
			byte[] serial = new byte[8];
			ret = EliteIV.S4Control(s4_context, EliteIV.S4_GET_SERIAL_NUMBER, null,
					0, EliteIV.ucharp_to_voidp(outbuff.cast()), 8, lpBytesReturned);
			if (EliteIVConstants.S4_SUCCESS != ret) {
				logger.error("SoftDog get serial number failed!");
				EliteIV.S4Close(s4_context);
				return info;
			} else {
				for (int i = 0; i < 8; i++) {
					serial[i] = (byte) outbuff.getitem(i);
				}
				softDogId = bytesToHex(serial);
			}
			
			//4关闭加密狗
			ret = EliteIV.S4Close(s4_context);
			if (EliteIVConstants.S4_SUCCESS != ret) {
				logger.error("SoftDog close failed!");
				return info;
			}
		}
		
		info.put("softDogId", softDogId);
		return info;
	}

	/**
	 * 保存成文件
	 * 
	 * @param path
	 * @throws Exception
	 */
	public static void saveFile(Map<String, String> info) throws Exception {
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("utf-8");
		Element root = document.addElement("machine");
		for (String key : info.keySet()) {
			root.addElement(key).addText(info.get(key));
		}
		// 格式化输出格式
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
		format.setTrimText(false);
		// 格式化输出流
		XMLWriter xmlWriter = new XMLWriter(new FileWriter("HardwareInfo.xml"),
				format);
		// 将document写入到输出流
		xmlWriter.write(document);
		xmlWriter.close();
	}
	
	private static String bytesToHex(final byte[] buf) {
		if (buf == null)
			return null;
		String res = "";
		for (int i = 0; i < buf.length; i++) {
			int b = 0x000000FF & buf[i];
			String hex = Integer.toString(b, 16);
			if (hex.length() < 2)
				res += "0";
			res += hex;
		}
		return res;
	}
}
