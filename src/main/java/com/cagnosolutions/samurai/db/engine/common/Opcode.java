package com.cagnosolutions.samurai.db.engine.common;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public final class Opcode {

	// state codes
	public static final byte NIL = 0x00;	//  -- null code
	public static final byte OKY = 0x01;	//  -- okay code
	public static final byte ERR = 0x02;	//  -- error code

	// opcodes
	public static final byte ADD = 0x03;	// ADD <VAL> 		-- add a new value with auto generated key
	public static final byte SET = 0x04;	// SET <KEY> <VAL> 	-- set a key value pair
	public static final byte GET = 0x05;	// GET <KEY> 		-- get value by key
	public static final byte DEL = 0x06;	// DEL <KEY>		-- delete a key value pair
	public static final byte PSH = 0x07;	// PSH <KEY> <VAL> 	-- push a value on
	public static final byte POP = 0x08;	// POP <KEY> 		-- pop a value off
	public static final byte TTL = 0x09;	// TTL <KEY> 		-- set time to live for a key value pair

	// persistence codes
	public static final byte SAV = 0x10;	//  SAV <KEY>		-- save snapshot
	public static final byte LOD = 0x11;	//  LOD	<KEY>		-- load snapshot

}
