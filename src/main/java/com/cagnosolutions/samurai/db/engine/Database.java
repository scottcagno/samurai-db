package com.cagnosolutions.samurai.db.engine;

import com.cagnosolutions.samurai.db.engine.util.UUID1;
import com.cagnosolutions.samurai.db.io.disk.DiskStoreEngine;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public final class Database {

	private final static String ERR = "ERR";
	private final static String OK = "OK";

	private static DiskStoreEngine diskStoreEngine;
	private static DatabaseEngine databaseEngine;

	public Database(String path, int size) {
		diskStoreEngine = new DiskStoreEngine(path, size);
		databaseEngine = new DatabaseEngine();

		// loaded from file
		System.out.println("Loading data from file...");
		for(String stmt : diskStoreEngine.readLines())
			stmt(stmt.substring(14));
		System.out.println("Done loading data.");
	}

	public String stmt(String stmt, Object ...fmt) {
		return stmt(String.format(stmt, fmt));
	}

	public String stmt(String stmt) {
		String[] stmtData = stmt.split("\\s+", 3);
		if(stmtData[0].equalsIgnoreCase("PUT"))
			return put(stmtData[1], stmtData[2]);
		else if(stmtData[0].equalsIgnoreCase("DEL"))
			return del(stmtData[1]);
		else if(stmtData[0].equalsIgnoreCase("GET"))
			return get(stmtData[1]);
		else if(stmtData[0].equalsIgnoreCase("UUID"))
			return uuid();
		else
			return ERR;
	}

	public String put(String key, String val) {
		if(!databaseEngine.put(key, val))
			return ERR;
		diskStoreEngine.put(key, val);
		return OK;
	}

	public String del(String key) {
		if(!databaseEngine.del(key))
			return ERR;
		diskStoreEngine.del(key);
		return OK;
	}

	public String get(String key) {
		String res = databaseEngine.get(key);
		return (res == null) ? ERR : res;
	}

	private String uuid() {
		return UUID1.getTimeUUID().toString();
	}

}