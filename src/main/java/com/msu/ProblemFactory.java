package com.msu;

import org.apache.log4j.BasicConfigurator;

import com.msu.io.writer.JsonThiefProblemWriter;
import com.msu.problems.ThiefProblem;
import com.msu.problems.factory.AKnapsackProblemFactory;
import com.msu.problems.factory.ASalesmanProblemFactory;
import com.msu.problems.factory.ClusteredSalesmanProblemFactory;
import com.msu.problems.factory.RandomKnapsackProblemFactory;
import com.msu.problems.factory.RandomKnapsackProblemFactory.CORRELATION_TYPE;
import com.msu.problems.factory.RandomThiefProblemFactory;
import com.msu.thief.evaluator.profit.NoDroppingEvaluator;

public class ProblemFactory {

	final public static Integer[] CITIES = new Integer[] { 10, 25, 50, 100 };

	final public static Integer[] ITEMS_PER_CITY = new Integer[] { 1, 5, 10, 25, 50 };

	final public static Double[] CAPACITY = new Double[] { 0.1, 0.4, 0.6, 0.9 };

	final public static CORRELATION_TYPE[] TYPES = new CORRELATION_TYPE[] { CORRELATION_TYPE.UNCORRELATED, CORRELATION_TYPE.WEAKLY_CORRELATED,
			CORRELATION_TYPE.STRONGLY_CORRELATED };

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
						ThiefProblem problem = facThief.create(cities, itemsPercity, rate);
						problem.setProfitEvaluator(new NoDroppingEvaluator());
						problem.setName(String.format("%s-%s-%s-%s-%s", "Clustered", cities, itemsPercity, type, rate));
						new JsonThiefProblemWriter().write(problem, String.format("../ttp-benchmark/MyBenchmark/%s.ttp", problem));
					
					}
				}
			}
		}
	}

}
