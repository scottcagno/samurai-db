package com.cagnosolutions.samurai.db.engine.util;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public final class ByteArray {

	private final byte[] buffer;

	public ByteArray(byte[] buffer) {
		this.buffer = buffer;
	}

	public boolean equals(Object o) {
		if(o != null) {
			if (o instanceof ByteArray) {
				ByteArray other = (ByteArray) o;
				return other.size() == this.size() && Arrays.equals(other.buffer, this.buffer);
			}
		}
		return false;
	}

	public int hashCode() {
		return Arrays.hashCode(buffer);
	}

	public static ByteArray wrap(byte[] key) {
		return new ByteArray(key);
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

}
