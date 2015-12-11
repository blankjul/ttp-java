package com.msu.thief.algorithms.factory;

import org.apache.log4j.BasicConfigurator;

import com.msu.thief.evaluator.profit.NoDroppingEvaluator;
import com.msu.thief.io.writer.JsonThiefProblemWriter;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.factory.AKnapsackProblemFactory;
import com.msu.thief.problems.factory.ASalesmanProblemFactory;
import com.msu.thief.problems.factory.ClusteredSalesmanProblemFactory;
import com.msu.thief.problems.factory.RandomKnapsackProblemFactory;
import com.msu.thief.problems.factory.RandomThiefProblemFactory;
import com.msu.thief.problems.factory.RandomKnapsackProblemFactory.CORRELATION_TYPE;
import com.msu.util.MyRandom;

public class ProblemFactory {

	final public static Integer[] CITIES = new Integer[] { 10, 25, 50, 100 };

	final public static Integer[] ITEMS_PER_CITY = new Integer[] { 1, 5, 10, 25, 50 };

	final public static Double[] CAPACITY = new Double[] { 0.1, 0.4, 0.6, 0.9 };

	final public static CORRELATION_TYPE[] TYPES = new CORRELATION_TYPE[] { CORRELATION_TYPE.UNCORRELATED, CORRELATION_TYPE.WEAKLY_CORRELATED,
			CORRELATION_TYPE.STRONGLY_CORRELATED };
	
	final public static long RANDOM_SEED = 123456;
	

	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		for (Integer cities : CITIES) {
			for (Integer itemsPercity : ITEMS_PER_CITY) {

				for (CORRELATION_TYPE type : TYPES) {

					for (double rate : CAPACITY) {
						ASalesmanProblemFactory facSalesman = new ClusteredSalesmanProblemFactory(3);
						//ASalesmanProblemFactory facSalesman = new ClusteredSalesmanProblemFactory(3);
						AKnapsackProblemFactory facKnp = new RandomKnapsackProblemFactory().setCorrType(type);
						RandomThiefProblemFactory facThief = new RandomThiefProblemFactory(facSalesman, facKnp);
						AbstractThiefProblem problem = facThief.create(cities, itemsPercity, rate, new MyRandom(RANDOM_SEED));
						problem.setProfitEvaluator(new NoDroppingEvaluator());
						problem.setName(String.format("%s-%s-%s-%s-%s", "Clustered", cities, itemsPercity, type, rate));
						new JsonThiefProblemWriter().write(problem, String.format("../ttp-benchmark/MyBenchmark/%s.ttp", problem));
					
					}
				}
			}
		}
	}

}
