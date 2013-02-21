package com.android.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

/***************************************************************
 * File name: FormatUtils.java    Created on January, 8, 2013
 * Title:		   [工具类]
 * Description: [格式化日期的工具类]
 * Company:     东软软件股份有限公司
 * Department: 软件开发事业部
 * @author      [王树生] wangshusheng@neusoft.com
 * ------------------------------------------------------------
 * 修改历史
 * 序号  日期  修改人   修改原因
 * 1
 *-------------------------------------------------------------
***************************************************************/
public class FormatUtils {
	
	private static final String TAG = "FormatUtils";

	/**
	 * Description: 把 Date 类型格式化为字符串类型
	 * @param date 日期
	 * @param formatStr 格式化之后显示的日期格式的字符串
	 * @return
	 */
	public static String formatStrDate(Date date, String formatStr) {
		if(date == null) 
			return "";
		SimpleDateFormat formater = new SimpleDateFormat(formatStr);
		return formater.format(date);
	}
	
	/**
	 * Description: 把字符串类型格式化为Date 类型
	 * @param strDate  字符串类型的日期
	 * @param formatStr 格式化之后的日期格式
	 * @return
	 */
	public static Date formatDate(String strDate, String formatStr) {
		if(strDate == null||"".equals(strDate)) 
			return null;
		SimpleDateFormat formater = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = formater.parse(strDate);
		} catch (ParseException e) {
			Log.e(TAG, e.getMessage());
		}
		return date;
	}
	
}
