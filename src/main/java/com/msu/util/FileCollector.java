package com.msu.util;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.log4j.Logger;

import com.msu.io.AProblemReader;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.util.Pair;

public class FileCollector {

	static final Logger logger = Logger.getLogger(FileCollector.class);
	
	// the map for all the files that should be loaded
	protected Map<Pair<String, String>, Function<String, IProblem>> map = new HashMap<>();

	public void add(Pair<String, String> regex, Function<String, IProblem> func) {
		map.put(regex, func);
	}

	public void add(Pair<String, String> regex, AProblemReader<IProblem> reader) {
		map.put(regex, new Function<String, IProblem>() {
			@Override
			public IProblem apply(String t) {
				return reader.read(t);
			}
		});
	}

	public List<IProblem> collect() {
		List<IProblem> problems = new ArrayList<>();

		for (Pair<String, String> p : map.keySet()) {

			String folder = p.first;
			String pattern = p.second;
			Function<String, IProblem> func = map.get(p);

			DirectoryStream<Path> dirStream;
			try {
				dirStream = Files.newDirectoryStream(Paths.get(folder), pattern);
				logger.info(String.format("Load files from folder %s with pattern %s.", folder, pattern));
				
				for (Path s : dirStream) {
					logger.info(String.format("Loading file %s.", s.toString()));
					problems.add(func.apply(s.toString()));
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(String.format("Could not load files from patern: %s", pattern));
			}

		}

		return problems;
	}

}
