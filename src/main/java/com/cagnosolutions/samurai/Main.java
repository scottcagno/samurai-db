package com.cagnosolutions.samurai;

import java.io.IOException;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class Main {

	public static void main(String[] args) throws IOException {

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
		Database db = Database.INSTANCE;
		db.stmt("set foo bar baz");
		db.stmt("set barness foo bar baz ness");
		String res1 = db.stmt("get foo");
		String res2 = db.stmt("get barness");
		System.out.printf("res1: %s\n", res1);
		System.out.printf("res2: %s\n", res2);
		*/

		/*
		DiskStore store = new DiskStore(new File("diskstore.txt"), DiskStore.KB);
		byte[] output = store.get(0, 8);
		String value = new String(output);
		System.out.printf("got: %s", value);
		*/

		/*int pos = 0;
		byte[] data = "foo bar\n".getBytes();
		store.put(pos, data);

		pos += data.length;
		data = "your momma is awesome\n".getBytes();
		store.put(pos, data);

		pos += data.length;
		data = "last one\n".getBytes();
		store.put(pos, data);*/

		//store.close();

		long ts = System.currentTimeMillis();
		System.out.printf("%d (%d)\n", ts);
	}
}
