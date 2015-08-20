package com.cagnosolutions.samurai.db.engine;

import com.gs.collections.api.map.ConcurrentMutableMap;
import com.gs.collections.impl.list.mutable.primitive.ByteArrayList;
import com.gs.collections.impl.map.mutable.ConcurrentHashMap;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class DatabaseEngine2 {

	private final ConcurrentMutableMap<ByteArrayList, ByteArrayList> dataStore = new ConcurrentHashMap<>();

	public boolean put(byte[] key, byte[] val) {
		return (dataStore.put(new ByteArrayList(key), new ByteArrayList(val)) == null);
	}

	public boolean del(byte[] key) {
		return (dataStore.remove(new ByteArrayList(key)) != null);
	}

	public byte[] get(byte[] key) {
		return dataStore.get(new ByteArrayList(key)).toArray();
	}

	public int size() {
		return dataStore.size();
	}
}
