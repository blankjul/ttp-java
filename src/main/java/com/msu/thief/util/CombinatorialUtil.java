package com.msu.thief.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CombinatorialUtil {

	/**
	 * Create all the possible permutations that exist
	 */
	public static <T> Collection<List<T>> permute(Collection<T> input) {
		Collection<List<T>> output = new ArrayList<List<T>>();
		if (input.isEmpty()) {
			output.add(new ArrayList<T>());
			return output;
		}
		List<T> list = new ArrayList<T>(input);
		T head = list.get(0);
		List<T> rest = list.subList(1, list.size());
		for (List<T> permutations : permute(rest)) {
			List<List<T>> subLists = new ArrayList<List<T>>();
			for (int i = 0; i <= permutations.size(); i++) {
				List<T> subList = new ArrayList<T>();
				subList.addAll(permutations);
				subList.add(i, head);
				subLists.add(subList);
			}
			output.addAll(subLists);
		}
		return output;
	}
	
	public static <T> Collection<List<Boolean>> getAllBooleanCombinations(int n) {
		List<List<Boolean>> result = new ArrayList<>();
		for (int i = 0; i <= n; i++) {
			Combination combination = new Combination(n, i);
			while (combination.hasNext()) {
				List<Boolean> l = new ArrayList<>();
				for (int j = 0; j < n; j++) l.add(false);
				List<Integer> entries = combination.next();
				for(int entry : entries) l.set(entry, true);
				result.add(l);
			}
		}
		return result;
	}

	public static List<Integer> getIndexVector(int ending) {
		return getIndexVector(0,ending);
	}
	
	/**
	 * Returns a list like [0,1,2,3,4,5,5...,n]
	 */
	public static List<Integer> getIndexVector(int starting, int ending) {
		// create the first tour
		List<Integer> index = new ArrayList<>();
		for (int i = starting; i < ending; i++) {
			index.add(i);
		}
		return index;
	}

	
}
