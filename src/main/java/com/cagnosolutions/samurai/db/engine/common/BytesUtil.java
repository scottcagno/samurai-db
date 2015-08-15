package com.cagnosolutions.samurai.db.engine.common;

import java.util.Comparator;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public final class BytesUtil {

	private BytesUtil() {}

	public static int compare(byte a, byte b) {
		return a - b; // safe due to restricted range
	}

	public static Comparator<byte[]> lexicographicalComparator() {
		return LexicographicalComparator.INSTANCE;
	}

	private enum LexicographicalComparator implements Comparator<byte[]> {
		INSTANCE;

		public int compare(byte[] left, byte[] right) {
			int minLength = Math.min(left.length, right.length);
			for (int i = 0; i < minLength; i++) {
				int result = BytesUtil.compare(left[i], right[i]);
				if (result != 0) {
					return result;
				}
			}
			return left.length - right.length;
		}
	}
}
