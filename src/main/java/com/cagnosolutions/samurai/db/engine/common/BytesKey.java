package com.cagnosolutions.samurai.db.engine.common;

import java.util.Comparator;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class BytesKey extends BytesVal implements Comparable<BytesKey> {

	private static final Comparator<byte[]> COMPARATOR = BytesUtil.lexicographicalComparator();

	private final int hashCode;

	public boolean equals(Object o) {
		if (o instanceof BytesKey) {
			BytesKey other = (BytesKey) o;
			return hashCode == other.hashCode && equals(bytes, other.bytes);
		}
		return false;
	}

	public BytesKey(byte[] bytes) {
		super(bytes);
		int hashCode = 0;
		for (byte aByte : this.bytes) {
			hashCode += 43 * aByte;
		}
		this.hashCode = hashCode;
	}

	public int hashCode() {
		return hashCode;
	}

	public int compareTo(BytesKey o) {
		return COMPARATOR.compare(this.bytes, o.bytes);
	}

	public int getLength() {
		return bytes.length;
	}
}