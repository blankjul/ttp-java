package com.msu.thief.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.msu.thief.io.thief.reader.ThiefSingleTSPLIBProblemReader;
import com.msu.thief.io.writer.JsonThiefProblemWriter;
import com.msu.thief.problems.SingleObjectiveThiefProblem;

public class ProblemConverter {
	
	
	
	public static void main(String[] args) throws FileNotFoundException {
		
		final String name = "eil51_n150_bounded-strongly-corr_07";
		
		ThiefSingleTSPLIBProblemReader r = new ThiefSingleTSPLIBProblemReader();
		SingleObjectiveThiefProblem p = r.read("/home/julesy/workspace/" + name + ".ttp");
		
		JsonThiefProblemWriter w = new JsonThiefProblemWriter();
		w.write(p, new FileOutputStream(new File("/home/julesy/workspace/" + name + ".json")));
		
	}

}
