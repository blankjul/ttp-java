package com.msu.thief;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.model.solution.Solution;
import com.msu.thief.io.thief.reader.JsonThiefProblemReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.util.Combination;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.tour.StandardTour;
import com.msu.thief.variable.tour.Tour;

public class KnapsackAnalyser  {

	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		System.out.println(asDecimal(Arrays.asList(false,false,true)));
		
		SingleObjectiveThiefProblem problem = (SingleObjectiveThiefProblem) new JsonThiefProblemReader()
				.read("resources/my_publication_coordinates_more_cities.ttp");
		Tour<?> bestTour = new StandardTour("0,6,5,4,3,2,1");
		
		List<Solution> solutions = new ArrayList<>();
		
		final int n = problem.numOfItems();
		for (int i = 0; i <= n; i++) {
			Combination combination = new Combination(n, i);
			while (combination.hasNext()) {
				List<Boolean> l = new ArrayList<>();
				for (int j = 0; j < n; j++)
					l.add(false);
				int[] entries = combination.next();
				for (int entry : entries) l.set(entry, true);
				
				Solution s = problem.evaluate(new TTPVariable(bestTour, new BooleanPackingList(l)));
				if (!s.hasConstrainViolations()) {
					solutions.add(s);
					System.out.println(s);
				}
			}
		}
		
		Collections.sort(solutions, new Comparator<Solution>() {

			@Override
			public int compare(Solution s1, Solution s2) {
				int o1 = asDecimal(((TTPVariable) s1.getVariable()).getPackingList().encode());
				int o2 = asDecimal(((TTPVariable) s2.getVariable()).getPackingList().encode());
				return Integer.compare(o1,o2);
			}
		});
		
		System.out.println("------------------------");
		for (int i = 0; i < solutions.size(); i++) {
			Solution s = solutions.get(i);
			TTPVariable var = (TTPVariable) s.getVariable();
			System.out.println(String.format("%s,%s,\"%s\"", i, s.getObjectives(0), var.getPackingList()));
		}
	}
	
	
	private static int asDecimal(List<Boolean> list) {
		int sum = 0;
		for (int i = list.size() - 1; i >= 0; i--) {
			final int exp = (list.size() - 1) - i;
			if (list.get(i) == true) sum += Math.pow(2, exp);
		}
		return sum;
	}

	


}
