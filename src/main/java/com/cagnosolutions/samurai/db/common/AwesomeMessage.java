package com.cagnosolutions.samurai.db.common;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class AwesomeMessage {

	/**
	 * 	+----------------+---------------+----------------+--------------------+--------------------------+
	 *	|     1 byte     |    3 bytes    |    7 bytes     |  712 max byte key  |  5242880 max byte value  |
	 *	+----------------+---------------+----------------+--------------------+--------------------------+
	 *	|  command byte  |   key length  |  value length  |    "user:1:data"   |   "my key foo bar baz"   |
	 *	+----------------+---------------+----------------+--------------------+--------------------------+
	 */

	public static byte[] encode(byte cmd, byte[] key, byte[] val) {
		if(key.length <= 712 && val.length <= 5242880) {
			byte[] encoded = new byte[11 + key.length + val.length];
			encoded[0] = cmd;
			System.arraycopy(key, 0, encoded, 11, key.length);
			System.arraycopy(val, 0, encoded, 11 + key.length, val.length);
		}
		return null;
	}

	public static byte[][] decode(byte[] encoded) {
		return null;
	}

}
