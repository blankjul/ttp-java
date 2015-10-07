package com.msu.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public abstract class AWriter<T> {

	protected abstract void write_(T obj, OutputStream os) throws IOException;

	public void write(T obj, String pathToFile) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(pathToFile);
			write_(obj, fos);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(String.format("Could not write to file %s.", pathToFile));
		}
	}
	
	public void write(T obj, OutputStream os) {
		try {
			write_(obj, os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	


}
