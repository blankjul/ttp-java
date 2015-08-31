package com.msu.thief.experiment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.operators.crossover.HalfUniformCrossover;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.crossover.UniformCrossover;
import com.msu.moo.operators.crossover.permutation.CycleCrossover;
import com.msu.moo.operators.crossover.permutation.EdgeRecombinationCrossover;
import com.msu.moo.operators.crossover.permutation.OrderedCrossover;
import com.msu.moo.operators.crossover.permutation.PMXCrossover;
import com.msu.moo.operators.mutation.BitFlipMutation;
import com.msu.moo.operators.mutation.RestrictedPolynomialMutation;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.thief.factory.ThiefProblemFactory;
import com.msu.thief.factory.items.ItemFactory;
import com.msu.thief.factory.map.MapFactory;
import com.msu.thief.model.packing.BooleanPackingListFactory;
import com.msu.thief.model.tour.PositionDecodedTourFactory;
import com.msu.thief.model.tour.StandardTourFactory;
import com.msu.thief.problems.TravellingThiefProblem;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.TTPVariableFactory;

public class NSGAIIOperatorExperiment extends AbstractExperiment<TravellingThiefProblem> {

	//! estimated true front
	protected NonDominatedSolutionSet trueFront = new NonDominatedSolutionSet();
	
	
	@Override
	public int getIterations() {
		return 2;
	}

	@Override
	public long getMaxEvaluations() {
		return 1000;
	}

	@Override
	protected List<IAlgorithm<TravellingThiefProblem>> getAlgorithms() {
		List<IAlgorithm<TravellingThiefProblem>> algorithms = new ArrayList<>();

		NSGAIIBuilder<TTPVariable, TravellingThiefProblem> builder = new NSGAIIBuilder<>();
		builder.setFactory(new TTPVariableFactory(new StandardTourFactory(), new BooleanPackingListFactory()));
		builder.setMutation(new TTPMutation(new SwapMutation<>(), new BitFlipMutation()));
		builder.setProbMutation(0.3);
		

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
				.setCrossover(new TTPCrossover(new  EdgeRecombinationCrossover<Integer>(), new UniformCrossover<>()))
				.setName("NSGAII-ST[ERX-SWAP]-BP[UX-BFM]").create());
		
		algorithms.add(builder
				.setCrossover(new TTPCrossover(new  EdgeRecombinationCrossover<Integer>(), new HalfUniformCrossover<>()))
				.setName("NSGAII-ST[ERX-SWAP]-BP[HUX-BFM]").create());
		
		algorithms.add(builder
				.setCrossover(new TTPCrossover(new SinglePointCrossover<Integer>(),  new SinglePointCrossover<Boolean>()))
				.setMutation(new TTPMutation(new RestrictedPolynomialMutation() , new BitFlipMutation()))
				.setFactory(new TTPVariableFactory(new PositionDecodedTourFactory(), new BooleanPackingListFactory()))
				.setName("NSGAII-PDT[SPC-RPM]-BP[SPX-BFM]").create());
		
		algorithms.add(builder
				.setCrossover(new TTPCrossover(new SinglePointCrossover<Integer>(),  new UniformCrossover<>()))
				.setName("NSGAII-PDT[SPC-RPM]-BP[UX-BFM]").create());
		
		algorithms.add(builder
				.setCrossover(new TTPCrossover(new SinglePointCrossover<Integer>(),  new HalfUniformCrossover<>()))
				.setName("NSGAII-PDT[SPC-RPM]-BP[HUX-BFM]").create());
		
		return algorithms;
	}

	@Override
	protected List<TravellingThiefProblem> getProblems() {
		List<TravellingThiefProblem> l = new ArrayList<TravellingThiefProblem>();
		/*
		l.add(ThiefFactory.create(20, 1, ItemFactory.TYPE.UNCORRELATED, 0.5, "TTP-20-1-UNCORRELATED"));
		l.add(ThiefFactory.create(20, 5, ItemFactory.TYPE.UNCORRELATED, 0.5, "TTP-20-5-UNCORRELATED"));
		l.add(ThiefFactory.create(20, 10, ItemFactory.TYPE.UNCORRELATED, 0.5, "TTP-20-10-UNCORRELATED"));
		
		l.add(ThiefFactory.create(20, 1, ItemFactory.TYPE.WEAKLY_CORRELATED, 0.5, "TTP-20-1-WEAK"));
		l.add(ThiefFactory.create(20, 5, ItemFactory.TYPE.WEAKLY_CORRELATED, 0.5, "TTP-20-5-WEAK"));
		l.add(ThiefFactory.create(20, 10, ItemFactory.TYPE.WEAKLY_CORRELATED, 0.5, "TTP-20-10-WEAK"));
		
		l.add(ThiefFactory.create(20, 1, ItemFactory.TYPE.STRONGLY_CORRELATED, 0.5, "TTP-20-1-STRONGLY"));
		l.add(ThiefFactory.create(20, 5, ItemFactory.TYPE.STRONGLY_CORRELATED, 0.5, "TTP-20-5-STRONGLY"));
		*/
		
		l.add(new ThiefProblemFactory(new MapFactory(), 
				new ItemFactory(ItemFactory.CORRELATION_TYPE.STRONGLY_CORRELATED), 0.5, "TTP-20-10-STRONGLY").create(20, 10));
		
		return l;
	}

	public static void main(String[] args) {
		new NSGAIIOperatorExperiment().run();
	}

	@Override
	public <P extends IProblem> Map<IProblem, NonDominatedSolutionSet> getTrueFronts(List<P> problems) {
		return null;
	}

	
	


}
