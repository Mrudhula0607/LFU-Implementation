package com.lfu.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Cache
{
	public Cache()
	{
		bykey = new HashMap<>();
		freq_head = newFrequencyNode();
	}
	
	public int access(int key)
	{
		Item tmp = bykey.get(key);
		if (tmp == null)
		{
			throw new RuntimeException("No such key");
		}
		FrequencyNode freq = tmp.parent;
		FrequencyNode next_freq = freq.next;
		
		if (next_freq.equals(freq_head) || next_freq.value != freq.value + 1)
		{
			next_freq = getNewNode(freq.value + 1, freq, next_freq);
		}
		next_freq.items.add(key);
		tmp.parent = next_freq;
		
		freq.items.remove(key);
		if (freq.items.size() == 0)
		{
			deleteNode(freq);
		}
		
		return tmp.data;
	}
	
	public void insert(int key, int value)
	{
		if (bykey.containsKey(key))
		{
			throw new RuntimeException("Key already exists");
		}
		
		FrequencyNode freq = freq_head.next;
		if (freq.value != 1)
		{
			freq = getNewNode(1, freq_head, freq);
		}
		
		freq.items.add(key);
		bykey.put(key, newItem(value, freq));
	}
	
	public boolean hasKey(int key)
	{
		return bykey.containsKey(key);
	}
	
	public Item getLfuItem()
	{
		if (bykey.size() == 0)
		{
			throw new RuntimeException("The set is empty");
		}
		
		return bykey.get(freq_head.next.items.iterator().next());
	}
	
	public List<Integer> getTopKItems(int k)
	{
		int idx = k;
		List<Integer> elements = new ArrayList<>();
		FrequencyNode tmp = freq_head.prev;
		while (idx > 0)
		{
			Iterator<Integer> itemsIter = tmp.items.iterator();
			while(itemsIter.hasNext() && idx > 0)
			{
				elements.add(itemsIter.next());
				idx --;
			}
			tmp = tmp.prev;
		}
		return elements;
	}
	
	private FrequencyNode newFrequencyNode()
	{
		FrequencyNode node = new FrequencyNode();
		node.value = 0 ;
		node.items = new HashSet<>();
		node.prev = node;
		node.next = node;
		
		return node;
	}
	
	private Item newItem(int data, FrequencyNode parent)
	{
		Item item = new Item();
		item.data = data;
		item.parent = parent;
		
		return item;
	}
	
	private FrequencyNode getNewNode(int value, FrequencyNode prev, FrequencyNode next)
	{
		FrequencyNode newNode = new FrequencyNode();
		newNode.value = value;
		newNode.prev = prev;
		newNode.next = next;
		prev.next = newNode;
		next.prev = newNode;
		
		return newNode;
	}
	
	private void deleteNode(FrequencyNode node)
	{
		node.prev.next = node.next;
		node.next.prev = node.prev;
	}
	
	private Map<Integer, Item> bykey ;
	private FrequencyNode freq_head;
}
