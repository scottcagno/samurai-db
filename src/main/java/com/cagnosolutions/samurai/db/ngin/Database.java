package com.cagnosolutions.samurai.db.ngin;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public final class Database {

	private final static ConcurrentMap<String, String> store = new ConcurrentHashMap<>(500, 0.75f, 2);
	private final static String ERR = "ERR";
	private final static String OK = "OK";



	private Database() {}

	public final static Database INSTANCE = new Database();

	/**
	 * Single string statement method
	 * @param stmt
	 * @return
	 */
	public String stmt(String stmt) {
		String[] stmtData = stmt.split("\\s+", 3);
		if(stmtData[0].equalsIgnoreCase("SET"))
			return set(stmtData[1], stmtData[2]);
		else if(stmtData[0].equalsIgnoreCase("GET"))
			return get(stmtData[1]);
		else if(stmtData[0].equalsIgnoreCase("DEL"))
			return del(stmtData[1]);
		else
			return ERR;
	}

	/**
	 * Public methods
	 */

	public String set(String key, String val) {
		String res = store.put(key, val);
		return (res == null) ? OK : res;
	}

	public String get(String key) {
		String res = store.get(key);
		return (res == null) ? ERR : res;
	}

	public String del(String key) {
		String res = store.remove(key);
		return (res == null) ? ERR : res;
	}
}