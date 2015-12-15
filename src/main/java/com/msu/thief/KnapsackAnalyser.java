package com.msu.thief;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.model.solution.Solution;
import com.msu.thief.algorithms.AlgorithmUtil;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.factory.RandomKnapsackProblemFactory;
import com.msu.thief.problems.factory.RandomKnapsackProblemFactory.CORRELATION_TYPE;
import com.msu.thief.problems.factory.RandomSalesmanProblemFactory;
import com.msu.thief.problems.factory.RandomThiefProblemFactory;
import com.msu.thief.util.Combination;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.IntegerSetPackingList;
import com.msu.thief.variable.tour.StandardTour;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class KnapsackAnalyser  {

	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		System.out.println(asDecimal(Arrays.asList(false,false,true)));
		
		//SingleObjectiveThiefProblem problem = (SingleObjectiveThiefProblem) new JsonThiefProblemReader()
		//		.read("resources/my_publication_coordinates_more_cities_depr.ttp");
		
		RandomSalesmanProblemFactory facSalesman = new RandomSalesmanProblemFactory();
		facSalesman.setMaxCoordinates(100);
		
		RandomKnapsackProblemFactory facKnapsack = new RandomKnapsackProblemFactory();
		facKnapsack.setCorrType(CORRELATION_TYPE.UNCORRELATED);
		facKnapsack.setMaximalValue(100);
		
		RandomThiefProblemFactory fac = new RandomThiefProblemFactory(facSalesman, facKnapsack);
		
		AbstractThiefProblem thief = fac.create(7, 1, 0.2, new MyRandom(564151345));
		double R = ExperimentConverter.calcR(thief, AlgorithmUtil.calcBestTour(thief), AlgorithmUtil.calcBestPackingPlan(thief));
		SingleObjectiveThiefProblem problem = new SingleObjectiveThiefProblem(thief, R);
		
		List<Solution> solutions = new ArrayList<>();
		
		Tour<?> bestTour = new StandardTour("0,1,2,3,4,5,6");
		
		final int n = problem.numOfItems();
		for (int i = 0; i <= n; i++) {
			Combination combination = new Combination(n, i);
			while (combination.hasNext()) {
				Set<Integer> entries = new HashSet<>(combination.next());
				Solution s =  problem.evaluate(new TTPVariable(bestTour, new IntegerSetPackingList(entries, problem.numOfItems())));
				if (!s.hasConstrainViolations()) {
					solutions.add(s);
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
		System.out.println("id,obj,bin");
		for (int i = 0; i < solutions.size(); i++) {
			Solution s = solutions.get(i);
			TTPVariable var = (TTPVariable) s.getVariable();
			BooleanPackingList b = new BooleanPackingList(var.getPackingList().encode());
			System.out.println(String.format("%s,%s,\"%s\"", i, s.getObjectives(0), b.toBinaryString() ));
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
