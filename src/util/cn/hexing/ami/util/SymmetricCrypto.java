package cn.hexing.ami.util;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import cn.hexing.ami.util.aes.Base64;

/**
 * @Description 对称算法 使用JDK API
 *    支持AES/DES/3DES等
 * @author jun
 * @Copyright 2014 hexing Inc. All rights reserved
 * @time：2014-5-21
 * @version AMI3.0
 */
public class SymmetricCrypto {
	// 密钥算法
	public static final String KEY_ALGORITHM = "AES";

	// 加解密算法/工作模式/填充方式,Java6.0支持PKCS5Padding填充方式,BouncyCastle支持PKCS7Padding填充方式
//	public static final String ALGORITHM_AES = "AES/ECB/PKCS5Padding";
	public static final String ALGORITHM_AES = "AES";
	public static final String ALGORITHM_DES = "DES";
	public static final String ALGORITHM_3DES = "3DES";
	private static String keyAlgorithm = ALGORITHM_AES;//默认为AES算法

	public static String getKeyAlgorithm() {
		return keyAlgorithm;
	}

	public static void setKeyAlgorithm(String keyAlgorithm) {
		SymmetricCrypto.keyAlgorithm = keyAlgorithm;
	}

	/**
	 * 生成密钥
	 */
	public static String initkey(String keyString) throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance(getKeyAlgorithm()); // 实例化密钥生成器
//		kg.init(128); // 初始化密钥生成器:AES要求密钥长度为128,192,256位
		kg.init(128, new SecureRandom(keyString.getBytes()));  
		SecretKey secretKey = kg.generateKey(); // 生成密钥
		return Base64.encode(secretKey.getEncoded()); // 获取二进制密钥编码形式
	}

	/**
	 * 转换密钥
	 */
	public static Key toKey(byte[] key) throws Exception {
		return new SecretKeySpec(key, KEY_ALGORITHM);
	}

	/**
	 * 加密数据
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return 加密后的数据
	 * */
	public static String encrypt(String data, String key) throws Exception {
		key = initkey(key);
		Key k = toKey(Base64.decode(key)); // 还原密钥
		Cipher cipher = Cipher.getInstance(getKeyAlgorithm()); // 实例化Cipher对象，它用于完成实际的加密操作
		cipher.init(Cipher.ENCRYPT_MODE, k); // 初始化Cipher对象，设置为加密模式
		return Base64.encode(cipher.doFinal(data.getBytes())); // 执行加密操作。加密后的结果通常都会用Base64编码进行传输
	}

	/**
	 * 解密数据
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return 解密后的数据
	 * */
	public static String decrypt(String data, String key) throws Exception {
		key = initkey(key);
		Key k = toKey(Base64.decode(key));
		Cipher cipher = Cipher.getInstance(getKeyAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, k); // 初始化Cipher对象，设置为解密模式
		return new String(cipher.doFinal(Base64.decode(data))); // 执行解密操作
	}
	
	
//	/** 加密(HHU交互用)
//	 * @param sSrc
//	 * @param sKey
//	 * @return 
//	 */
//	public static String hhuEncrypt(String sSrc, String sKey) throws Exception {
//	    if (sKey == null) {
//	        return null;
//	    }
//
//	    // 判断Key是否为16位
//	    if (sKey.length() != 16) {
//	        return null;
//	    }
//
//	    byte[] raw = sKey.getBytes("utf-8");
//	    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
//	    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
//	    cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
//	    byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
//	    return String.valueOf(new org.apache.commons.codec.binary.Base64().encode(encrypted));//此处使用BASE64做转码功能，同时能起到2次加密的作用。
//
//	}

//	/** 解密(HHU交互用)
//	 * @param sSrc
//	 * @param sKey
//	 * @return 
//	 */	
//	public static String hhuDecrypt(String sSrc, String sKey) throws Exception {
//	    try {
//	        // 判断Key是否正确
//	        if (sKey == null) {
//	            return null;
//	        }
//
//	        // 判断Key是否为16位(使用AES-128-ECB加密模式，key需要为16位)
//	        if (sKey.length() != 16) {
//	            return null;
//	        }
//
//	        byte[] raw = sKey.getBytes("utf-8");
//	        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
//	        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//	        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
//	        byte[] encrypted1 = new org.apache.commons.codec.binary.Base64().decode(sSrc.getBytes());//先用base64解密
//	        try {
//	            byte[] original = cipher.doFinal(encrypted1);
//	            String originalString = new String(original, "utf-8");
//	            return originalString;
//	        } catch (Exception e) {
//	            return null;
//	        }
//
//	    } catch (Exception ex) {
//	        return null;
//	    }
//	}
	
	public static String hhuEncrypt(String content, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		SecretKeySpec securekey = new SecretKeySpec(key.getBytes(), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, securekey);//设置密钥和加密形式
		return new String(new org.apache.commons.codec.binary.Base64().encode(cipher.doFinal(content.getBytes())),"UTF-8");
	}
	
	/** 解密(HHU交互用)
	 * @param sSrc
	 * @param sKey
	 * @return 
	 */	
	public static String hhuDecrypt(String sSrc, String sKey) throws Exception {
	    try {
	        // 判断Key是否正确
	        if (sKey == null) {
	            return null;
	        }
	        Cipher cipher = Cipher.getInstance("AES");
			SecretKeySpec securekey = new SecretKeySpec(sKey.getBytes(), "AES");
			cipher.init(Cipher.DECRYPT_MODE, securekey);
			byte[] original = cipher.doFinal(new org.apache.commons.codec.binary.Base64().decode(sSrc.getBytes()));
			String originalString = new String(original, "utf-8");
            return originalString; 
	    } catch (Exception ex) {
	        return null;
	    }
	}
}
