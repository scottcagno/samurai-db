package com.cagnosolutions.samurai.db.ngin;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public final class Database {

	private final static ConcurrentMap<String, DataSet> store = new ConcurrentHashMap<>(500, 0.75f, 2);

	private Database() {}

	public final static Database INSTANCE = new Database();

	/**
	 * Single string statement method
	 * @param stmt
	 * @return
	 */
	public String stmt(String stmt) {
		String[] stmtData = stmt.split("\\s+", 3);
		if(stmtData[0] == "ADD")
			return add(stmtData);
		else if(stmtData[0] == "SET")
			return set(stmtData);
		else if(stmtData[0] == "GET")
			return get(stmtData);
		else if(stmtData[0] == "DEL")
			return del(stmtData);
		else if(stmtData[0] == "PSH")
			return psh(stmtData);
		else if(stmtData[0] == "POP")
			return pop(stmtData);
		else if(stmtData[0] == "TTL")
			return ttl(stmtData);
		else if(stmtData[0] == "SAV")
			return sav(stmtData);
		else if(stmtData[0] == "LOD")
			return lod(stmtData);
		else
			return "ERR";
	}

	/**
	 * Private statement methods
	 * @param stmtData
	 * @return
	 */
	
	private String add(String[] stmtData) {
		// TODO: add method
		return null;
	}

	private String set(String[] stmtData) {
		// TODO: set method
		return null;
	}

	private String get(String[] stmtData) {
		// TODO: get method
		return null;
	}

	private String del(String[] stmtData) {
		// TODO: delete method
		return null;
	}

	private String psh(String[] stmtData) {
		// TODO: push method
		return null;
	}

	private String pop(String[] stmtData) {
		// TODO: pop method
		return null;
	}

	private String ttl(String[] stmtData) {
		// TODO: time to live method
		return null;
	}

	private String sav(String[] stmtData) {
		// TODO: save method
		return null;
	}

	private String lod(String[] stmtData) {
		// TODO: load method
		return null;
	}

	/**
	 * Public methods
	 */

	public String add(String value) {
		// TODO: add method
		return null;
	}

	public String set(String key, String value) {
		// TODO: set method
		return null;
	}

	public String get(String jey) {
		// TODO: get method
		return null;
	}

	public String del(String key) {
		// TODO: delete method
		return null;
	}

	public String psh(String key, String value) {
		// TODO: push method
		return null;
	}

	public String pop(String key) {
		// TODO: pop method
		return null;
	}

	public String ttl(String key) {
		// TODO: time to live method
		return null;
	}

	public String sav(String key) {
		// TODO: save method
		return null;
	}

	public String lod(String key) {
		// TODO: load method
		return null;
	}

}
