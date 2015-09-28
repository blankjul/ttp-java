package com.msu.experiment;

import java.util.Arrays;
import java.util.Collection;

import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.scenarios.thief.bonyadi.BenchmarkSingleObjective;
import com.msu.thief.SingleObjectiveThiefProblem;
import com.msu.thief.ThiefProblem;

public class BonyadiSingleExperiment extends ABonyadiBenchmark {

	final public static String FOLDER = "../ttp-benchmark/SingleObjective/10";

	@Override
	protected void report_(ThiefProblem problem, IAlgorithm<NonDominatedSolutionSet, ThiefProblem> algorithm, NonDominatedSolutionSet set) {
		Solution best = set.getSolutions().get(0);
		double objective = best.getObjectives(0);
		
		String[] str = problem.toString().substring(0, problem.toString().length() - 4).split("_");
		String numOfCities = str[0];
		String numItems = str[1];
		String instanceNum = str[2];
		String tightness = str[3];
		
		System.out.println(String.format("%s,%s,%s,%s,%s,%s,%s", problem, numOfCities, numItems, instanceNum, tightness, algorithm, objective));
	}

	public static Collection<String> getInstances() {
		return Arrays.asList("../ttp-benchmark/SingleObjective/10/10_3_3_25.txt");
		/*
		List<String> instances = new ArrayList<>();
		try {
			Files.walk(Paths.get(FOLDER)).forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					String file = filePath.toString();
					instances.add(file);
					
					System.out.println(file);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return instances;
		*/
		
	}

	@Override
	protected void setProblems(ExperimetSettings<ThiefProblem, NonDominatedSolutionSet> settings) {
		for (String path : getInstances()) {
			SingleObjectiveThiefProblem ttp = new BenchmarkSingleObjective().create(path);
			
			// ttp.setMinSpeed(1);
			// ttp.setMaxSpeed(1000);

			settings.addProblem(ttp);
		}
	}

}
