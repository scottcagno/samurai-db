package com.cagnosolutions.samurai;

import com.cagnosolutions.samurai.db.ngin.Database;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class Main {

	public static void main(String[] args) {

		/*Server server = new Server("/tmp/samurai-aof.txt");
		server.run("localhost", 9999);

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.printf("... shutting down.\n");
			}
		});*/

		Database db = Database.INSTANCE;

		db.stmt("set foo bar baz");
		db.stmt("set barness foo bar baz ness");
		String res1 = db.stmt("get foo");
		String res2 = db.stmt("get barness");
		System.out.printf("res1: %s\n", res1);
		System.out.printf("res2: %s\n", res2);

	}
}
