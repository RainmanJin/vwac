/**  
 * 文件名：AESEXT.java  
 *   
 * 日期：2015年7月8日  
 *  
 */

package com.ecannetwork.core.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * @author: 李超
 *
 * @version: 2015年7月8日 下午9:01:50
 */

public class AESEXT {

	public static final String AES_KEY = "JcVXP8ADJcVXP8AD";

	public static String aesDecrypt(String encryptStr, String decryptKey)
			throws Exception {
		return encryptStr == null ? null : aesDecryptByBytes(
				base64Decode(encryptStr), decryptKey);
	}

	/**
	 * AES解密
	 * 
	 * @param encryptBytes
	 *            待解密的byte[]
	 * @param decryptKey
	 *            解密密钥
	 * @return 解密后的String
	 * @throws Exception
	 */
	public static String aesDecryptByBytes(byte[] encryptBytes,
			String decryptKey) throws Exception {
		byte[] raw = decryptKey.getBytes("ASCII");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
		byte[] decryptBytes = cipher.doFinal(encryptBytes);

		return new String(decryptBytes);
	}

	public static byte[] base64Decode(String base64Code) throws Exception {
		return base64Code == null ? null : new BASE64Decoder()
				.decodeBuffer(base64Code);
	}

	/**
	 * AES加密为base 64 code
	 * 
	 * @param content
	 *            待加密的内容
	 * @param encryptKey
	 *            加密密钥
	 * @return 加密后的base 64 code
	 * @throws Exception
	 */
	public static String aesEncrypt(String content, String encryptKey)
			throws Exception {
		return base64Encode(aesEncryptToBytes(content, encryptKey));
	}

	/**
	 * base 64 encode
	 * 
	 * @param bytes
	 *            待编码的byte[]
	 * @return 编码后的base 64 code
	 */
	public static String base64Encode(byte[] bytes) {
		return new BASE64Encoder().encode(bytes);
	}

	/**
	 * AES加密
	 * 
	 * @param content
	 *            待加密的内容
	 * @param encryptKey
	 *            加密密钥
	 * @return 加密后的byte[]
	 * @throws Exception
	 */
	public static byte[] aesEncryptToBytes(String content, String encryptKey)
			throws Exception {
		if (encryptKey == null) {
			System.out.print("Key为空null");
			return null;
		}
		// 判断Key是否为16位
		if (encryptKey.length() != 16) {
			System.out.print("Key长度不是16位");
			return null;
		}
		byte[] raw = encryptKey.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		return cipher.doFinal(content.getBytes());
	}

	public static void main(String[] args) throws Exception {

		String key = "JcVXP8ADJcVXP8AD";
		
		 System.out.println("加密密钥和解密密钥：" + key);
		  
		 String content = "2015@163.com";
		 
		 System.out.println("加密前：" + content);
		  
		 String encrypt = aesEncrypt(content, key);
		 System.out.println("加密后：" + encrypt);
	

		String aesbasepass = "eYKe82cihDIv9kUMZxUL6Q==";

		System.out.println("原密文:" + aesbasepass);

		String decrypt = aesDecrypt(aesbasepass, key);
		System.out.println("解密后：" + decrypt);

		aesbasepass = "96886Lr/G8unnLacPflV3A==";

		System.out.println("原密文:" + aesbasepass);

		decrypt = aesDecrypt(aesbasepass, key);
		System.out.println("解密后：" + decrypt);

		aesbasepass = "rm4rCExTo4T5hNotSFozIQ==";

		System.out.println("原密文:" + aesbasepass);

		decrypt = aesDecrypt(aesbasepass, key);
		System.out.println("解密后：" + decrypt);
	}
}
