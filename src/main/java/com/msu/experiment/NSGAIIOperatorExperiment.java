package com.msu.experiment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.knp.impl.scenarios.RandomKnapsackScenario.CORRELATION_TYPE;
import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.experiment.NProblemNAlgorithmExperiment;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.crossover.permutation.PMXCrossover;
import com.msu.moo.operators.mutation.BitFlipMutation;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.thief.TravellingThiefProblem;
import com.msu.thief.model.packing.factory.BooleanPackingListFactory;
import com.msu.thief.model.tour.factory.StandardTourFactory;
import com.msu.thief.scenarios.RandomTTPScenario;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.TTPVariableFactory;

public class NSGAIIOperatorExperiment extends NProblemNAlgorithmExperiment<TravellingThiefProblem> {

	@Override
	protected List<IAlgorithm<TravellingThiefProblem>> getAlgorithms() {
		List<IAlgorithm<TravellingThiefProblem>> result = new ArrayList<>();

		NSGAIIBuilder<TTPVariable, TravellingThiefProblem> builder = new NSGAIIBuilder<>();
		builder.setFactory(new TTPVariableFactory(new StandardTourFactory<>(), new BooleanPackingListFactory()));
		builder.setMutation(new TTPMutation(new SwapMutation<>(), new BitFlipMutation()));
		builder.setProbMutation(0.3);
		result.add(builder.setCrossover(new TTPCrossover(new PMXCrossover<Integer>(), new SinglePointCrossover<>()))
				.setName("NSGAII-ST[PMX-SWAP]-BP[SPX-BFM]").create());

		/*
		result.add(builder.setCrossover(new TTPCrossover(new CycleCrossover<Integer>(), new SinglePointCrossover<Boolean>()))
				.setName("NSGAII-ST[CX-SWAP]-BP[SPX-BFM]").create());

		result.add(builder.setCrossover(new TTPCrossover(new OrderedCrossover<Integer>(), new SinglePointCrossover<Boolean>()))
				.setName("NSGAII-ST[OX-SWAP]-BP[SPX-BFM]").create());

		result.add(builder.setCrossover(new TTPCrossover(new EdgeRecombinationCrossover<Integer>(), new SinglePointCrossover<Boolean>()))
				.setName("NSGAII-ST[ERX-SWAP]-BP[SPX-BFM]").create());

		result.add(builder.setCrossover(new TTPCrossover(new EdgeRecombinationCrossover<Integer>(), new UniformCrossover<>()))
				.setName("NSGAII-ST[ERX-SWAP]-BP[UX-BFM]").create());

		result.add(builder.setCrossover(new TTPCrossover(new EdgeRecombinationCrossover<Integer>(), new HalfUniformCrossover<>()))
				.setName("NSGAII-ST[ERX-SWAP]-BP[HUX-BFM]").create());

		result.add(builder.setCrossover(new TTPCrossover(new SinglePointCrossover<Integer>(), new SinglePointCrossover<Boolean>()))
				.setMutation(new TTPMutation(new RestrictedPolynomialMutation(), new BitFlipMutation()))
				.setFactory(new TTPVariableFactory(new PositionDecodedTourFactory<>(), new BooleanPackingListFactory()))
				.setName("NSGAII-PDT[SPC-RPM]-BP[SPX-BFM]").create());

		result.add(builder.setCrossover(new TTPCrossover(new SinglePointCrossover<Integer>(), new UniformCrossover<>()))
				.setName("NSGAII-PDT[SPC-RPM]-BP[UX-BFM]").create());

		result.add(builder.setCrossover(new TTPCrossover(new SinglePointCrossover<Integer>(), new HalfUniformCrossover<>()))
				.setName("NSGAII-PDT[SPC-RPM]-BP[HUX-BFM]").create());
*/
		return result;
	}


	@Override
	protected Map<TravellingThiefProblem, NonDominatedSolutionSet> getProblems() {
		Map<TravellingThiefProblem, NonDominatedSolutionSet> m = new HashMap<>();

		TravellingThiefProblem ttp =  new RandomTTPScenario(100, 3, 0.5, CORRELATION_TYPE.STRONGLY_CORRELATED).getObject();
		ttp.setName("TTP-50-10-0.7-UNCORRELATED");
		m.put(ttp, null);

		return m;
	}




}