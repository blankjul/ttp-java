package com.msu.thief.experiment.IEEE;

import java.util.List;

import com.msu.experiment.AExperiment;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.thief.algorithms.AlternatingPoolingEvolution;
import com.msu.thief.experiment.SingleObjectiveReport;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.util.FileCollectorParser;

public class BankToBankExperiment extends AExperiment {


	protected void initialize() {
		new SingleObjectiveReport("../ttp-results/coevo_real.csv");
	};

	@Override
	protected void setProblems(List<IProblem> problems) {
		
		FileCollectorParser<AbstractThiefProblem> fcp = new FileCollectorParser<>();
		fcp.add("../ttp-benchmark/SingleObjective/10", "*_*_1_*.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/20", "*_*_1_*.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/50", "*_*_1_*.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/100","*_*_1_*.txt", new BonyadiSingleObjectiveReader());

		//problems.addAll(fcp.collect());
		
		problems.addAll(IEEE.getProblems());
	}

	
	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		algorithms.add(new AlternatingPoolingEvolution());
		//algorithms.add(new CoevolutionAlgorithm());
	}

}