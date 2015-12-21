package com.msu.thief.experiment.IEEE;

import java.util.List;

import com.msu.builder.Builder;
import com.msu.experiment.AExperiment;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.operators.crossover.HalfUniformCrossover;
import com.msu.operators.crossover.NoCrossover;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.operators.mutation.NoMutation;
import com.msu.thief.algorithms.SingleThiefEvoluation;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.thief.variable.pack.factory.OptimalPackingListFactory;
import com.msu.thief.variable.tour.factory.OptimalTourFactory;

public class EvoluationTourCountExperiment extends AExperiment {



	@Override
	protected void setProblems(List<IProblem> problems) {
		problems.addAll(IEEE.getProblems());
	}

	
	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		
		IAlgorithm a = new Builder<SingleThiefEvoluation>(SingleThiefEvoluation.class)
				.set("populationSize", 50)
				.set("probMutation", 0.3)
				.set("factory", new TTPVariableFactory(new OptimalTourFactory(), new OptimalPackingListFactory()))
				.set("crossover", new TTPCrossover(new NoCrossover<>(), new HalfUniformCrossover<>()))
				.set("mutation", new TTPMutation(new NoMutation<>(), new BitFlipMutation()))
				.set("name", "EA-HUX").build();
		
		algorithms.add(a);
		

	}

}