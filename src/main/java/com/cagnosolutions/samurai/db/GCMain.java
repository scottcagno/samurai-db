package com.cagnosolutions.samurai.db;

import com.cagnosolutions.samurai.db.engine.util.MemoryStats;
import com.gs.collections.api.map.MutableMap;
import com.gs.collections.impl.list.mutable.primitive.ByteArrayList;
import com.gs.collections.impl.map.mutable.ConcurrentHashMap;

import java.util.Random;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class GCMain {

	public static void main(String[] args) throws InterruptedException {

		System.out.println(":: RUNNING MEMORY TEST");
		MemoryStats.print();
		Thread.sleep(1000);
		System.out.println(":: ADDING RECORDS TO INDEX");
		long ts1 = System.currentTimeMillis();
		Random random = new Random();
		MutableMap<ByteArrayList, ByteArrayList> index = new ConcurrentHashMap<>();
		for (int i = 0; i < 100000; i++) {
			if(i==9) {
				byte[] foo = new byte[]{'f', 'o', 'o'};
				byte[] bar = new byte[]{'b', 'a', 'r'};
				index.put(new ByteArrayList(foo), new ByteArrayList(foo));
			} else {
				int j = len(i);
				byte[] key = new byte[j+1]; random.nextBytes(key);
				byte[] val = new byte[j+1]; random.nextBytes(val);
				index.put(new ByteArrayList(key), new ByteArrayList(val));
			}
		}
		long ts2 = System.currentTimeMillis()-ts1;
		System.out.println(":: RUNNING MEMORY TEST");
		MemoryStats.print();
		Thread.sleep(1000);

		System.out.printf("index size: %d\n", index.size());
		System.out.printf("program ran in %dms\n", ts2);

		ByteArrayList key = new ByteArrayList(new byte[]{'f','o','o'});
		ByteArrayList val = index.get(key);
		System.out.printf("GOT -> {%s:%s}\n", key, val);
		System.out.println(key.containsAll(new byte[]{'f','o'}));

	}

	public static int len(int n) {
		if (n < 100000)
		{
			// 5 or less
			if (n < 100)
			{
				// 1 or 2
				if (n < 10)
					return 1;
				else
					return 2;
			}
			else
			{
				// 3 or 4 or 5
				if (n < 1000)
					return 3;
				else
				{
					// 4 or 5
					if (n < 10000)
						return 4;
					else
						return 5;
				}
			}
		}
		else
		{
			// 6 or more
			if (n < 10000000)
			{
				// 6 or 7
				if (n < 1000000)
					return 6;
				else
					return 7;
			}
			else
			{
				// 8 to 10
				if (n < 100000000)
					return 8;
				else
				{
					// 9 or 10
					if (n < 1000000000)
						return 9;
					else
						return 10;
				}
			}
		}
	}
}
