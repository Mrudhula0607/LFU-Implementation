package com.lfu.main;

import java.util.HashSet;
import java.util.Set;

public class FrequencyNode
{
	int value;
	FrequencyNode prev;
	FrequencyNode next;
	Set<Integer> items = new HashSet<>();
}
