package com.cagnosolutions.samurai.db.engine;

import com.gs.collections.api.map.MutableMap;
import com.gs.collections.impl.list.mutable.primitive.ByteArrayList;
import com.gs.collections.impl.map.mutable.ConcurrentHashMap;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class DatabaseEngine2 {

	private final MutableMap<ByteArrayList, ByteArrayList> dataStore = new ConcurrentHashMap<>();

	public boolean put(String key, byte[] val) {
		return (dataStore.put(new ByteArrayList(key.getBytes()), new ByteArrayList(val)) == null);
	}

	public boolean del(String key) {
		return (dataStore.remove(new ByteArrayList(key.getBytes())) != null);
	}

	public byte[] get(String key) {
		return dataStore.get(new ByteArrayList(key.getBytes())).toArray();
	}

	public int size() {
		return dataStore.size();
	}
}
