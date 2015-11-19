package com.msu.thief.io.thief.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.io.AProblemReader;
import com.msu.thief.model.Item;
import com.msu.thief.problems.KnapsackProblem;

public class KnapsackProblemReader extends AProblemReader<KnapsackProblem> {

	static final Logger logger = Logger.getLogger(KnapsackProblemReader.class);
	
	@Override
	public KnapsackProblem read_(BufferedReader br) throws IOException {
		
		List<Item> items = new ArrayList<>();
		
		String name = br.readLine();
		logger.info(String.format("Reading problem %s", name));
		
		logger.info(String.format("Items: %s", br.readLine()));
		
		int maxWeight = Integer.valueOf(br.readLine().substring(2));
		logger.info(String.format("Maximal weight: %s", maxWeight));
		
		Solution optimum = new Solution(null, Arrays.asList(Double.valueOf(br.readLine().substring(2))));
		logger.info(String.format("Optimal Profit: %s", optimum.getObjective()));
		
		logger.info(String.format("Solving problem %s", br.readLine()));
		
		String line = null;
		while((line = br.readLine()) != null) {
			
			// always read only the first instance
			if (line.equals("-----")) break;
			
			String[] values = line.split(",");
			Item item = new Item(Integer.valueOf(values[1]), Integer.valueOf(values[2]));
			logger.trace(String.format("Insert item %s", item));
			items.add(item);
		}
		
		KnapsackProblem p = new KnapsackProblem(maxWeight, items);
		p.setOptimum(new NonDominatedSolutionSet(Arrays.asList(optimum)));
		return p;
	}

}
