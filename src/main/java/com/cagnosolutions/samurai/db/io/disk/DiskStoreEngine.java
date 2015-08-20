package com.cagnosolutions.samurai.db.io.disk;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class DiskStoreEngine {

	private static DiskStore diskStore;
	private final Lock writeLock = new ReentrantLock();
	private int position = 0;

	private final static Map<String,Integer> INDEX = new HashMap<>();

	public DiskStoreEngine(String path, int fileSize) {
		try {
			diskStore = new DiskStore(new File(path), fileSize);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public byte[] generatePutRecord(String key, byte[] value) {
		return String.format("%d PUT %s %s\n", System.currentTimeMillis(), key, value).getBytes();
	}

	public byte[] generateDelRecord(String key) {
		return String.format("%d DEL %s\n", System.currentTimeMillis(), key).getBytes();
	}

	public void put(String key, byte[] value) {
		byte[] record = generatePutRecord(key, value);
		if(record.length >= diskStore.remaining()) {
			try {
				diskStore.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			throw new StackOverflowError(record.length + " exceeds bytes remaining (" + diskStore.remaining() +
					"), discarding last call and closing file.");
		}
		put(record);
	}

	private void put(byte[] record) {
		try {
			writeLock.lock();
			diskStore.put(position, record);
			position += record.length;
		} finally {
			writeLock.unlock();
		}
	}

	public void del(String key) {
		byte[] record = generateDelRecord(key);
		if(record.length >= diskStore.remaining()) {
			try {
				diskStore.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			throw new StackOverflowError(record.length + " exceeds bytes remaining (" + diskStore.remaining() +
					"), discarding last call and closing file.");
		}
		del(record);
	}

	private void del(byte[] record) {
		try {
			writeLock.lock();
			diskStore.put(position, record);
			position += record.length;
		} finally {
			writeLock.unlock();
		}
	}

	public List<String> readLines() {
		return diskStore.readLines();
	}

}
