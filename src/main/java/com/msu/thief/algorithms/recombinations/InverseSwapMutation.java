package com.msu.thief.algorithms.recombinations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.operators.AbstractMutation;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.util.MyRandom;

public class InverseSwapMutation extends AbstractMutation<List<Integer>>{
	@Override
	public List<Integer> mutate_(List<Integer> var, IProblem problem, MyRandom rand, IEvaluator evaluator) {
		
		int i = rand.nextInt(var.size() - 1);
		int k = rand.nextInt(var.size() - 1);
		//System.out.println(i + " " + k);
		
		return mutate_(var, problem, rand, evaluator, i, k);
		
	}
	
	
	public List<Integer> mutate_(List<Integer> var, IProblem problem, MyRandom rand, IEvaluator evaluator, int i, int k) {
		
		if (k < i) {
			int tmp = i;
			i = k;
			k = tmp;
		}

		List<Integer> tour = new ArrayList<>(var);
		
		List<Integer> result = new ArrayList<>();
		result.addAll(tour.subList(0, i));

		List<Integer> middle = tour.subList(i, k+1);
		Collections.reverse(middle);
		result.addAll(middle);

		result.addAll(tour.subList(k+1, tour.size()));
		
		return result;
		
		
	}
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		SingleObjectiveThiefProblem thief = (SingleObjectiveThiefProblem) new BonyadiSingleObjectiveReader()
				.read("../ttp-benchmark/SingleObjective/10/10_5_6_25.txt");
		
		List<Integer> l = Arrays.asList(0,1,2,3,4,5,6,7,8,9);
		System.out.println(new InverseSwapMutation().mutate_(l, thief, new MyRandom(123456), null, 1, 9));
		
	}
	
	
	
	
	
}
