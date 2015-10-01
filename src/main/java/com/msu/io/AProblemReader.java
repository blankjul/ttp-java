package com.msu.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.msu.moo.interfaces.IProblem;

/**
 * Th
 *
 * @param <T> Resulting file that should be read by this class.
 */
public abstract class AProblemReader<T extends IProblem> {
	
	protected abstract T read_(BufferedReader br) throws IOException;
	
	public T read(String pathToFile) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(pathToFile));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(String.format("Could not open %s.", pathToFile));
		}
		T problem = read(br);
		return problem;
	}

	public T read(BufferedReader br) {
		try {
			return read_(br);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error while reading file!");
		}
	}
	
	
}
