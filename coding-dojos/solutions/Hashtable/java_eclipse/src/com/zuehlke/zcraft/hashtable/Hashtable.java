package com.zuehlke.zcraft.hashtable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hashtable {
	private List<Long>[] array;
	private int elementCount = 0;

	@SuppressWarnings("unchecked")
	public Hashtable(int size) {
		array = new List[size];
	}

	public void add(long val) {
		int index = hash(val);
		List<Long> entry = array[index];
		if (entry == null) {
			entry = new ArrayList<>();
			array[index] = entry;
		}
		if (!entry.contains(val)) {
			entry.add(val);
			elementCount++;

			resize();
		}
	}

	private void resize() {
		if (elementCount >= array.length * 0.75) {
			List<Long>[] oldArray = array;
			array = new List[oldArray.length * 2];

			elementCount = 0;
			for (List<Long> elem : oldArray) {
				if (elem != null) {
					for (Long l : elem) {
						add(l);
					}
				}
			}
		}
	}

	int hash(long val) {
		return (int) Math.abs((val ^ 99721) * 91019 % array.length);
	}

	public boolean contains(long val) {
		int index = hash(val);
		List<Long> entry = array[index];
		if (entry == null) {
			return false;
		}
		return entry.contains(val);
	}

	public boolean remove(long val) {
		int index = hash(val);
		List<Long> entry = array[index];

		if (entry == null) {
			return false;
		}

		boolean removed = entry.remove(val);
		if (removed) {
			elementCount--;
		}
		return removed;
	}

	public int getElementCount() {
		return elementCount;
	}
}
