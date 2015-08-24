package com.moo.ttp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.moo.ttp.experiment.factory.ItemFactory;
import com.moo.ttp.experiment.factory.ThiefFactory;
import com.moo.ttp.model.packing.BooleanPackingListFactory;
import com.moo.ttp.model.tour.PositionDecodedTourFactory;
import com.moo.ttp.model.tour.StandardTourFactory;
import com.moo.ttp.variable.TTPCrossover;
import com.moo.ttp.variable.TTPMutation;
import com.moo.ttp.variable.TTPVariable;
import com.moo.ttp.variable.TTPVariableFactory;
import com.moo.ttp.variable.TravellingThiefProblem;
import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.model.AbstractExperiment;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.operators.crossover.BooleanSimilarityCrossover;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.crossover.permutation.CycleCrossover;
import com.msu.moo.operators.crossover.permutation.EdgeRecombinationCrossover;
import com.msu.moo.operators.crossover.permutation.OrderedCrossover;
import com.msu.moo.operators.crossover.permutation.PMXCrossover;
import com.msu.moo.operators.mutation.BitFlipMutation;
import com.msu.moo.operators.mutation.RestrictedPolynomialMutation;
import com.msu.moo.operators.mutation.SwapMutation;

public class NSGAIIOperatorExperiment extends AbstractExperiment<TravellingThiefProblem> {

	public NSGAIIOperatorExperiment() {
		this.maxEvaluations = 100000L;
		this.iterations = 10;
		this.pathToEAF = "../moo-java/vendor/aft-0.95/eaf";
		this.pathToHV = "../moo-java/vendor/hv-1.3-src/hv";
	}

	@Override
	protected List<IAlgorithm<TravellingThiefProblem>> getAlgorithms() {
		List<IAlgorithm<TravellingThiefProblem>> algorithms = new ArrayList<>();

		NSGAIIBuilder<TTPVariable, TravellingThiefProblem> builder = new NSGAIIBuilder<>();
		builder.setFactory(new TTPVariableFactory(new StandardTourFactory(), new BooleanPackingListFactory()));
		builder.setMutation(new TTPMutation(new SwapMutation<>(), new BitFlipMutation()));
		

		algorithms.add(builder
				.setCrossover(new TTPCrossover(new  PMXCrossover<Integer>(), new SinglePointCrossover<>()))
				.setName("NSGAII-ST[PMX-SWAP]-BP[SPX-BFM]").create());
		
		
		algorithms.add(builder
				.setCrossover(new TTPCrossover(new CycleCrossover<Integer>(),  new SinglePointCrossover<Boolean>()))
				.setName("NSGAII-ST[CX-SWAP]-BP[SPX-BFM]").create());
		
		algorithms.add(builder
				.setCrossover(new TTPCrossover(new OrderedCrossover<Integer>(),  new SinglePointCrossover<Boolean>()))
				.setName("NSGAII-ST[OX-SWAP]-BP[SPX-BFM]").create());
		
		algorithms.add(builder
				.setCrossover(new TTPCrossover(new EdgeRecombinationCrossover<Integer>(),  new SinglePointCrossover<Boolean>()))
				.setName("NSGAII-ST[ERX-SWAP]-BP[SPX-BFM]").create());
		
		algorithms.add(builder
				.setCrossover(new TTPCrossover(new  EdgeRecombinationCrossover<Integer>(), new BooleanSimilarityCrossover()))
				.setName("NSGAII-ST[ERX-SWAP]-BP[BSX-BFM]").create());
		
		algorithms.add(builder
				.setCrossover(new TTPCrossover(new SinglePointCrossover<Integer>(),  new SinglePointCrossover<Boolean>()))
				.setMutation(new TTPMutation(new RestrictedPolynomialMutation() , new BitFlipMutation()))
				.setFactory(new TTPVariableFactory(new PositionDecodedTourFactory(), new BooleanPackingListFactory()))
				.setName("NSGAII-PDT[SPC-RPM]-BP[SPX-BFM]").create());
		
		algorithms.add(builder
				.setCrossover(new TTPCrossover(new SinglePointCrossover<Integer>(),  new BooleanSimilarityCrossover()))
				.setName("NSGAII-PDT[SPC-RPM]-BP[BSX-BFM]").create());
		
		return algorithms;
	}

	@Override
	protected List<TravellingThiefProblem> getProblems() {
		TravellingThiefProblem ttp = ThiefFactory.create(20, 1, ItemFactory.TYPE.WEAKLY_CORRELATED, 0.5);
		return new ArrayList<TravellingThiefProblem>(Arrays.asList(ttp));
	}

	public static void main(String[] args) {
		new NSGAIIOperatorExperiment().run();
	}

}
