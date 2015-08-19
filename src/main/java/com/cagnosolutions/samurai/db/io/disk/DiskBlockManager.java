package com.cagnosolutions.samurai.db.io.disk;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import static java.nio.channels.FileChannel.*;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class DiskBlockManager {

	// -----------------------------
	//   4096 KB BLOCK SIZE (4KB)
	// -----------------------------
	// <KEY> <VALUE>
	//  64B   4032B
	// -----------------------------
	// 768MB RAM = 192,608 4K BLOCKS
	// 512MB RAM = 131,072 4K BLOCKS

	private static final int BLOCK_SIZE = 1024*4;
	private static final int KEY_BLOCK = 64;
	private static final int VAL_BLOCK = 4032;
	private static final long DEFAULT_DB_SIZE = BLOCK_SIZE*16;

	private RandomAccessFile in;
	private FileChannel ch;
	private MappedByteBuffer buffer;

	public DiskBlockManager(File dbFile, int dbSize) throws IOException {
		if (!dbFile.exists())
			dbFile.createNewFile();
		in = new RandomAccessFile(dbFile, "rw");
		ch = in.getChannel();
		long mappedSize = dbFile.length();
		if(mappedSize <= 0)
			if (dbSize <= 0)
				mappedSize = DEFAULT_DB_SIZE;
			else
				mappedSize = dbSize;
		buffer = ch.map(MapMode.READ_WRITE, 0, mappedSize);
	}

	public void close() throws IOException {
		ch.close();
		in.close();
	}

	public byte[] get(int block) {
		buffer.position(block*BLOCK_SIZE);
		byte[] value = new byte[BLOCK_SIZE];
		buffer.get(value);
		return value;
	}

	public byte[] get() {
		byte[] value = new byte[BLOCK_SIZE];
		buffer.get(value);
		return value;
	}

	public void position(int block) {
		buffer.position(block*BLOCK_SIZE);
	}









	public void get(byte[] bytes) {
		this.buffer.get(bytes);
	}

	public byte[] get(int startIndex, int size) {
		byte[] value = new byte[size];
		buffer.position(startIndex);
		buffer.get(value);
		return value;
	}

	public ByteBuffer getBuffer() {
		return this.buffer;
	}

	public void put(int startIndex, byte value) {
		buffer.position(startIndex);
		buffer.put(value);
	}

	public void put(int startIndex, byte[] value) {
		buffer.position(startIndex);
		buffer.put(value);
	}

	public int remaining() {
		return this.buffer.remaining();
	}

	public void rewind() {
		this.buffer.rewind();
	}


	public void put(byte[] key, byte[] value) {

	}
}
