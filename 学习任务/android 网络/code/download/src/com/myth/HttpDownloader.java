package com.myth;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDownloader {
	
	private URL url = null;
	/**
	 *  根据 URL 下载文件，前提是这个文件的内容是文本，函数的返回值是文件当中的内容。
	 * @return
	 */
	public String download(String urlStr) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try {
			url = new URL(urlStr);
			System.out.println("download --> " + url);
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			// 使用 IO 流读取数据
			buffer = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			while((line = buffer.readLine()) != null) {
				sb.append(line);
				System.out.println(sb.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	/**
	 *    返回 -1，下载出错；返回 0，下载成功；返回 1，文件已经存在。
	 */
	public int downFile(String urlStr, String path, String fileName) {
		InputStream inputStream = null;
		try {
			FileUtils fileUtils = new FileUtils();
			if(fileUtils.isFileExist(path + fileName)) {
				return 1;
			} else {
				inputStream = getInputStreamFromUrl(urlStr);
				File resultFile = fileUtils.write2SDFromInput(path, fileName, inputStream);
				if(resultFile == null) {
					return -1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	public InputStream getInputStreamFromUrl(String urlStr) throws Exception {
		url = new URL(urlStr);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		return urlConn.getInputStream();
	}
}
