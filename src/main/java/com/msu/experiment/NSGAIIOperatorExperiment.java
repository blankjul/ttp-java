package com.msu.experiment;

import com.msu.knp.scenarios.impl.RandomKnapsackScenario.CORRELATION_TYPE;
import com.msu.moo.algorithms.impl.NSGAIIBuilder;
import com.msu.moo.experiment.AMultiObjectiveExperiment;
import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.crossover.permutation.CycleCrossover;
import com.msu.moo.operators.crossover.permutation.EdgeRecombinationCrossover;
import com.msu.moo.operators.crossover.permutation.OrderedCrossover;
import com.msu.moo.operators.crossover.permutation.PMXCrossover;
import com.msu.moo.operators.mutation.BitFlipMutation;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.thief.TravellingThiefProblem;
import com.msu.thief.model.packing.factory.PackingListFactory;
import com.msu.thief.model.tour.factory.StandardTourFactory;
import com.msu.thief.model.tour.factory.StandardTourMutateOptimumFactory;
import com.msu.thief.scenarios.impl.RandomTTPScenario;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.TTPVariableFactory;

public class NSGAIIOperatorExperiment extends AMultiObjectiveExperiment<TravellingThiefProblem> {

	@Override
	protected void setAlgorithms(ExperimetSettings<TravellingThiefProblem, NonDominatedSolutionSet> settings) {

		NSGAIIBuilder<TTPVariable, TravellingThiefProblem> builder = new NSGAIIBuilder<>();
		builder.setFactory(new TTPVariableFactory(new StandardTourFactory<>(), new PackingListFactory()));
		builder.setMutation(new TTPMutation(new SwapMutation<>(), new BitFlipMutation()));
		builder.setProbMutation(0.3);
		
		
		settings.addAlgorithm(builder.setCrossover(new TTPCrossover(new PMXCrossover<Integer>(), new SinglePointCrossover<>()))
				.setName("NSGAII-ST[PMX-SWAP]-BP[SPX-BFM]").create());
		
		settings.addAlgorithm(builder.setCrossover(new TTPCrossover(new CycleCrossover<Integer>(), new SinglePointCrossover<Boolean>()))
				.setName("NSGAII-ST[CX-SWAP]-BP[SPX-BFM]").create());

		
		settings.addAlgorithm(builder.setCrossover(new TTPCrossover(new EdgeRecombinationCrossover<Integer>(), new SinglePointCrossover<Boolean>()))
				.setName("NSGAII-ST[ERX-SWAP]-BP[SPX-BFM]").create());
		
		
		builder.setFactory(new TTPVariableFactory(new StandardTourMutateOptimumFactory<>(), new PackingListFactory()));
		settings.addAlgorithm(builder.setCrossover(new TTPCrossover(new OrderedCrossover<Integer>(), new SinglePointCrossover<Boolean>()))
				.setName("NSGAII-ST[OX-SWAP]-BP[SPX-BFM]").create());
		
		/*
		settings.addAlgorithm(builder.setCrossover(new TTPCrossover(new EdgeRecombinationCrossover<Integer>(), new UniformCrossover<>()))
				.setName("NSGAII-ST[ERX-SWAP]-BP[UX-BFM]").create());

		settings.addAlgorithm(builder.setCrossover(new TTPCrossover(new EdgeRecombinationCrossover<Integer>(), new HalfUniformCrossover<>()))
				.setName("NSGAII-ST[ERX-SWAP]-BP[HUX-BFM]").create());
		
	
		settings.addAlgorithm(builder.setCrossover(new TTPCrossover(new SinglePointCrossover<Integer>(), new SinglePointCrossover<Boolean>()))
				.setMutation(new TTPMutation(new RestrictedPolynomialMutation(), new BitFlipMutation()))
				.setFactory(new TTPVariableFactory(new PositionDecodedTourFactory<>(), new BooleanPackingListFactory()))
				.setName("NSGAII-PDT[SPC-RPM]-BP[SPX-BFM]").create());

		settings.addAlgorithm(builder.setCrossover(new TTPCrossover(new SinglePointCrossover<Integer>(), new UniformCrossover<>()))
				.setName("NSGAII-PDT[SPC-RPM]-BP[UX-BFM]").create());

		settings.addAlgorithm(builder.setCrossover(new TTPCrossover(new SinglePointCrossover<Integer>(), new HalfUniformCrossover<>()))
				.setName("NSGAII-PDT[SPC-RPM]-BP[HUX-BFM]").create());

 		*/
	}


	@Override
	protected void setProblems(ExperimetSettings<TravellingThiefProblem, NonDominatedSolutionSet> settings) {
		TravellingThiefProblem ttp =  new RandomTTPScenario(100, 3, 0.5, CORRELATION_TYPE.STRONGLY_CORRELATED).getObject();
		ttp.setName("TTP-100-3-0.5-STRONGLY_CORRELATED");
		settings.addProblem(ttp);
	}






}