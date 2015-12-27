package com.msu.thief.experiment.IEEE;

import java.util.Arrays;
import java.util.List;

import com.msu.builder.Builder;
import com.msu.experiment.AExperiment;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.thief.algorithms.TwoPhaseEvolution;
import com.msu.thief.algorithms.coevolution.CoevolutionAlgorithm;
import com.msu.thief.algorithms.coevolution.selector.NBestSelector;
import com.msu.thief.algorithms.coevolution.selector.RandomSelector;
import com.msu.thief.experiment.SingleObjectiveReport;

public class BankToBankExperiment extends AExperiment {


	protected void initialize() {
		new SingleObjectiveReport("/home/julesy/Dropbox/result.csv");
		//new SingleObjectiveReport("../ttp-results/coevo_real.csv");
	};

	@Override
	protected void setProblems(List<IProblem> problems) {
		
		//problems.addAll(IEEE.getFirstInstancesProblems());
		problems.addAll(IEEE.getProblems());
		
/*		FileCollectorParser<AbstractThiefProblem> fcp = new FileCollectorParser<>();
		fcp.add("../ttp-benchmark/SingleObjective/50", "50_75_6_25.txt", new BonyadiSingleObjectiveReader());
		problems.addAll(fcp.collect());*/
	}

	
	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		
		Builder<TwoPhaseEvolution> builder = new Builder<>(TwoPhaseEvolution.class);
		
		for(double bilevel : Arrays.asList(0.0, 0.05, 0.1, 0.2, 0.5)) {
			builder.set("bilevelEvaluationsFactor", bilevel);
			for(double pooling : Arrays.asList(0.01, 0.05, 0.1, 0.2, 0.3)) {
				builder.set("poolingEvaluationsFactor", pooling);
				builder.set("name", String.format("TWOPHASE-%s-%s", (bilevel == 0) ? "NO" : bilevel, pooling));
				algorithms.add(builder.build());
			}
		}
		
		
		
		Builder<CoevolutionAlgorithm> builderCo = new Builder<CoevolutionAlgorithm>(CoevolutionAlgorithm.class);
		
		for(boolean best : Arrays.asList(false, true)) {
			for(int collaborators : Arrays.asList(1,2,5,10)) {
				if (best) builderCo.set("selector", new NBestSelector(collaborators));
				else  builderCo.set("selector", new RandomSelector(collaborators));
				for(int numOfGenerations : Arrays.asList(1,5,10,20)) {
					builderCo.set("numOfGenerations", numOfGenerations);
					builderCo.set("name", String.format("COEVO-%s-%s-%s", (best) ? "BEST" : "RANDOM", collaborators, numOfGenerations));
					algorithms.add(builderCo.build());
					
				}

			}
		}
		

		

	}

}