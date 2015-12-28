package com.nju.run.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	public static String md5Password(String password){
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			byte[] result = digest.digest(password.getBytes());
			StringBuffer buffer = new StringBuffer();
			//把每一个byte做一个与运算
			for (byte b : result) {
				//与运算
				int num = b & 0xff;
				String str = Integer.toHexString(num);
				if(str.length()==1){
					buffer.append("0");
				}
				buffer.append(str);
			}
			//标准的md5加密后的结果
			return buffer.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
}
