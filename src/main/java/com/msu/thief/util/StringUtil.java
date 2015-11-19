package com.msu.thief.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
	
	public static List<Integer> parseAsIntegerList(String s) {
		s = s.replace("[", "").replace("]", "");
		String[] values = s.split(",");
		List<Integer> l = new ArrayList<>();
		for (String value : values) l.add(Integer.valueOf(value.trim()));
		return l;
	}
	
	public static List<Boolean> parseAsBooleanList(String s) {
		s = s.replace("[", "").replace("]", "");
		String[] values = s.split(",");
		List<Boolean> l = new ArrayList<>();
		for (String value : values) {
			value = value.trim();
			if (value.equals("true")) l.add(true);
			else if (value.equals("false")) l.add(false);
			else if (value.equals("0")) l.add(false);
			else if (value.equals("1")) l.add(true);
			else throw new RuntimeException(String.format("Error while parsing vector: %s is not convertable.", value));
		}
		return l;
	}

}
