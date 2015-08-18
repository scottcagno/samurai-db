package com.cagnosolutions.samurai.db.engine.util;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class MemoryStats {

	public static void print() {

		int mb = 1024*1024;

		// get runtime reference from system
		Runtime runtime = Runtime.getRuntime();

		System.out.println("- - - [ MEMORY STATS ] - - -");

		// used memory
		System.out.printf("Used Memory: %d MB\n", (runtime.totalMemory() - runtime.freeMemory()) / mb);

		// free memory
		System.out.printf("Free Memory: %d MB\n", runtime.freeMemory() / mb);

		// total available memory
		System.out.printf("Total Memory: %d MB\n", runtime.totalMemory() / mb);

		// maximum available memory
		System.out.printf("Max Memory: %d MB\n", runtime.maxMemory() / mb);

		System.out.println("- - - - - - - - - - - - - - -");
	}

}
