package org.wangye.carprinciple.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author wangye04 笨笨
 * @email wangye04@baidu.com
 * @datetime Sep 13, 2014 10:52:59 AM
 */
public class IOUtil {
	
	public static void closeQuietly(Closeable c) {
		if (c != null) {
			try {
				c.close();
			} catch (IOException e) {
			}
		}
	}
	
	public static String[] sArr(String arg) {
		return new String[]{arg};
	}
	
	public static String[] sArr(String... args) {
		return args;
	}
	
	public static int[] iArr(int arg) {
		return new int[]{arg};
	}
	
	public static int[] iArr(int... args) {
		return args;
	}
}
