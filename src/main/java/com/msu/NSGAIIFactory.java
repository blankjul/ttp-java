package com.msu;

import com.msu.moo.algorithms.NSGAII;
import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.operators.AbstractCrossover;
import com.msu.moo.operators.AbstractMutation;
import com.msu.moo.operators.crossover.HalfUniformCrossover;
import com.msu.moo.operators.crossover.NoCrossover;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.crossover.UniformCrossover;
import com.msu.moo.operators.crossover.permutation.CycleCrossover;
import com.msu.moo.operators.crossover.permutation.EdgeRecombinationCrossover;
import com.msu.moo.operators.crossover.permutation.OrderedCrossover;
import com.msu.moo.operators.crossover.permutation.PMXCrossover;
import com.msu.moo.operators.mutation.BitFlipMutation;
import com.msu.moo.operators.mutation.NoMutation;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.thief.variable.pack.factory.APackingPlanFactory;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.thief.variable.pack.factory.FullPackingListFactory;
import com.msu.thief.variable.pack.factory.RandomPackingListFactory;
import com.msu.thief.variable.tour.factory.ATourFactory;
import com.msu.thief.variable.tour.factory.NearestNeighbourFactory;
import com.msu.thief.variable.tour.factory.OptimumFactory;
import com.msu.thief.variable.tour.factory.RandomTourFactory;

public class NSGAIIFactory {

	public static NSGAII createNSGAII() {
		return createNSGAIIBuilder().create();
	}

	public static NSGAIIBuilder createNSGAIIBuilder() {
		NSGAIIBuilder builder = new NSGAIIBuilder();
		builder.setFactory(new TTPVariableFactory(new RandomTourFactory(), new RandomPackingListFactory()));
		builder.setCrossover(new TTPCrossover(new OrderedCrossover<>(), new SinglePointCrossover<>()));
		builder.setMutation(new TTPMutation(new SwapMutation<>(), new BitFlipMutation()));
		builder.setProbMutation(0.3);
		builder.setName("NSGAII-ST[OX-SWAP]-BP[SPX-BFM]");
		return builder;
	}

	public static NSGAIIBuilder createNSGAIIBuilderKnapsack(String name) {
		NSGAIIBuilder builder = new NSGAIIBuilder();
		builder.setName(name);
		String[] values = name.substring(7).split("-");
		builder.setFactory(parsePackingFactory(values[0]));
		builder.setCrossover(parsePackCrossover(values[1]));
		builder.setMutation(parsePackingMutation(values[2]));
		return builder;
	}
	

	public static NSGAIIBuilder createNSGAIIBuilder(String name) {

		NSGAIIBuilder builder = new NSGAIIBuilder();
		builder.setName(name);

		String[] values = name.substring(7).replaceAll("\\[", "").replaceAll("\\]", "").split("-");

		ATourFactory facTour = parseTourFactory(values[0]);
		APackingPlanFactory facPlan = parsePackingFactory(values[1]);
		builder.setFactory(new TTPVariableFactory(facTour, facPlan));

		AbstractCrossover<?> cTour = parseTourCrossover(values[2]);
		AbstractCrossover<?> cPlan = parsePackCrossover(values[3]);
		builder.setCrossover(new TTPCrossover(cTour, cPlan));

		AbstractMutation<?> mTour = parseTourMutation(values[4]);
		AbstractMutation<?> mPlan = parsePackingMutation(values[5]);
		builder.setMutation(new TTPMutation(mTour, mPlan));
		builder.setProbMutation(0.3);

		return builder;
	}

	protected static AbstractMutation<?> parsePackingMutation(String value) {
		if (value.equals("BF")) {
			return new BitFlipMutation();
		} else if (value.equals("NO")) {
			return new NoMutation<>();
		} else {
			throw new RuntimeException("Unknown Packing Plan Mutation");
		}
	}

	protected static AbstractMutation<?> parseTourMutation(String value) {
		if (value.equals("SWAP")) {
			return new SwapMutation<>();
		} else if (value.equals("NO")) {
			return new NoMutation<>();
		} else {
			throw new RuntimeException("Unknown Tour Mutation");
		}
	}

	protected static AbstractCrossover<?> parsePackCrossover(String value) {
		if (value.equals("HUX")) {
			return new HalfUniformCrossover<>();
		} else if (value.equals("UX")) {
			return new UniformCrossover<>();
		} else if (value.equals("SPX")) {
			return new SinglePointCrossover<>();
		} else if (value.equals("NO")) {
			return new NoCrossover<>();
		} else {
			throw new RuntimeException("Unknown Packing Plan Crossover");
		}
	}

	protected static AbstractCrossover<?> parseTourCrossover(String value) {
		if (value.equals("OX")) {
			return new OrderedCrossover<>();
		} else if (value.equals("CX")) {
			return new CycleCrossover<>();
		} else if (value.equals("PMX")) {
			return new PMXCrossover<>();
		} else if (value.equals("NO")) {
			return new NoCrossover<>();
		} else if (value.equals("ERX")) {
			return new EdgeRecombinationCrossover<>();
		} else {
			throw new RuntimeException("Unknown Tour Crossover");
		}
	}

	protected static ATourFactory parseTourFactory(String value) {
		if (value.equals("OPT")) {
			return new OptimumFactory();
		} else if (value.equals("NEAREST")) {
			return new NearestNeighbourFactory();
		} else if (value.equals("RANDOM")) {
			return new RandomTourFactory();
		} else {
			throw new RuntimeException("Unknown Tour factory");
		}
	}

	protected static APackingPlanFactory parsePackingFactory(String value) {
		if (value.equals("FULL")) {
			return new FullPackingListFactory();
		} else if (value.equals("EMPTY")) {
			return new EmptyPackingListFactory();
		} else if (value.equals("RANDOM")) {
			return new RandomPackingListFactory();
		} else {
			throw new RuntimeException("Unknown Packing factory");
		}
	}

}
