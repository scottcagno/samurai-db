package com.cagnosolutions.samurai.db.ngin.common;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class BytesVal {
	protected final byte[] bytes;

	public BytesVal(byte[] bytes) {
		this.bytes = bytes;
	}

	public int hashCode() {
		int hashCode = 0;
		for (byte aByte : this.bytes) {
			hashCode += 43 * aByte;
		}
		return hashCode;
	}

	public static boolean equals(byte[] thisBytes, byte[] otherBytes) {
		int length = thisBytes.length;
		if (length != otherBytes.length) {
			return false;
		}
		int half = length / 2;
		for (int i = 0; i < half; i++) {
			int end = length - i - 1;
			if (thisBytes[end] != otherBytes[end]) return false;
			if (thisBytes[i] != otherBytes[i]) return false;
		}
		if (half != length - half) {
			if (thisBytes[half] != otherBytes[half]) return false;
		}
		return true;
	}

	public boolean equals(Object o) {
		return o instanceof BytesKey && equals(bytes, ((BytesKey) o).bytes);
	}

	public String toString() {
		return new String(bytes);
	}

	public byte[] getBytes() {
		return bytes;
	}

	public int getLength() {
		return bytes.length;
	}
}