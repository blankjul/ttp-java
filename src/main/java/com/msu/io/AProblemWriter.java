package com.msu.io;

import com.msu.moo.interfaces.IProblem;
public abstract class AProblemWriter<T extends IProblem> {

	public abstract void write(T obj, String path) ;
	
	
}
