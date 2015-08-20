package com.cagnosolutions.samurai.db.engine;

import com.cagnosolutions.samurai.db.engine.util.Bytes;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class DatabaseEngine3 {

	private final ConcurrentMap<Bytes,Bytes> dataStore = new ConcurrentHashMap<>(500, 0.75f, 2);

	public boolean put(byte[] key, byte[] val) {
		return (dataStore.put(Bytes.wrap(key), Bytes.wrap(val)) == null);
	}

	public boolean del(byte[] key) {
		return (dataStore.remove(Bytes.wrap(key)) != null);
	}

	public byte[] get(byte[] key) {
		Bytes bytes = dataStore.get(Bytes.wrap(key));
		return bytes.getBuffer();
	}

	public int size() {
		return dataStore.size();
	}
}
