package com.cagnosolutions.samurai.db.io.disk;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class TestMap {

	private Map<byte[],byte[]> test = new HashMap<>();

	public byte[] put(String k, String v) {
		return test.put(k.getBytes(), v.getBytes());
	}

	public byte[] put(byte[] k, byte[] v) {
		return test.put(k, v);
	}

	public byte[] put(byte k, byte v) {
		return test.put(new byte[]{k}, new byte[]{v});
	}

	public byte[] get(String k) {
		return test.get(k.getBytes());
	}

	public byte[] get(byte[] k) {
		return test.get(k);
	}

	public byte[] get(byte k) {
		return test.get(new byte[]{k});
	}

	public byte[] remove(byte[] k) {
		return test.remove(k);
	}

	public int size() {
		return test.size();
	}

}
