package com.msu.algorithms;

import java.util.ArrayList;
import java.util.List;

import com.msu.ThiefConfiguration;
import com.msu.knp.KnapsackProblem;
import com.msu.knp.model.BooleanPackingList;
import com.msu.knp.model.Item;
import com.msu.knp.model.PackingList;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.AbstractAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.BashExecutor;
import com.msu.moo.util.Util;

public class Combo extends AbstractAlgorithm {

	
	public Combo() {
		super();
		if (!Util.doesFileExist(ThiefConfiguration.PATH_TO_COMBO))
			throw new RuntimeException("Combo Implementation not found!");
	}

	@Override
	public NonDominatedSolutionSet run(IEvaluator eval) {
		NonDominatedSolutionSet result = new NonDominatedSolutionSet();
		result.add(eval.evaluate(Combo.getPackingList(eval)));
		return result;
	}
	
	
	public static PackingList<?> getPackingList(IEvaluator eval) {
		String command = getCommand((KnapsackProblem) eval.getProblem());
		//System.out.println(command);
		String out = BashExecutor.execute(command);
		
		List<Boolean> result = new ArrayList<>();
		
		// for each line at the results
		for (String line : out.split("\n")) {
			if (line.startsWith("#")) continue;
			result.add(line.equals("1"));
		}

		return new BooleanPackingList(result);
	}


	protected static String getCommand(KnapsackProblem problem) {
		StringBuilder sb = new StringBuilder();
		sb.append("echo -e \"");
		sb.append(problem.getMaxWeight());
		sb.append(" ");
		sb.append(problem.numOfItems());
		sb.append(" ");
		for (Item item : problem.getItems()) {
			sb.append(Math.round(item.getProfit()));
			sb.append(" ");
			sb.append((int) Math.ceil(item.getWeight()));
			sb.append(" ");
		}
		sb.append("\" | ");
		sb.append(ThiefConfiguration.PATH_TO_COMBO);
		return sb.toString();
	}

	
	

}
