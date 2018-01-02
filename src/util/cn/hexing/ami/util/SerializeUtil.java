package cn.hexing.ami.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

/** 
 * @Description 序列化工具类  完成序列化和反序列化
 * @author  jun
 * @Copyright 2013 hexing Inc. All rights reserved
 * @time：2013-8-29
 * @version AMI3.0 
 */
public class SerializeUtil {
	private static Logger logger = Logger.getLogger(SerializeUtil.class.getName());
	/**
	 * 序列化对象
	 * @param obj
	 * @return
	 */
	public static byte[]  serializeData(Object obj){
		byte[] data = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		
        try {
        	oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.flush();
            oos.close();
            
            data = baos.toByteArray();
        } catch (Exception e) {
            logger.error("serialize object:"+obj.getClass()+" is abnormal:"+StringUtil.getExceptionDetailInfo(e));
        }finally{
        	try{
	        	if (oos!=null) {
					oos.close();
				}
	        	if (baos!=null) {
	        		baos.close();
				}
        	}catch(Exception e1){
        		logger.error("serialize close outputStream error:"+StringUtil.getExceptionDetailInfo(e1));
        	}
        	
        }
        return data;
    }
	
	/**
	 * 反序列化对象
	 * @param data
	 * @return
	 */
	public static Object deserializeData(byte[] data) {
		Object rtnObj = null;
		ByteArrayInputStream is = null;
		ObjectInputStream ois = null;
        try {
        	is = new ByteArrayInputStream(data);  
        	ois = new ObjectInputStream(is);
            
            rtnObj = ois.readObject();
        } catch (Exception e) {
        	 logger.error("deserialize object:"+data.getClass()+" is abnormal:"+StringUtil.getExceptionDetailInfo(e));
        }finally{
        	try{
	        	if (ois!=null) {
	        		ois.close();
				}
	        	if (is!=null) {
	        		is.close();
				}
        	}catch(Exception e1){
        		logger.error("deserialize close outputStream error:"+StringUtil.getExceptionDetailInfo(e1));
        	}
        }
        return rtnObj;
    }
}
