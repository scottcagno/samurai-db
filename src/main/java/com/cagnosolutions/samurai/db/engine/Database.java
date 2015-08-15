package com.cagnosolutions.samurai.db.engine;

import com.cagnosolutions.samurai.db.engine.common.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public final class Database {

	private final static ConcurrentMap<BytesKey, BytesVal> store = new ConcurrentHashMap<>(500, 0.75f, 2);

	private Database() {}

	public final static Database INSTANCE = new Database();

	public Message request(Message request) {
		byte opcode = request.getOpcode();
		if(opcode == Opcode.ADD)
			return add(request);
		else if(opcode == Opcode.SET)
			return set(request);
		else if(opcode == Opcode.GET)
			return get(request);
		else if(opcode == Opcode.DEL)
			return del(request);
		else if(opcode == Opcode.PSH)
			return psh(request);
		else if(opcode == Opcode.POP)
			return pop(request);
		else if(opcode == Opcode.TTL)
			return ttl(request);
		else if(opcode == Opcode.SAV)
			return sav(request);
		else if(opcode == Opcode.LOD)
			return lod(request);
		else
			return new Message(Opcode.ERR);
	}
	
	private Message add(Message request) {
		BytesKey key = new BytesKey(UUID1.getTimeUUIDBytes());
		store.putIfAbsent(key, request.getVal());
		if(store.containsKey(key))
			return new Message(Opcode.OKY, key);
		return new Message(Opcode.ERR);
	}

	private Message set(Message request) {
		store.put(request.getKey(), request.getVal());
		if(store.containsKey(request.getKey()))
			return new Message(Opcode.OKY);
		return new Message(Opcode.ERR);
	}

	private Message get(Message request) {
		if(store.containsKey(request.getKey()))
			return new Message(Opcode.OKY, new BytesKey(new byte[]{Opcode.NIL}), store.get(request.getKey()));
		return new Message(Opcode.ERR);
	}

	private Message del(Message request) {
		store.remove(request.getKey());
		if(!store.containsKey(request.getKey()))
			return new Message(Opcode.OKY);
		return new Message(Opcode.ERR);
	}

	private Message psh(Message request) {
		return new Message(Opcode.OKY);
	}

	private Message pop(Message request) {
		return new Message(Opcode.OKY);
	}

	private Message ttl(Message request) {
		return new Message(Opcode.OKY);
	}

	private Message sav(Message request) {
		return new Message(Opcode.OKY);
	}

	private Message lod(Message request) {
		return new Message(Opcode.OKY);
	}

}
