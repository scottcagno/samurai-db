package com.cagnosolutions.samurai.db.netty.memcached;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class Opcode {
	public static final byte GET = 0x00;
	public static final byte SET = 0x01;
	public static final byte DELETE = 0x04;
}
