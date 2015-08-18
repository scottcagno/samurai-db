package com.cagnosolutions.samurai;

import com.cagnosolutions.samurai.db.engine.Database;

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

		Database db = new Database("samurai-db.aof.txt", 1024*512);
		long ts = System.currentTimeMillis();
		//System.out.println(db.stmt("GET K:85"));
		for(int i = 0; i < 10000; i++) {
			db.stmt("put K:%d THE %d VALUE", i, i);
		}
		System.out.printf("took %dms to insert 10,000 records\n", System.currentTimeMillis() - ts);
	}
}
