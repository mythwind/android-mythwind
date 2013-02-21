package com.android.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/***************************************************************
 * File name: CheckNetworkUtils.java    Created on January, 8, 2013
 * Title:		   [工具类]
 * Description: [查看网络连接状态的工具类]
 * Company:     东软软件股份有限公司
 * Department: 软件开发事业部
 * @author      [王树生] wangshusheng@neusoft.com
 * ------------------------------------------------------------
 * 修改历史
 * 序号  日期  修改人   修改原因
 * 1
 *-------------------------------------------------------------
***************************************************************/
public class CheckNetworkUtils {
	
	private Context context;
	
	public CheckNetworkUtils() {
	}
	
	public CheckNetworkUtils(Context context) {
		this.context = context;
	}
	
	/**
	 * Description: 查看网络是否已经连接
	 * @return
	 */
	public boolean isNetworkReachable() {
    	ConnectivityManager mManager = (ConnectivityManager) context.getSystemService(
    			Context.CONNECTIVITY_SERVICE);
    	NetworkInfo networkInfo = mManager.getActiveNetworkInfo();
    	if(networkInfo == null) {
    		return false;
    	}
    	return networkInfo.getState() == NetworkInfo.State.CONNECTED;
    }
	
	/**
	 * Description: 查看WIFI是否已经连接
	 * @return
	 */
	public boolean isWifiReachable() {
    	ConnectivityManager mManager = (ConnectivityManager) context.getSystemService(
    			Context.CONNECTIVITY_SERVICE);
    	NetworkInfo networkInfo = mManager.getActiveNetworkInfo();
    	if(networkInfo == null) {
    		return false;
    	}
    	return networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }
	
}
