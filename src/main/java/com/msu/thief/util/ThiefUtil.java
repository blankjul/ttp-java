package com.msu.thief.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class ThiefUtil {
	

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

	/**
	 * @return all the files in one folder. If it's a file just the file is returned
	 */
	public static List<String> getFiles(String folder) {
		List<String> result = new ArrayList<>();
		try {
			Files.walk(Paths.get(folder)).forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					String file = filePath.toString();
					result.add(file);
				}
			});
		} catch (IOException e) {
			return new ArrayList<>();
		}
		return result;
		
	}
	
	
	
	public static <K, V extends Comparable<? super V>> LinkedHashMap<K, V> sortByValue(Map<K, V> map) {
		LinkedHashMap<K, V> result = new LinkedHashMap<>();
		Stream<Entry<K, V>> st = map.entrySet().stream();
		st.sorted(Comparator.comparing(e -> e.getValue())).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
		return result;
	}


	
	
}
