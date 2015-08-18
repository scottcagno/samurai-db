package com.cagnosolutions.samurai.db.io.disk;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

	public DiskStoreEngine(String path, int fileSize) {
		try {
			diskStore = new DiskStore(new File(path), fileSize);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public byte[] generatePutRecord(String key, String value) {
		return String.format("%d PUT %s %s\n", System.currentTimeMillis(), key, value).getBytes();
	}

	public byte[] generateDelRecord(String key) {
		return String.format("%d DEL %s\n", System.currentTimeMillis(), key).getBytes();
	}

	public void put(String key, String value) {
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

	public List<String> read() {
		position = 0;
		int startIndex = 0, stopIndex = 0;
		List<String> data = new ArrayList<>();
		while(stopIndex < diskStore.remaining()-1) {
			stopIndex = diskStore.findNewline();
			data.add(new String(diskStore.get(startIndex, stopIndex-startIndex)));
			startIndex = stopIndex;
		}
		return data;
	}

}
