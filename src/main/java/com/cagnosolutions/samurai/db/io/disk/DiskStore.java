package com.cagnosolutions.samurai.db.io.disk;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class DiskStore {

	public static final int KB = 1024;
	public static final int MB = 1024*KB;

	private RandomAccessFile in;
	private FileChannel ch;
	private MappedByteBuffer buffer;
	private final static int DEFAULT_DB_SIZE = 1024 * 1024;

	private boolean needDeserial = false;

	public DiskStore(File dbFile) throws IOException {
		this(dbFile, 0);
	}

	public DiskStore(File dbFile, int dbSize) throws IOException {
		if (!dbFile.exists()) {
			dbFile.createNewFile();
		}
		in = new RandomAccessFile(dbFile, "rw");
		ch = in.getChannel();
		long mappedSize = dbFile.length();
		if (mappedSize <= 0) {
			if (dbSize <= 0) {
				mappedSize = DEFAULT_DB_SIZE;
			} else {
				mappedSize = dbSize;
			}
		} else {
			this.needDeserial = true;
		}
		buffer = ch.map(FileChannel.MapMode.READ_WRITE, 0, mappedSize);
	}

	public void close() throws IOException {
		ch.close();
		in.close();
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

	public boolean isNeedDeserial() {
		return needDeserial;
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

}
