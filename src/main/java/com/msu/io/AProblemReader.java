package com.msu.io;

import java.io.File;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.util.io.AReader;

public abstract class AProblemReader<T extends IProblem> extends AReader<T>{

	public T read(String pathToFile) {
		T problem = super.read(pathToFile);
		String name = new File(pathToFile).getName();
		problem.setName(name.substring(0, name.length() - 4));
		return problem;
	}
	
}
