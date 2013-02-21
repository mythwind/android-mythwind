package com.myth;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;

public class FileUtils {
	private String SDPATH;
	public String getSDPATH() {
		return SDPATH;
	}
	public FileUtils() {
		// 得到当前外部存储设备目录 "/SDCARD"
		SDPATH = Environment.getExternalStorageDirectory() + "/";
	}
	
	/**
	 *  在 SD卡上创建文件
	 * @throws IOException 
	 */
	public File createSDFile(String fileName) throws IOException {
		File file = new File(SDPATH + fileName);
		file.createNewFile();
		return file;
	}
	/**
	 *  在 SD卡上创建目录
	 * @throws IOException 
	 */
	public File createSDDir(String dirName) {
		File dir = new File(SDPATH + dirName);
		dir.mkdir();
		return dir;
	}
	/**
	 *  判断 SD卡上文件夹是否存在
	 * @throws IOException 
	 */
	public boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		return file.exists();
	}
	/**
	 *  将一个 InputStream 里面的数据写入到 SD卡
	 */
	public File write2SDFromInput(String path, String fileName, InputStream input) {
		File file = null;
		OutputStream output = null;
		try {
			createSDDir(path);
			file = createSDFile(path + fileName);
			output = new FileOutputStream(file);
			byte[] buffer = new byte[1024 * 4];
			while((input.read(buffer)) != -1) {
				output.write(buffer);
			}
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}
}
