package com.msu.util;

import java.util.Collection;
import java.util.HashSet;

public class Util {
	

	/**
	 * Return the first duplicate that is found on a given collection
	 * @param c collection of a generic type.
	 * @return first found duplicate
	 */
	public static <T> T getDuplicate(HashSet<T> hash, Collection<T> c) {
		for(T element : c) {
			if (hash.contains(element)) {
				return element;
			}
			hash.add(element);
		}
		return null;
	}

	
	


	
	
}
