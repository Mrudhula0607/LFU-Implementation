package com.lfu.main;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class CacheTest
{
	@Test
	public void lfuTest()
	{
		Cache cache = new Cache();
		cache.insert(1, 1);
		cache.insert(2, 2);
		cache.access(2);
		
		assertEquals(1, cache.getLfuItem().data);
	}
	
	@Test
	public void lfuHasKeyTest()
	{
		Cache cache = new Cache();
		cache.insert(1, 1);
		cache.insert(2, 2);
		cache.access(2);
		
		assertTrue(cache.hasKey(2));
	}
	
	@Test
	public void getTopKItemsTest()
	{
		Cache cache = new Cache();
		cache.insert(1, 1);
		cache.access(1);
		cache.access(1);
		cache.insert(2, 2);
		cache.access(2);
		cache.insert(3, 3);
		
		assertEquals(Arrays.asList(1, 2), cache.getTopKItems(2));
	}
}
