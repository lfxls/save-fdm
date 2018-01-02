package cn.hexing.ami.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

public class ImageUtil {
	private static Logger logger = Logger.getLogger(ImageUtil.class.getName());
	
	/**
	 * 按指定比例缩放图片
	 * @param sourceImagePath 源图片路径
	 * @param destinationPath 目的路径
	 * @param scale 比例
	 * @param format 格式
	 */
	public static void resizeByScale(String sourceImagePath, 
		      String destinationPath, double scale, String format) {
		File file = new File(sourceImagePath);
		BufferedImage bufferedImage; 
	    try { 
	      bufferedImage = ImageIO.read(file); 
	      int width = bufferedImage.getWidth(); 
	      int height = bufferedImage.getHeight(); 
	      
	      width = (int)(width * scale); 
	      height = (int)(height * scale); 
	  
	      Image image = bufferedImage.getScaledInstance(width, height, 
	          Image.SCALE_SMOOTH); 
	      BufferedImage outputImage = new BufferedImage(width, height, 
	          BufferedImage.TYPE_INT_RGB); 
	      Graphics graphics = outputImage.getGraphics(); 
	      graphics.drawImage(image, 0, 0, null); 
	      graphics.dispose(); 
	      ImageIO.write(outputImage, format, new File(destinationPath)); 
	    } catch (IOException e) { 
	      logger.error("photo resize error:" + e.getMessage());
	    } 
	}
	
	/**
	 * 按指定宽高缩放图片
	 * @param sourceImagePath 源图片路径
	 * @param destinationPath 目的图片路径
	 * @param width 长
	 * @param height 高
	 * @param auto 是否自动保持图片的原高宽比例
	 * @param format 图片格式
	 */
	public static void resizeBySize(String sourceImagePath, 
		      String destinationPath, int width, int height, boolean auto,String format) { 
		try { 
		    File file = new File(sourceImagePath); 
		    BufferedImage bufferedImage = null; 
		    bufferedImage = ImageIO.read(file); 
		    if (auto) { 
		    	ArrayList<Integer> paramsArrayList = getAutoWidthAndHeight(bufferedImage,width,height); 
		    	width = paramsArrayList.get(0); 
		    	height = paramsArrayList.get(1); 
		    } 
		      
		    Image image = bufferedImage.getScaledInstance(width, height, 
		        Image.SCALE_DEFAULT); 
		    BufferedImage outputImage = new BufferedImage(width, height, 
		        BufferedImage.TYPE_INT_RGB); 
		    Graphics graphics = outputImage.getGraphics(); 
		    graphics.drawImage(image, 0, 0, null); 
		    graphics.dispose(); 
		    ImageIO.write(outputImage, format, new File(destinationPath)); 
	    } catch (Exception e) { 
	    	logger.error("photo resize error:" + e.getMessage());
	    } 
	} 
	
	/** 
	 * 
	 * @param bufferedImage 要缩放的图片对象 
	 * @param width_scale 要缩放到的宽度 
	 * @param height_scale 要缩放到的高度 
	 * @return 一个集合，第一个元素为宽度，第二个元素为高度 
	 */
	private static ArrayList<Integer> getAutoWidthAndHeight(BufferedImage bufferedImage,int width_scale,int height_scale){ 
	    ArrayList<Integer> arrayList = new ArrayList<Integer>(); 
	    int width = bufferedImage.getWidth(); 
	    int height = bufferedImage.getHeight(); 
	    double scale_w =getDot2Decimal(width_scale,width); 
	    double scale_h = getDot2Decimal(height_scale,height); 
	    if(scale_w<scale_h) { 
	    	arrayList.add((int)(scale_w*width)); 
	    	arrayList.add((int)(scale_w*height)); 
	    } else { 
	    	arrayList.add((int)(scale_h*width)); 
	    	arrayList.add((int)(scale_h*height)); 
	    } 
	    return arrayList; 
	 } 
	
	/** 
	 * 返回两个数a/b的小数点后三位的表示 
	 * @param a 
	 * @param b 
	 * @return 
	 */
	public static double getDot2Decimal(int a,int b){ 
	    BigDecimal bigDecimal_1 = new BigDecimal(a); 
	    BigDecimal bigDecimal_2 = new BigDecimal(b); 
	    BigDecimal bigDecimal_result = bigDecimal_1.divide(bigDecimal_2,new MathContext(4)); 
	    Double double1 = new Double(bigDecimal_result.toString()); 
	    return double1; 
	  } 
	
	/**
	 * 生成缩略图
	 * @param dir 文件存储所在的目录
	 * @param files 源文件
	 * @param width 缩放宽度
	 * @param height 缩放高度
	 * @param auto 按比例自动缩放
	 * @param flag 标志 1=放大，2=缩小
	 * @return
	 */
	public static String resizeManyFiles(String dir,String files,int width, 
			int height, boolean auto, String flag) {
		StringBuilder resizeFiles = new StringBuilder();
		String[] fileArray = files.split(",");
		for(String fileStr : fileArray) {
			File file = new File(dir + fileStr);
			if(file.exists()) {//存在文件
				//文件名
				String fileName = file.getName();
				String name = fileName.split("\\.")[0];//名称
				String format = fileName.split("\\.")[1];//后缀
				//文件路径
				String filePath = file.getPath().replace("\\", "/");
				filePath = filePath.substring(0,filePath.lastIndexOf("/"));
				String reFilePath = "";
				if(flag.equals("1")) {
					//新压缩文件路径
					reFilePath = filePath + File.separator + name+ "." + format;
					resizeByScale(file.getPath(),reFilePath,1.5,format);
				} else if(flag.equals("2")) {
					//新压缩文件路径
					reFilePath = filePath + File.separator + name+"s" + "." + format;
					resizeByScale(file.getPath(),reFilePath,0.5,format);
				}
//				resizeBySize(file.getPath(),reFilePath,width,height,auto,format);
				
				if(resizeFiles.length() == 0) {
					resizeFiles.append("/" + reFilePath.substring(dir.length()-1).replace("\\", "/"));
				} else {
					resizeFiles.append(",").append("/" + reFilePath.substring(dir.length()-1).replace("\\", "/"));
				}
			}
		}
		return resizeFiles.toString();
	}
	
	/**
	 * 缩放图片
	 * @param dir 文件存储所在的目录
	 * @param file 源文件
	 * @return
	 */
	public static void resizeFile(String dir,String file) {
		File sourceFile = new File(dir + file);
		if(sourceFile.exists()) {//存在文件
			//文件名
			String fileName = sourceFile.getName();
			String name = fileName.split("\\.")[0];//名称
			String format = fileName.split("\\.")[1];//后缀
			//文件路径
			String filePath = sourceFile.getPath().replace("\\", "/");
			filePath = filePath.substring(0,filePath.lastIndexOf("/"));
			//放大文件路径
			String eFilePath = filePath + File.separator + name+ "e." + format;
			resizeBySize(sourceFile.getPath(),eFilePath,180,320,false,format);
//			resizeByScale(sourceFile.getPath(),eFilePath,1.5,format);
			//缩小文件路径
			String nFilePath = filePath + File.separator + name+"n" + "." + format;
			resizeBySize(sourceFile.getPath(),nFilePath,36,64,false,format);
//			resizeByScale(sourceFile.getPath(),nFilePath,0.3,format);
		}
	}
}
