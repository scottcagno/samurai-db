package com.cagnosolutions.samurai.db.engine;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class DatabaseEngine {

	private final ConcurrentMap<String, String> dataStore =
			new ConcurrentHashMap<>(500, 0.75f, 2);

	public boolean put(String key, String val) {
		return (dataStore.put(key, val) == null);
	}

	public boolean del(String key) {
		return (dataStore.remove(key) != null);
	}

	public String get(String key) {
		return dataStore.get(key);
	}

}
