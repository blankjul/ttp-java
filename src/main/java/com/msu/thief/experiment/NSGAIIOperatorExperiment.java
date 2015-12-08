package com.msu.thief.experiment;

import java.util.List;

import com.msu.experiment.AExperiment;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.thief.algorithm.factory.NSGAIIFactory;
import com.msu.thief.algorithms.OnePlusOneEA;
import com.msu.thief.problems.factory.RandomKnapsackProblemFactory;
import com.msu.thief.problems.factory.RandomSalesmanProblemFactory;
import com.msu.thief.problems.factory.RandomThiefProblemFactory;
import com.msu.thief.problems.factory.RandomKnapsackProblemFactory.CORRELATION_TYPE;
import com.msu.util.MyRandom;

public class NSGAIIOperatorExperiment extends AExperiment {

	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-RANDOM]-[OX-HUX]-[SWAP-BF]").build());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[NEAREST-RANDOM]-[OX-HUX]-[SWAP-BF]").build());
		algorithms.add(new OnePlusOneEA());
	}

	@Override
	protected void setProblems(List<IProblem> problems) {
		for (Integer cities : new Integer[] { 10, 25, 50 }) {
			for (Integer itemsPercity : new Integer[] { 1, 3, 5 }) {
				for (double rate : new Double[] { 0.1, 0.4, 0.6, 0.9 }) {
					RandomKnapsackProblemFactory knp = new RandomKnapsackProblemFactory().setCorrType(CORRELATION_TYPE.UNCORRELATED);
					RandomThiefProblemFactory facThief = new RandomThiefProblemFactory(new RandomSalesmanProblemFactory(), knp);
					problems.add(facThief.create(cities, itemsPercity, rate, new MyRandom(12345)));
				}
			}
		}
	}

	public void finalize() {
		// new ThiefVisualizer<ThiefProblem>().show(this);
	}

}