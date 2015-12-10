package com.msu.thief.algorithms.factory;

import com.msu.builder.NSGAIIBuilder;
import com.msu.operators.AbstractCrossover;
import com.msu.operators.AbstractMutation;
import com.msu.operators.crossover.HalfUniformCrossover;
import com.msu.operators.crossover.NoCrossover;
import com.msu.operators.crossover.SinglePointCrossover;
import com.msu.operators.crossover.UniformCrossover;
import com.msu.operators.crossover.permutation.CycleCrossover;
import com.msu.operators.crossover.permutation.EdgeRecombinationCrossover;
import com.msu.operators.crossover.permutation.OrderedCrossover;
import com.msu.operators.crossover.permutation.PMXCrossover;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.operators.mutation.NoMutation;
import com.msu.operators.mutation.SwapMutation;
import com.msu.thief.util.TwoOptMutation;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.thief.variable.pack.factory.APackingListFactory;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.thief.variable.pack.factory.FullPackingListFactory;
import com.msu.thief.variable.pack.factory.OptimalPackingListFactory;
import com.msu.thief.variable.pack.factory.RandomPackingListFactory;
import com.msu.thief.variable.tour.factory.ATourFactory;
import com.msu.thief.variable.tour.factory.NearestNeighbourFactory;
import com.msu.thief.variable.tour.factory.OptimalTourFactory;
import com.msu.thief.variable.tour.factory.RandomTourFactory;
import com.msu.thief.variable.tour.factory.TwoOptFactory;

public class NSGAIIFactory {

	public static NSGAIIBuilder createNSGAIIBuilderKnapsack(String name) {
		NSGAIIBuilder builder = new NSGAIIBuilder();
		builder.set("name", name);
		String[] values = name.substring(7).split("-");
		builder.set("factory", parsePackingFactory(values[0]));
		builder.set("crossover", parsePackCrossover(values[1]));
		builder.set("mutation", parsePackingMutation(values[2]));
		return builder;
	}

	public static NSGAIIBuilder createNSGAIIBuilder(String name) {
		return NSGAIIFactory.createNSGAIIBuilder(name, new NSGAIIBuilder());
	}

	public static NSGAIIBuilder createNSGAIIBuilder(String name, NSGAIIBuilder builder) {

		builder.set("name", name);

		String[] values = name.substring(7).replaceAll("\\[", "").replaceAll("\\]", "").split("-");

		ATourFactory facTour = parseTourFactory(values[0]);
		APackingListFactory facPlan = parsePackingFactory(values[1]);
		builder.set("factory", new TTPVariableFactory(facTour, facPlan));

		AbstractCrossover<?> cTour = parseTourCrossover(values[2]);
		AbstractCrossover<?> cPlan = parsePackCrossover(values[3]);
		builder.set("crossover", new TTPCrossover(cTour, cPlan));

		AbstractMutation<?> mTour = parseTourMutation(values[4]);
		AbstractMutation<?> mPlan = parsePackingMutation(values[5]);
		builder.set("mutation", new TTPMutation(mTour, mPlan));

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
		} else if (value.equals("2OPT")) {
			return new TwoOptMutation();
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
			return new OptimalTourFactory();
		} else if (value.equals("NEAREST")) {
			return new NearestNeighbourFactory();
		} else if (value.equals("RANDOM")) {
			return new RandomTourFactory();
		} else if (value.equals("2OPT")) {
			return new TwoOptFactory();
		} else {
			throw new RuntimeException("Unknown Tour factory");
		}
	}

	protected static APackingListFactory parsePackingFactory(String value) {
		if (value.equals("FULL")) {
			return new FullPackingListFactory();
		} else if (value.equals("EMPTY")) {
			return new EmptyPackingListFactory();
		} else if (value.equals("RANDOM")) {
			return new RandomPackingListFactory();
		} else if (value.equals("OPT")) {
			return new OptimalPackingListFactory();
		} else {
			throw new RuntimeException("Unknown Packing factory");
		}
	}

}
