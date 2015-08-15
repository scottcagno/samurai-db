package com.cagnosolutions.samurai;

import com.cagnosolutions.samurai.db.net.Server;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class Main {

	public static void main(String[] args) {

		new Server().run("localhost", 9999);

	}
}
