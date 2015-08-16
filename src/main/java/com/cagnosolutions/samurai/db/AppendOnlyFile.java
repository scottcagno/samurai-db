package com.cagnosolutions.samurai.db;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class AppendOnlyFile {

	private static BufferedWriter writer;
	private static final List<String> queue = new ArrayList<>();

	public AppendOnlyFile(String path) {
		try {
			writer = Files.newBufferedWriter(Paths.get(path));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
