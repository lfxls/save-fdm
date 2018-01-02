package cn.hexing.ami.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

/**
 * 文件操作工具类
 */
public class FileUtil {
	private static Logger logger = Logger.getLogger(FileUtil.class.getName());
    /**
     * 创建目录。如果父目录不存在，将创建所有父目录
     * @param path 路径名
     * @return 目录对象
     */
    public static File mkdirs(String path) {
        File dir = new File(path);
        if (dir.isFile()) {
            throw new IllegalArgumentException(path + " is not a directory");
        }
        
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        return dir;
    }
    
    /**
     * 打开文件。如果文件不存在，则创建之
     * @param path 文件所在目录
     * @param fileName 文件名
     * @return 文件对象
     */
    public static File openFile(String path, String fileName) {
        File dir = mkdirs(path);
        File file = new File(dir, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException ex) {
                throw new RuntimeException("Error to open file: " + fileName, ex);
            }
        }
        
        return file;
    }
    
    /**
     * 删除文件
     * @param path 文件所在路径
     * @param fileName 文件名
     */
    public static void deleteFile(String path, String fileName) {
        File file = new File(path, fileName);
        if (file.exists()) {
            file.delete();
        }
    }
    
    /**
     * 取得目录的绝对路径名。如果传入的路径是相对路径，则把用户的当前目录作为其父目录 
     * @param path 路径名。可能是绝对路径或相对路径
     * @return 绝对路径名
     */
    public static String getAbsolutePath(String path) {
        File f = new File(path);
        return f.getAbsolutePath();
    }
    
    /**
     * 取得文件的绝对路径名
     * @param path 文件的存放路径
     * @param fileName 文件名
     * @return 文件的绝对路径名
     */
    public static String getAbsolutePath(String path, String fileName) {
        File dir = mkdirs(getAbsolutePath(path));
        File file = new File(dir, fileName);
        return file.getAbsolutePath();
    }
    
    /**
     * 格式化目录(将目录中的//符号替换成/,如果不是以/结尾则在尾部添加/,如果目录不存在则创建)
     * @param path 源目录
     * @return 格式化后的目录
     */
    public static String formatFilePath(String path) { //转换成尾部为/
      String s = StringUtil.replaceStr(path, "//", "/");
      mkDirs(path);
      File file = new File(s);
      if (s.endsWith("/")) {
        return s.substring(0, s.length() - 1) + "/";
      }
      if ( (file.isDirectory()) && (!s.endsWith("/"))) {
        return s + "/";
      }
      return s;
    }
    
    /**
     * 创建目标目录
     * @param str 目标目录(带绝对路径)
     */
    public static void mkDirs(String str) {
    	//FileUtil在写文件时,如果文件目录名中带有”.”号出现错误的问题
    	String strFile = StringUtil.replaceStr(str, "//", "/");//兼容UNIX
    	File file = new File(strFile);
    	file.mkdirs();
    }
    
    /**
     * 删除文件或目录
     * @param name 文件或目录
     * @return 删除成功返回true,失败返回false
     */
    public static boolean deleteFile(String name) {
      return deleteFile(new File(name));
    }

    /**
     * 删除文件或目录,如果不存在则返回true
     * @param f 文件或目录对象
     * @return 删除成功返回true,失败返回false
     */
    public static boolean deleteFile(File f) {
      if (!f.exists()) {
        return true;
      }
      if (f.isDirectory()) {
        String[] children = f.list();
        for (int i = 0; i < children.length; i++) {
          boolean success = deleteFile(new File(f, children[i]));
          if (!success) {
            return false;
          }
        }
      }
      // The directory is now empty so delete it
      return f.delete();
    }

    
    /**
     * 解压缩文件到目标目录(注意:目前只支持一个或多个文件的压缩包解压缩不支持有子目录的情况)
     * @param zipFileName 压缩文件
     * @param destPath 目标目录
     * @return 如果解压缩成功则返回true,失败返回false
     */
    public static boolean unzipFile(String zipFileName, String destPath) {
      boolean result = true;
      destPath = formatFilePath(destPath);
      int BUFFER = 2048;
      try {
        BufferedOutputStream dest = null;
        BufferedInputStream is = null;
        ZipEntry entry;
        ZipFile zipfile = new ZipFile(zipFileName);
        Enumeration e = zipfile.entries();
        while (e.hasMoreElements()) {
          entry = (ZipEntry) e.nextElement();
          if(entry.isDirectory()){
          	File tmp = new File(destPath + entry.getName());
          	tmp.mkdir();
          	continue;
          }
          
          is = new BufferedInputStream
              (zipfile.getInputStream(entry));
          int count;
          byte data[] = new byte[BUFFER];
          FileOutputStream fos = new
              FileOutputStream(destPath + entry.getName());
          dest = new
              BufferedOutputStream(fos, BUFFER);
          while ( (count = is.read(data, 0, BUFFER))
                 != -1) {
            dest.write(data, 0, count);
          }
          dest.flush();
          dest.close();
          is.close();
        }
        zipfile.close();
      }
      catch (Exception ex) {
        result = false;
        logger.error(StringUtil.getExceptionDetailInfo(ex));
      }
      return result;
    }

    /**
     * 解压缩文件到目标目录,支持更低版本的zip文件的解压缩(注意:目前只支持一个或多个文件的压缩包解压缩不支持有子目录的情况)
     * @param zipFileName 压缩文件
     * @param destPath 目标目录
     * @return 如果解压缩成功则返回true,失败返回false
     */
    public static boolean unzipFile2(String zipFileName, String destPath) {
      try {
        destPath = formatFilePath(destPath);
        BufferedInputStream is;
        FileInputStream dest = new
            FileInputStream(zipFileName);
        ZipInputStream ip = new ZipInputStream(new BufferedInputStream(dest));
        ZipEntry entry = ip.getNextEntry();
        while (entry != null) {
          int count;
          byte data[] = new byte[2048];
          FileOutputStream fos = new
              FileOutputStream(destPath + entry.getName());
          while ( (count = ip.read(data, 0, 2048))
                 != -1) {
            fos.write(data, 0, count);
          }
          fos.flush();
          fos.close();
          entry = ip.getNextEntry();
        }

        ip.close();
        return true;
      }
      catch (Exception e) {
        logger.error(StringUtil.getExceptionDetailInfo(e));
        return false;
      }
    }

    /**
     * 压缩目录下的所有文件和子目录为一个ZIP文件,这里ZIP文件中的中文文件名为乱码(注意:目前只支持源目录下有一个或多个文件的压缩不支持还含有子目录的情况)
     * @param zipFileName 目标zip文件
     * @param souPath 源目录
     * @return 压缩成功返回true,失败返回false
     */
    public static boolean zipFile(String zipFileName, String souPath) {
      boolean result = true;
      FileOutputStream dest=null;
      ZipOutputStream out=null;
      try {
        souPath = formatFilePath(souPath);
        File destFile = new File(zipFileName);
        if (destFile.exists()) {
          deleteFile(zipFileName);
        }
        int BUFFER = 2048;
        BufferedInputStream origin = null;
        //out.setMethod(ZipOutputStream.DEFLATED);
        byte data[] = new byte[BUFFER];
        // get a list of files from current directory
        File f = new File(souPath);
        if (f.isDirectory()) {//传递入的是路径
          String files[] = f.list();
           dest = new FileOutputStream(zipFileName);
           out = new ZipOutputStream(new BufferedOutputStream(dest));
          for (int i = 0; i < files.length; i++) {
            FileInputStream fi = new
                FileInputStream(souPath + files[i]);
            origin = new
                BufferedInputStream(fi, BUFFER);
            ZipEntry entry = new ZipEntry(files[i]);
            out.putNextEntry(entry);
            int count;
            while ( (count = origin.read(data, 0,
                                         BUFFER)) != -1) {
              out.write(data, 0, count);
            }
            out.flush();
            origin.close();
          }
        }else{//是具体的文件
          FileInputStream fi = new
              FileInputStream(souPath);
          origin = new
              BufferedInputStream(fi, BUFFER);
          ZipEntry entry = new ZipEntry(f.getName());
          out.putNextEntry(entry);
          int count;
          while ( (count = origin.read(data, 0,
                                       BUFFER)) != -1) {
            out.write(data, 0, count);
          }
          out.flush();
          origin.close();
        }

        out.close();
      }
      catch (Exception e) {
        result = false;
        logger.error(StringUtil.getExceptionDetailInfo(e));
      }
      return result;
    }

    /**
     * 压缩目录为字节数组,这里ZIP文件中的中文文件名为乱码(注意:目前只支持源目录下有一个或多个文件的压缩不支持还含有子目录的情况)
     * @param souPath 源目录
     * @return 压缩后的字节流
     */
    public static byte[] zipFile(String souPath) {
      try {
        souPath = formatFilePath(souPath);
        int BUFFER = 2048;
        BufferedInputStream origin = null;
        ByteArrayOutputStream dest = new ByteArrayOutputStream();
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
        //out.setMethod(ZipOutputStream.DEFLATED);
        byte data[] = new byte[BUFFER];
        // get a list of files from current directory
        File f = new File(souPath);
        if (f.isDirectory()) {//传递入的是路径
          String files[] = f.list();
          for (int i = 0; i < files.length; i++) {
            FileInputStream fi = new  FileInputStream(souPath + files[i]);
            origin = new BufferedInputStream(fi, BUFFER);
            ZipEntry entry = new ZipEntry(files[i]);
            out.putNextEntry(entry);
            int count;
            while ( (count = origin.read(data, 0,  BUFFER)) != -1) {
              out.write(data, 0, count);
            }
            out.flush();
            origin.close();
          }
        }else{//是具体的文件
          FileInputStream fi = new
              FileInputStream(souPath);
          origin = new
              BufferedInputStream(fi, BUFFER);
          ZipEntry entry = new ZipEntry(f.getName());
          out.putNextEntry(entry);
          int count;
          while ( (count = origin.read(data, 0,
                                       BUFFER)) != -1) {
            out.write(data, 0, count);
          }
          out.flush();
          origin.close();
        }

        out.close();
        return dest.toByteArray();
      }
      catch (Exception e) {
        logger.error(StringUtil.getExceptionDetailInfo(e));
      }
      return null;
    }

    /**
     * 压缩文件列表为字节数组,这里ZIP文件中的中文文件名为乱码
     * @param souPath 文件列表(多个文件以ArrayList对象传入)
     * @return 压缩后的字节流
     */
    public static byte[] zipFile(ArrayList souPath) {
      try {
        int BUFFER = 2048;
        BufferedInputStream origin = null;
        ByteArrayOutputStream dest = new ByteArrayOutputStream();
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
        //out.setMethod(ZipOutputStream.DEFLATED);
        byte data[] = new byte[BUFFER];
        // get a list of files from current directory
        for (int i = 0; i < souPath.size(); i++) {
          String fileName = (String)souPath.get(i);
          //fileName = formatFilePath(fileName);
          File f = new File(fileName);

            FileInputStream fi = new
                FileInputStream(fileName);
            origin = new
                BufferedInputStream(fi, BUFFER);
            ZipEntry entry = new ZipEntry(f.getName());
            out.putNextEntry(entry);
            int count;
            while ( (count = origin.read(data, 0,
                                         BUFFER)) != -1) {
              out.write(data, 0, count);
            }
            out.flush();
            origin.close();

        }

        out.close();
        return dest.toByteArray();
      }
      catch (Exception e) {
        logger.error(StringUtil.getExceptionDetailInfo(e));
      }
      return null;
    }

    /**
     * 取得文件列表
     * @param path
     * @param ls
     */
    public static void setFile(String path, List<File> ls){
    	File dir = new File(path);
    	File[] file = dir.listFiles();
		for(File fi : file){
			if(fi.isDirectory()){
				setFile(path + "/" +fi.getName(), ls);
			} else {
				ls.add(fi);
			}
		}
	}
    
    /**
     * 写文件操作
     * @param fileName
     * @param content
     * @return
     */
	public static boolean writeBytesToFile(String fileName, byte[] content) {
		return writeBytesToFile(fileName, content, false);
	}

	public static boolean writeBytesToFile(String fileName, byte[] content,
			boolean append) {
		boolean result = true;
		File file = new File(fileName);
		BufferedOutputStream outfile = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			outfile = new BufferedOutputStream(new FileOutputStream(fileName,
					append));
			outfile.write(content);
			outfile.flush();
		} catch (IOException ex1) {
			logger.error("Write data to file:" + fileName + ",exception occurred!",ex1);
			result = false;
		} finally {
			if (outfile != null) {
				try {
					outfile.close();
				} catch (IOException ex3) {
					logger.error("Close file:" + fileName + "时,exception occurred!",ex3);
				}
			}
		}
		return result;
	}
	
	/**
	 * 文件内容读取
	 * @param fileName
	 * @param encode
	 * @return
	 */
	public static String readFile(String fileName,String encode) {
		StringBuffer content = new StringBuffer();
		String fileline;
		BufferedReader filedata = null;
		try {
			filedata=new BufferedReader(new InputStreamReader(new FileInputStream(fileName),encode));       
			while ((fileline = filedata.readLine()) != null) {
				content.append(fileline + "/r/n");
			}
		} catch (FileNotFoundException ex1) {
			logger.error("File:" + fileName + " not exist!",ex1);
		} catch (IOException ex2) {
			logger.error("Read File:" + fileName + ",exception occurred!",ex2);
		} finally {
			if (filedata != null) {
				try {
					filedata.close();
				} catch (IOException ex3) {
					logger.error("Close File:" + fileName + ",exception occurred",ex3);
				}
			}
		}
		return content.toString();
	}
	
	/**
	 * 二进制数据生成图片保存
	 * @param imgStr 二进制数据
	 * @param filePath 文件路径
	 * @param fileName 文件名称
	 * @return
	 */
	private static int saveToFile(String imgStr, String fileName, String filePath) {
		int stateInt = 1;
        
		if("".equals(imgStr)) {
        	return 0;
        }
		
        if("".equals(fileName)) {
        	return 0;
        }
        
//        String[] fl = fileName.split("_");
//        if(fl.length != 2) {
//        	return 0;
//        }
        
//        filePath += fl[0]+ "/";
        //fileName = fl[1];
        InputStream in = null;
        FileOutputStream bos = null;
        
        try{
        	deleteFile(filePath, fileName);
//        	openFile(filePath, fileName);
        	
        	in = new ByteArrayInputStream(new org.apache.commons.codec.binary.Base64().decode(imgStr.getBytes()));
        	bos = new FileOutputStream(new File(filePath + fileName));
            byte[] b =new byte[1024];
            int iRead =0;
            while((iRead = in.read(b)) != -1) {
            	bos.write(b, 0, iRead);
            }
            
            bos.flush();
            return stateInt;
        } catch (FileNotFoundException ex1) {
			logger.error("File:" + fileName + " not exist!", ex1);
			return stateInt = 0;
		} catch (IOException ex2) {
			logger.error("Read File:" + fileName + ",exception occurred!", ex2);
			return stateInt = 0;
		} catch(Exception ex3) {
            logger.error("Read File:" + fileName + ",exception occurred!", ex3);
            return stateInt = 0;
        } finally {
            try {
            	if(null != in) 
            		in.close();
            	if(null != bos) 
            		bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
	
	
	/**
	 * 二进制数据生成图片保存
	 * @param imgStr 二进制数据
	 * @param filePath 文件路径
	 * @param fileName 文件名称
	 * @return
	 */
	public static int[] saveToFileByImgStr(String[] imgStr, String fileName[]) {
		if(null == imgStr || null == fileName) {
			return new int[0];
		}
		
		String path = FileUtil.class.getClassLoader().getResource("").getPath();
        String realPath = path.substring(0, path.length() - 17) + "/files/installation/";
        
        int[] ret = new int[fileName.length];
		for(int i = 0; i < fileName.length; i++) {
			ret[i] = saveToFileByImgStr(imgStr[i], fileName[i], realPath);
		}
		
		return ret;
	}
	
	/**
	 * 二进制数据生成图片保存
	 * @param imgStr 二进制数据
	 * @param picDir 文件路径
	 * @param fileName 文件名称
	 * @return
	 */
	public static int saveToFileByImgStr(String imgStr, String picDir, String fileName) {
		if(null == imgStr || null == fileName) {
			return 0;
		}
		
		String path = FileUtil.class.getClassLoader().getResource("/").getPath();
		String dir = "";
		if(!StringUtil.isEmptyString(path)) {
			dir = path.substring(0,path.lastIndexOf("WEB-INF"));
		}
		dir = dir + "/" + picDir;
		File dirFile = new File(dir);
		if(!dirFile.exists()) {
			dirFile.mkdirs();
		}
		String realPath = dir + "/";
		return saveToFile(imgStr, fileName, realPath);
	}	
	
}
