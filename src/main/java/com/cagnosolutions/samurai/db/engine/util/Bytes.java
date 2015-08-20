package com.cagnosolutions.samurai.db.engine.util;

import java.nio.charset.StandardCharsets;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public final class Bytes {

	protected final byte[] buffer;

	public Bytes(byte[] buffer) {
		this.buffer = buffer;
	}

	public Bytes(String str) {
		this.buffer = str.getBytes(StandardCharsets.UTF_8);
	}

	public boolean equals(Object o) {
		if(o != null) {
			if (o instanceof Bytes) {
				Bytes other = (Bytes) o;
				return other.size() == this.size() && checkEquals(other.buffer, this.buffer, size());
			}
		}
		return false;
	}

	public int hashCode() {
		int h = 1, max = this.buffer.length;
		for(int i = 0; i < max; i++)
			h = 31 * h + rehash(this.buffer[i]);
		return h;
	}

	public static Bytes wrap(byte[] key) {
		return new Bytes(key);
	}

	public byte[] getBuffer() {
		return buffer;
	}

	public int size() {
		return buffer.length;
	}

	public String toString() {
		return new String(buffer, StandardCharsets.UTF_8);
	}

	private boolean checkEquals(byte [] b1, byte [] b2, int length) {
		for (int i = 0; i < length; i++)
			if (!((b1[i]) == (b2[i])))
				return false;
		return true;
	}

	// murmur hash 3 algorithm (32-bit/int finalizer)
	private static int rehash(int k) {
		k ^= k >>> 16;
		k *= 0x85ebca6b;
		k ^= k >>> 13;
		k *= 0xc2b2ae35;
		k ^= k >>> 16;
		return k;
	}
}
