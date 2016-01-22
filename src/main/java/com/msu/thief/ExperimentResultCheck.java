package com.msu.thief;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.model.solution.Solution;
import com.msu.thief.io.thief.reader.ThiefSingleTSPLIBProblemReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.util.visualization.VariableAsHtml;


public class ExperimentResultCheck {
	
	
	final public static String NAME = "tsma";
	

	public static void main(String[] args) throws IOException  {

		BasicConfigurator.configure();

		SingleObjectiveThiefProblem thief = (SingleObjectiveThiefProblem) new ThiefSingleTSPLIBProblemReader()
				.read("../ttp-benchmark/TSPLIB/berlin52-ttp/berlin52_n51_bounded-strongly-corr_01.ttp");

		
		Map<String, TTPVariable> m = new HashMap<>();
		m.put("bilevel", TTPVariable.createFromString("[0, 48, 31, 44, 18, 40, 7, 8, 9, 42, 32, 50, 10, 51, 13, 12, 46, 25, 26, 27, 11, 24, 3, 5, 14, 4, 23, 47, 37, 36, 39, 38, 35, 34, 33, 43, 45, 15, 28, 49, 19, 22, 29, 1, 6, 41, 20, 16, 2, 17, 30, 21];[0, 1, 2, 3, 42, 15, 16, 48, 17, 49, 18, 50, 19, 20, 29]"));
		m.put("best", TTPVariable.createFromString("[0, 48, 31, 2, 16, 41, 6, 1, 29, 28, 15, 45, 36, 39, 37, 47, 23, 4, 14, 5, 3, 24, 11, 27, 26, 25, 46, 12, 13, 51, 10, 50, 32, 42, 9, 8, 7, 40, 18, 44, 17, 30, 20, 22, 19, 49, 43, 33, 38, 35, 34, 21];[0, 32, 1, 33, 2, 34, 3, 42, 48, 17, 49, 18, 50, 19, 20]"));
		m.put("tsma", TTPVariable.createFromString("[0,48,31,44,18,40,7,8,9,42,32,50,10,51,13,12,46,25,26,27,11,24,3,5,14,4,23,37,39,38,36,47,45,15,28,29,1,6,41,20,16,2,17,30,22,19,49,43,33,34,35,21];[1,2,3,17,18,19,20,32,33,34,42,48,49,50]"));

		
		Solution<TTPVariable> s = thief.evaluate(m.get(NAME));
		System.out.println(s);

		
		VariableAsHtml.write(thief, s, String.format("../ttp-results/berlin_%s.html", NAME));
		//System.out.println(writer.getResult());
		
	}
}
