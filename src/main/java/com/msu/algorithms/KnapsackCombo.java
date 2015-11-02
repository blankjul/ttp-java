package com.msu.algorithms;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.msu.ThiefConfiguration;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.AbstractAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.BashExecutor;
import com.msu.moo.util.Random;
import com.msu.moo.util.Util;
import com.msu.problems.KnapsackProblem;
import com.msu.thief.model.Item;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.PackingList;

public class KnapsackCombo extends AbstractAlgorithm {

	
	public KnapsackCombo() {
		super();
		if (!Util.doesFileExist(ThiefConfiguration.PATH_TO_COMBO))
			throw new RuntimeException("Combo C Implementation not found!");
	}

	@Override
	public NonDominatedSolutionSet run_(IEvaluator eval, Random rand) {
		NonDominatedSolutionSet result = new NonDominatedSolutionSet();
		result.add(eval.evaluate(KnapsackCombo.getPackingList(eval)));
		return result;
	}
	
	
	public static PackingList<?> getPackingList(IEvaluator eval) {
		//String command = getCommand((KnapsackProblem) eval.getProblem());
		
		try {
			String file = String.format("%s.knp", UUID.randomUUID().toString()) ;
			
			writeProblemFile((KnapsackProblem) eval.getProblem(), file);
			BashExecutor.execute(String.format("vendor/combo/combo %s", file));
			List<Boolean> result = new ArrayList<>();
			
			FileReader fileReader = new FileReader(String.format("%s.sol", file));
			BufferedReader br = new BufferedReader(fileReader);
			
			String line = null;
				
			// for each line at the results
			while ((line = br.readLine()) != null) {
				if (line.startsWith("#")) continue;
				result.add(line.equals("1"));
			}
			br.close();
			
			BashExecutor.execute(String.format("rm %s", file));
			BashExecutor.execute(String.format("rm %s.sol", file));
			
			return new BooleanPackingList(result);
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error while using Combo C Implementation!");
		}
		
		
		
	}
	
	private static void writeProblemFile(KnapsackProblem problem, String file) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer;
		writer = new PrintWriter(file);
		writer.println(problem.getMaxWeight());
		writer.println(problem.numOfItems());
		for (Item item : problem.getItems()) {
			writer.println(Math.round(item.getProfit()) + " " + (int) Math.ceil(item.getWeight()));
		}
		writer.close();
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
