package com.msu.experiment;

import java.util.List;

import com.msu.algorithms.NSGAIIFactory;
import com.msu.algorithms.OnePlusOneEA;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.scenarios.knp.RandomKnapsackScenario.CORRELATION_TYPE;
import com.msu.scenarios.thief.RandomTTPScenario;

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
					problems.add(new RandomTTPScenario(cities, itemsPercity, rate, CORRELATION_TYPE.STRONGLY_CORRELATED).getObject());
				}
			}
		}
	}


	public void finalize() {
		//new ThiefVisualizer<ThiefProblem>().show(this);
	}



}