package com.mfei.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamTool {

	/**
	 *  读取流中的数据
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static byte[] read(InputStream in) throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = in.read(buffer)) != -1) {
			outputStream.write(buffer, 0, len);
		}
		in.close();
		return outputStream.toByteArray();
	}

}
