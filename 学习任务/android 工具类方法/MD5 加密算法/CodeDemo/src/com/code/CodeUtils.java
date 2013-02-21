package com.code;

import java.security.MessageDigest;

/***************************************************************
 * File name: MainScreen.java     Created on December. 12, 2012
 * Title:		   [工具类]
 * Description: [字符加密解密实现的工具类]
 * Company:     东软软件股份有限公司
 * Department: 软件开发事业部
 * @author      [王树生] wangshusheng@neusoft.com
 * ------------------------------------------------------------
 * 修改历史
 * 序号  日期  修改人   修改原因
 * 1
 *-------------------------------------------------------------
***************************************************************/
public class CodeUtils {
	
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'a', 'b', 'c', 'd', 'e', 'f' };
	
	/**------  MD5加密功能的实现  ------*/
	public static String encrypt(String source) {
		if (source == null || source.length() == 0) {
			return "";
		}
		String target = null;
		try {
			// 获取 MD5 算法
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 根据给定的(字符串先转化为字节)字节流更新算法
			md.update(source.getBytes());
			// 为 MessageDigest 计算并返回最终的 hash value
			byte tmp[] = md.digest();
			//  16 应该为 tmp.length()
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				str[k++] = HEX_DIGITS[tmp[i] >>> 4 & 0xf];
				str[k++] = HEX_DIGITS[tmp[i] & 0xf];
			}
			target = new String(str);
			return target;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**------  MD5解密功能的实现  ------*/
	public static String decrypt(String source) {
		return "";
	}
	
}
