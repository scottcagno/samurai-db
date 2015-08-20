package com.cagnosolutions.samurai.db.io.disk;

import com.gs.collections.api.map.MutableMap;
import com.gs.collections.impl.list.mutable.primitive.ByteArrayList;
import com.gs.collections.impl.map.mutable.ConcurrentHashMap;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class TestMap {

	private MutableMap<ByteArrayList, ByteArrayList> index = new ConcurrentHashMap<>();

	private ByteArrayList toByteArrayList(String s) {
		return new ByteArrayList(s.getBytes());
	}

	private ByteArrayList toByteArrayList(byte[] b) {
		return new ByteArrayList(b);
	}

	public boolean put(byte[] k, byte[] v) {
		return index.put(toByteArrayList(k), toByteArrayList(v)) != null;

	}

	public byte[] get(byte[] k) {
		return index.get(toByteArrayList(k)).toArray();
	}

	public boolean del(byte[] k) {
		return index.remove(toByteArrayList(k)) != null;
	}

	public int size() {
		return index.size();
	}

}
