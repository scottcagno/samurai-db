package com.cagnosolutions.samurai.db.engine.common;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class Message {

	private byte opcode;
	private BytesKey key;
	private BytesVal val;

	public Message(byte opcode) {
		this.opcode = opcode;
		this.key = null;
		this.val = null;
	}

	public Message(byte opcode, byte[] key) {
		this.opcode = opcode;
		this.key = new BytesKey(key);
		this.val = null;
	}

	public Message(byte opcode, BytesKey key) {
		this.opcode = opcode;
		this.key = key;
		this.val = null;
	}

	public Message(byte opcode, byte[] key, byte[] val) {
		this.opcode = opcode;
		this.key = new BytesKey(key);
		this.val = new BytesVal(val);
	}

	public Message(byte opcode, BytesKey key, BytesVal val) {
		this.opcode = opcode;
		this.key = key;
		this.val = val;
	}

	public byte getOpcode() {
		return opcode;
	}

	public void setOpcode(byte opcode) {
		this.opcode = opcode;
	}

	public BytesKey getKey() {
		return key;
	}

	public void setKey(BytesKey key) {
		this.key = key;
	}

	public void setKey(byte[] key) {
		this.key = new BytesKey(key);
	}

	public BytesVal getVal() {
		return val;
	}

	public void setVal(BytesVal val) {
		this.val = val;
	}

	public void setVal(byte[] val) {
		this.val =new BytesVal(val);
	}

	public byte[] getHeader() {
		byte[] header = new byte[7];
		header[0] = opcode;
		System.arraycopy(enc(key.getLength()), 0, header, 1, 3);
		System.arraycopy(enc(val.getLength()), 0, header, 4, 6);
		return header;
	}

	public byte[] getBody() {
		byte[] body = new byte[key.getLength() + val.getLength()];
		System.arraycopy(key.getBytes(), 0, body, 0, key.getLength());
		System.arraycopy(val.getBytes(), 0, body, key.getLength(), val.getLength());
		return body;
	}

	public byte[] getPayload() {
		byte[] payload = new byte[7 + key.getLength() + val.getLength()];
		System.arraycopy(getHeader(), 0, payload, 0, 7);
		System.arraycopy(getBody(), 0, payload, 7, payload.length-7);
		return payload;
	}

	private byte[] enc(int len) {
		return new byte[]{
			(byte)(len & 0xff),
			(byte)((len >> 8) & 0xff),
			(byte)((len >> 16) & 0xff)
		};
	}

	private int dec(byte[] blen) {
		int b3 = blen[2] >= 0 ? blen[2] : 256 + blen[2];
		int b2 = blen[1] >= 0 ? blen[1] : 256 + blen[1];
		int b1 = blen[0] >= 0 ? blen[0] : 256 + blen[0];
		return b1 | (b2 << 8) | (b3 << 16);
	}
}
