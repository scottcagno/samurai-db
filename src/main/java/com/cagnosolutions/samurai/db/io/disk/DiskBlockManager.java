package com.cagnosolutions.samurai.db.io.disk;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class DiskBlockManager {

	// ----------------------------
	//   4096 KB BLOCK SIZE
	// -----------------------------
	// <OPCODE> <KEY> <VALUE>
	//    2B     64B   4030B
	// -----------------------------
	// 768MB RAM = 192,608 4K BLOCKS
	// 512MB RAM = 131,072 4K BLOCKS
}
