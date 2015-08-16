package com.cagnosolutions.samurai;

import com.cagnosolutions.samurai.db.net.Server;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class Main {

	public static void main(String[] args) {

		Server server = new Server("/tmp/samurai-aof.txt");
		server.run("localhost", 9999);

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.printf("... shutting down.\n");
			}
		});

	}
}
