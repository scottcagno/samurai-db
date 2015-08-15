package com.cagnosolutions.samurai.db.common;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class Message {

	public static final byte NUL = 0x00;
	public static final byte ADD = 0x01;
	public static final byte SET = 0x02;
	public static final byte GET = 0x03;
	public static final byte DEL = 0x04;
	public static final int KB = 1024;

	// encode a message as a byte array (stream)
	public static byte[] encMsg(byte cmd, byte[] key, byte[] val) {
		// assemble header
		byte[] header = new byte[9];
		byte[] keylen = encLen(key.length, KB); 	// max key size 1KB
		byte[] vallen = encLen(val.length, KB*512); // max val size 512KB
		for (int i = 0; i < header.length; i++)
			if(i == 0)
				header[i] = cmd;
			else if(i > 0 && i <= 4)
				header[i] = keylen[i-1];
			else if(i > 4 && i <= 9)
				header[i] = vallen[i-5];

		// setup payload
		byte[] payload = new byte[header.length + key.length + val.length];

		// copy header into payload
		System.arraycopy(header, 0, payload, 0, header.length);

		// copy key into payload
		System.arraycopy(key, 0, payload, header.length, key.length);

		// copy value into payload
		System.arraycopy(val, 0, payload, header.length + key.length, val.length);

		return payload;
	}

	// decode a message from a byte array (stream)
	public static byte[][] decMsg(byte[] payload) {

		// decode the key and value lengths
		int keylen = decLen(payload, 1);
		int vallen = decLen(payload, 5);

		// create two dimensional message array for decoded values
		byte[][] msg = new byte[][]{ new byte[1], new byte[keylen], new byte[vallen] };

		// populate message from payload
		System.arraycopy(payload, 0, msg[0], 0, 1);
		System.arraycopy(payload, 9, msg[1], 0, keylen);
		System.arraycopy(payload, 9 + keylen, msg[2], 0, vallen);

		return msg;
	}

	// encode integer into byte array, ensure len does not exceed max
	public static byte[] encLen(int len, int max) {
		if(len <= max)
			return new byte[]{
				(byte)(len & 0xff),
				(byte)((len >> 8) & 0xff),
				(byte)((len >> 16) & 0xff),
				(byte)((len >> 24) & 0xff)
			};
		return new byte[]{0x00,0x00,0x00,0x00};
	}

	// decode byte array into integer value
	public static int decLen(byte[] blen, int offset) {
		int b4 = blen[offset + 3] >= 0 ? blen[offset + 3] : 256 + blen[offset + 3];
		int b3 = blen[offset + 2] >= 0 ? blen[offset + 2] : 256 + blen[offset + 2];
		int b2 = blen[offset + 1] >= 0 ? blen[offset + 1] : 256 + blen[offset + 1];
		int b1 = blen[offset] >= 0 ? blen[offset] : 256 + blen[offset];
		return b1 | (b2 << 8) | (b3 << 16) | (b4 << 24);
	}

	public static String display(byte[] bytes) {
		StringBuilder sb = new StringBuilder("[ ");
		for (byte aByte : bytes)
			sb.append(String.format("%c", (char) aByte)).append(" ");
		return sb.append("]").toString();
	}

	public static String display(byte[][] multibytes) {
		StringBuilder sb = new StringBuilder("[ ");
		for (byte[] multibyte : multibytes)
			sb.append(display(multibyte)).append(" ");
		return sb.append("]").toString();
	}

}
