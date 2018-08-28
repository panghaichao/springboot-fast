package com.cctc.fast.common.utils.string;

import java.lang.reflect.Array;

public class ArrayUtil {
	public static final int INDEX_NOT_FOUND = -1;

	public static <T> boolean isEmpty(T[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(Object array) {
		if (null == array)
			return true;
		if (isArray(array)) {
			return 0 == Array.getLength(array);
		}
		return false;
	}

	public static boolean isEmpty(long[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(int[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(short[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(char[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(byte[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(double[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(float[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(boolean[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isArray(Object obj) {
		if (null == obj) {
			return false;
		}
		return obj.getClass().isArray();
	}
}
