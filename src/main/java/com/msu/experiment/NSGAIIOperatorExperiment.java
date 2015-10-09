package com.msu.experiment;

import java.util.List;

import com.msu.NSGAIIFactory;
import com.msu.algorithms.OnePlusOneEA;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.problems.factory.RandomThiefScenario;
import com.msu.problems.factory.RandomKnapsackScenario.CORRELATION_TYPE;

public class NSGAIIOperatorExperiment extends AExperiment {

	
	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-RANDOM]-[OX-HUX]-[SWAP-BF]").create());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[NEAREST-RANDOM]-[OX-HUX]-[SWAP-BF]").create());
		algorithms.add(new OnePlusOneEA());
	}


	@Override
	protected void setProblems(List<IProblem> problems) {
		for (Integer cities : new Integer[] {10,25,50}) {
			for (Integer itemsPercity : new Integer[] {1,3,5}) {
				for(double rate : new Double[] {0.1, 0.4, 0.6, 0.9}) {
					problems.add(new RandomThiefScenario(cities, itemsPercity, rate, CORRELATION_TYPE.STRONGLY_CORRELATED).getObject());
				}
			}
		}
	}


	public void finalize() {
		//new ThiefVisualizer<ThiefProblem>().show(this);
	}



}