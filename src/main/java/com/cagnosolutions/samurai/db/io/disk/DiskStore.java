package com.cagnosolutions.samurai.db.io.disk;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

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

	/**
	 * NEW METHODS THAT I WROTE
	 */

	public List<String> readLines() {
		StringBuffer bytes = new StringBuffer();
		List<String> lines = new ArrayList<>();
		byte NULLBYTE = 0x00, NEWLINE = 0x0A, b = -1;
		while(b != NULLBYTE && buffer.hasRemaining()) {
			while(b != NEWLINE) {
				b = buffer.get();
				bytes.append((char) b);
			}
			lines.add(bytes.toString());
			bytes.delete(0, bytes.length());
		}
		if((b == -1) && (bytes.length() == 0))
			return null;
		buffer.limit(buffer.position());
		return lines;
	}

	public int findNewline() {
		byte b = this.buffer.get(this.buffer.position());
		while(b != '\n' && (this.buffer.remaining() > this.buffer.position()-2))
			b = this.buffer.get();
		return this.buffer.position();
	}

	public void initLimit() {
		byte b = -1;
		while(b != 0x00)
			b = this.buffer.get();
		this.buffer.flip();
	}

	public final String readLine() {
		//System.out.printf("%%%%%%%%%%%%%%%%%%%%%% >>> position: %d\n", this.buffer.position());
		System.out.printf("%%%%%%%%%%%%%%%%%%%%%% >>> loaded, postion, limit, capacity: %b, %d, %d, %d\n",
				this.buffer.isLoaded(), this.buffer.position(), this.buffer.limit(), this.buffer.capacity());

		StringBuffer input = new StringBuffer();
		byte b = -1;
		boolean eol = false;
		while (!eol) {
			switch (b = this.buffer.get()) {
				case -1:
				case '\n':
					eol = true;
					break;
				case '\r':
					eol = true;
					int cur = this.buffer.position();
					if((this.buffer.get()) != '\n')
						if(this.buffer.position() >= 0)
							this.buffer.position(cur);
					break;
				default:
					input.append((char) b);
					break;
			}
		}
		if((b == -1) && (input.length() == 0))
			return null;
		return input.toString();
	}

}
