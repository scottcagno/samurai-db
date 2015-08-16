package com.cagnosolutions.samurai.db.ngin.util;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public interface Value {

	boolean equals(Object o);
	int hashcode();
	int size();

}
