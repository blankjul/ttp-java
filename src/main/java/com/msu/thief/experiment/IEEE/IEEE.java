package com.msu.thief.experiment.IEEE;

import java.util.Collection;

import com.msu.moo.util.FileCollectorParser;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.io.thief.reader.BonyadiTSPLIBReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;

public class IEEE {

	public static Collection<SingleObjectiveThiefProblem> getProblems() {
			FileCollectorParser<SingleObjectiveThiefProblem> fcp = new FileCollectorParser<>();

			fcp.add("../ttp-benchmark/SingleObjective/10", "10_5_6_25.txt", new BonyadiSingleObjectiveReader());
			fcp.add("../ttp-benchmark/SingleObjective/10", "10_10_2_50.txt", new BonyadiSingleObjectiveReader());
			fcp.add("../ttp-benchmark/SingleObjective/10", "10_15_10_75.txt", new BonyadiSingleObjectiveReader());

			fcp.add("../ttp-benchmark/SingleObjective/20", "20_5_6_75.txt", new BonyadiSingleObjectiveReader());
			fcp.add("../ttp-benchmark/SingleObjective/20", "20_20_7_50.txt", new BonyadiSingleObjectiveReader());
			fcp.add("../ttp-benchmark/SingleObjective/20", "20_30_9_25.txt", new BonyadiSingleObjectiveReader());

			fcp.add("../ttp-benchmark/SingleObjective/50", "50_15_8_50.txt", new BonyadiSingleObjectiveReader());
			fcp.add("../ttp-benchmark/SingleObjective/50", "50_25_3_75.txt", new BonyadiSingleObjectiveReader());
			fcp.add("../ttp-benchmark/SingleObjective/50", "50_75_6_25.txt", new BonyadiSingleObjectiveReader());

			fcp.add("../ttp-benchmark/SingleObjective/100", "100_5_10_50.txt", new BonyadiSingleObjectiveReader());
			fcp.add("../ttp-benchmark/SingleObjective/100", "100_50_5_75.txt", new BonyadiSingleObjectiveReader());
			fcp.add("../ttp-benchmark/SingleObjective/100", "100_150_10_25.txt", new BonyadiSingleObjectiveReader());

			return fcp.collect();
		
	}
	
	public static Collection<SingleObjectiveThiefProblem> getFirstInstancesProblems() {
		FileCollectorParser<SingleObjectiveThiefProblem> fcp = new FileCollectorParser<>();
		fcp.add("../ttp-benchmark/SingleObjective/10", "*_*_1_*.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/20", "*_*_1_*.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/50", "*_*_1_*.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/100","*_*_1_*.txt", new BonyadiSingleObjectiveReader());
		return fcp.collect();
	
}
	
	
	

	public static Collection<SingleObjectiveThiefProblem> getAllProblems() {
			FileCollectorParser<SingleObjectiveThiefProblem> fcp = new FileCollectorParser<>();
			fcp.add("../ttp-benchmark/SingleObjective/10", "*", new BonyadiSingleObjectiveReader());
			fcp.add("../ttp-benchmark/SingleObjective/20", "*", new BonyadiSingleObjectiveReader());
			fcp.add("../ttp-benchmark/SingleObjective/50", "*", new BonyadiSingleObjectiveReader());
			fcp.add("../ttp-benchmark/SingleObjective/100", "*", new BonyadiSingleObjectiveReader());
			return fcp.collect();
		
	}
	
	
	public static Collection<SingleObjectiveThiefProblem> getTTPLIBProblems() {
		FileCollectorParser<SingleObjectiveThiefProblem> fcp = new FileCollectorParser<>();
		fcp.add("../ttp-benchmark/TSPLIB/berlin52-ttp", "*_01.ttp", new BonyadiTSPLIBReader());
		return fcp.collect();
	
}

	
}
