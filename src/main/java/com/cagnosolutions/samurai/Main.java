package com.cagnosolutions.samurai;

import com.cagnosolutions.samurai.db.engine.util.Bytes;
import com.cagnosolutions.samurai.db.engine.util.MemoryStats;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {

		/*
		Server server = new Server("/tmp/samurai-aof.txt");
		server.run("localhost", 9999);

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.printf("... shutting down.\n");
			}
		});
		*/

		/*
		System.out.println("----- PROGRAM STARTED -----");

		System.out.println(":: PAUSED FOR 3 SECONDS");
		Thread.sleep(3000);

		System.out.println(":: RUNNING MEMORY TEST");
		MemoryStats.print();
		Thread.sleep(1000);

		System.out.println(":: INSTANTIATING DATABASE");
		Database db = new Database("samurai-db.aof.txt", 1024*512);
		System.out.println(":: PAUSED FOR 3 SECONDS");
		Thread.sleep(3000);

		System.out.println(":: RUNNING MEMORY TEST");
		MemoryStats.print();
		Thread.sleep(1000);

		System.out.println(":: PERFORMING 'GET' CALL ON DATABASE");
		String res = db.stmt("GET K:85");

		System.out.println(":: RUNNING MEMORY TEST");
		MemoryStats.print();
		Thread.sleep(1000);

		System.out.println(":: PRINTING RESULT OF 'GET' CALL");
		System.out.println(res);

		System.out.println("----- PROGRAM STOPPED -----");
		*/



		System.out.println(":: RUNNING MEMORY TEST");
		MemoryStats.print();
		Thread.sleep(1000);

		System.out.println(":: ADDING RECORDS TO INDEX");
		Map<Bytes,byte[]> index = new HashMap<>();
		for (int i = 0; i < 128; i++)
			index.put(new Bytes(new byte[]{(byte)(i)}), new byte[]{(byte)(i)});
		System.out.println(":: RUNNING MEMORY TEST");
		MemoryStats.print();
		Thread.sleep(1000);
		
		for (int i = 0; i < 25; i++) {
			System.out.printf("%d\n", index.get(new Bytes(new byte[]{(byte) (i)}))[0]);
		}





		/*System.out.println(index.size());
		byte[] res1 = index.get(new Str((byte) 1));
		System.out.printf("res1: %s\n", new String(res1));
		byte[] res2 = index.get(new Str((byte) 267));
		System.out.printf("res1: %s\n", new String(res2));
		byte[] res3 = index.get(new Str((byte) 82));
		System.out.printf("res1: %s\n", new String(res3));
		byte[] res4 = index.get(new Str((byte) 44));
		System.out.printf("res1: %s\n", new String(res4));
		byte[] res5 = index.get(new Str((byte) 249000));
		System.out.printf("res1: %s\n", new String(res5));*/


		//long ts = System.currentTimeMillis();
		//System.out.println(db.stmt("GET K:85"));
		//for(int i = 0; i < 10000; i++) {
		//	db.stmt("put K:%d THE %d VALUE", i, i);
		//}
		//System.out.printf("took %dms to insert 10,000 records\n", System.currentTimeMillis() - ts);
	}

	public static final class Str {

		private final byte[] bytes;
		private int hash;

		public Str(byte[] bytes) {
			this.bytes = bytes;
		}

		public Str(byte b) {
			this.bytes = new byte[]{b};
		}

		public int hashCode() {
			int h = hash;
			if (h == 0 && bytes.length > 0) {
				byte[] val = bytes;
				for (int i = 0; i < bytes.length; i++)
					h = 31 * h + val[i];
				hash = h;
			}
			return h;
		}

		public boolean equals(Object that) {
			if (this == that)
				return true;
			if (that instanceof Str) {
				Str thatStr = (Str)that;
				int n = bytes.length;
				if (n == thatStr.bytes.length) {
					byte[] v1 = bytes;
					byte[] v2 = thatStr.bytes;
					int i = 0;
					while (n-- != 0) {
						if (v1[i] != v2[i])
							return false;
						i++;
					}
					return true;
				}
			}
			return false;
		}
	}
}


