package com.moo.ttp.util;



public class Factory {

	public static Object create(String fullType) {
		Class<?> c;
		try {
			c = Class.forName(fullType);
			Object pc = (Object) c.newInstance();
			return pc;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static <T> T create(Class<T> c, String fullType)  {
		try {
			return (T) Class.forName(fullType).newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

}
