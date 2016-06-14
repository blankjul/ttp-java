package com.msu.thief.util;

import java.util.ArrayList;
import java.util.List;

public class Combination {
	private int n, r;
	private int[] index;
	private boolean hasNext = true;
	protected List<Integer> l;

	public Combination( List<Integer> l, int r) {
		this(l.size(),r);
		this.l = l;
	}
	
	public Combination(int n, int r) {
		this.n = n;
		this.r = r;
		index = new int[r];
		for (int i = 0; i < r; i++) index[i] = i;
	}

	public boolean hasNext() {
		return hasNext;
	}

	private void moveIndex() {
		int i = rightmostIndexBelowMax();
		if (i >= 0) {
			index[i] = index[i] + 1;
			for (int j = i + 1; j < r; j++)
				index[j] = index[j - 1] + 1;
		} else
			hasNext = false;
	}

	public List<Integer> next() {
		if (!hasNext) return null;
		List<Integer> result = new ArrayList<>(r);
		for (int i = 0; i < r; i++) {
			if (l == null) result.add(index[i]);
			else result.add(l.get(index[i]));
		}
		moveIndex();
		return result;
	}

	// return int,the index which can be bumped up.
	private int rightmostIndexBelowMax() {
		for (int i = r - 1; i >= 0; i--)
			if (index[i] < n - r + i)
				return i;
		return -1;
	}
}
