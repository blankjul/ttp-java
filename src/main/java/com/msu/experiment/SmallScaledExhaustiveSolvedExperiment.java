package com.msu.experiment;

import com.msu.algorithms.ExhaustiveThief;
import com.msu.knp.model.factory.RandomPackingListFactory;
import com.msu.moo.algorithms.impl.NSGAIIBuilder;
import com.msu.moo.experiment.AMultiObjectiveExperiment;
import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.crossover.permutation.CycleCrossover;
import com.msu.moo.operators.crossover.permutation.EdgeRecombinationCrossover;
import com.msu.moo.operators.crossover.permutation.OrderedCrossover;
import com.msu.moo.operators.crossover.permutation.PMXCrossover;
import com.msu.moo.operators.mutation.BitFlipMutation;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.scenarios.knp.RandomKnapsackScenario.CORRELATION_TYPE;
import com.msu.scenarios.thief.RandomTTPScenario;
import com.msu.thief.ThiefProblem;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.tsp.model.factory.OptimumFactory;
import com.msu.tsp.model.factory.RandomFactory;

public class SmallScaledExhaustiveSolvedExperiment extends AMultiObjectiveExperiment<ThiefProblem> {

	@Override
	protected void setAlgorithms(ExperimetSettings<ThiefProblem, NonDominatedSolutionSet> settings) {

		NSGAIIBuilder<TTPVariable, ThiefProblem> builder = new NSGAIIBuilder<>();
		builder.setFactory(new TTPVariableFactory(new RandomFactory<>(), new RandomPackingListFactory()));
		builder.setMutation(new TTPMutation(new SwapMutation<>(), new BitFlipMutation()));
		builder.setProbMutation(0.3);
		
		
		settings.addAlgorithm(builder.setCrossover(new TTPCrossover(new PMXCrossover<Integer>(), new SinglePointCrossover<>()))
				.setName("NSGAII-ST[PMX-SWAP]-BP[SPX-BFM]").create());
		
		settings.addAlgorithm(builder.setCrossover(new TTPCrossover(new CycleCrossover<Integer>(), new SinglePointCrossover<Boolean>()))
				.setName("NSGAII-ST[CX-SWAP]-BP[SPX-BFM]").create());

		
		settings.addAlgorithm(builder.setCrossover(new TTPCrossover(new EdgeRecombinationCrossover<Integer>(), new SinglePointCrossover<Boolean>()))
				.setName("NSGAII-ST[ERX-SWAP]-BP[SPX-BFM]").create());
		
		
		builder.setFactory(new TTPVariableFactory(new OptimumFactory<>(), new RandomPackingListFactory()));
		settings.addAlgorithm(builder.setCrossover(new TTPCrossover(new OrderedCrossover<Integer>(), new SinglePointCrossover<Boolean>()))
				.setName("NSGAII-ST[OX-SWAP]-BP[SPX-BFM]").create());
		
	}


	@Override
	protected void setProblems(ExperimetSettings<ThiefProblem, NonDominatedSolutionSet> settings) {
		ThiefProblem ttp =  new RandomTTPScenario(7, 1, 0.5, CORRELATION_TYPE.STRONGLY_CORRELATED).getObject();
		ttp.setName("TTP-7-1-0.5-STRONGLY_CORRELATED");
		settings.addProblem(ttp);
	}
	
	@Override
	protected void setOptima(ExperimetSettings<ThiefProblem, NonDominatedSolutionSet> settings) {
		for (ThiefProblem problem : settings.getProblems()) {
			settings.addOptima(problem, new ExhaustiveThief().run(new Evaluator<ThiefProblem>(problem)));
		}
		
	}






}