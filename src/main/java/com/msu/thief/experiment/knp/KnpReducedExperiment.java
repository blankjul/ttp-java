package com.msu.thief.experiment.knp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.moo.algorithms.ExhaustiveSolver;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.experiment.AbstractExperiment;
import com.msu.thief.factory.AlgorithmFactory;
import com.msu.thief.factory.ThiefProblemFactory;
import com.msu.thief.factory.items.ItemFactory;
import com.msu.thief.factory.map.MapFactory;
import com.msu.thief.problems.TravellingThiefProblem;
import com.msu.thief.problems.knp.KnapsackExhaustiveFactory;
import com.msu.thief.problems.knp.KnapsackProblem;
import com.msu.thief.problems.knp.KnapsackVariable;

public class KnpReducedExperiment extends AbstractExperiment<TravellingThiefProblem> {

	@Override
	protected List<IAlgorithm<TravellingThiefProblem>> getAlgorithms() {
		return new ArrayList<>(Arrays.asList(AlgorithmFactory.createNSGAII()));
	}

	@Override
	protected List<TravellingThiefProblem> getProblems() {
		List<TravellingThiefProblem> l = new ArrayList<TravellingThiefProblem>();
		ItemFactory facItems = new ItemFactory(ItemFactory.CORRELATION_TYPE.UNCORRELATED);
		ThiefProblemFactory fac = new ThiefProblemFactory(new MapFactory(), 
				facItems, 0.5, "KnpReducedProblem");
		fac.setDropType(ThiefProblemFactory.DROPPING_TYPE.RANDOM);
		l.add(fac.create(1, 10));
		return l;
	}

	@Override
	public int getIterations() {
		return 10;
	}

	@Override
	public long getMaxEvaluations() {
		return 50000L;
	}

	@Override
	public <P extends IProblem> Map<IProblem, NonDominatedSolutionSet> getTrueFronts(List<P> problems) {
		Map<IProblem, NonDominatedSolutionSet> result = new HashMap<>();
		for (P p : problems) {
			TravellingThiefProblem ttp = (TravellingThiefProblem) p;
			KnapsackProblem knp = new KnapsackProblem(ttp.getMaxWeight(), ttp.getItems().getItems());
			ExhaustiveSolver<KnapsackVariable, KnapsackProblem> solver = new ExhaustiveSolver<>(new KnapsackExhaustiveFactory());
			NonDominatedSolutionSet set = solver.run(knp);
			for (Solution s : set.getSolutions()) {
				s.getObjectives().add(0, 0.0);
				System.out.println(String.format("Optimum: %s", s));
			}
			result.put(p, set);
		}
		return result;
	}

	public static void main(String[] args) {
		new KnpReducedExperiment().run();
	}

}
