package com.cagnosolutions.samurai.db.ngin;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public interface DataSet {

	// all methods that both java.util.Map and java.util.Collection share

	boolean equals(Object o);
	int hashcode();
	int size();
	void clear();
	boolean isEmpty();

}
