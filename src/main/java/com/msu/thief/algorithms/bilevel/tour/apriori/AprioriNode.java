package com.msu.thief.algorithms.bilevel.tour.apriori;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.list.mutable.FastList;
import com.msu.interfaces.IEvaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.thief.problems.ThiefProblemWithFixedTour;

public class AprioriNode {


	public Set<AprioriEntry> children = new HashSet<>();
	
	public boolean add(AprioriEntry entry) {
		return this.children.add(entry);
	}
	
	public Set<AprioriEntry> getChildren() {
		return children;
	}
	
	
	public double getBestFitness() {
		double best = Integer.MAX_VALUE;
		for (AprioriEntry e : children) {
			best = Math.min(best, e.solution.getObjectives(0));
		}
		return best;
	}
	
	
	public void prune(Set<AprioriEntry> hash) {
		Set<AprioriEntry> next = new HashSet<>();
		for (AprioriEntry entry : children) {
			if (hash.contains(entry)) next.add(entry);
		}
		children = next;
	}
	
		
	
	public List<AprioriNode> expand(IEvaluator eval, ThiefProblemWithFixedTour problem, NonDominatedSolutionSet set) {
		
		MutableList<AprioriNode> result = new FastList<>();
		
		// for every node in list
		List<AprioriEntry> l = new ArrayList<>(children);
		for (int i = 0; i < l.size(); i++) {
			
			AprioriNode child = new AprioriNode();
			
			final AprioriEntry first = l.get(i);
			
			for (int j = i + 1; j < l.size(); j++) {
				
				final AprioriEntry second = l.get(j);
				
				Set<Integer> nextItems = new HashSet<>(first.items);
				nextItems.add(second.idx);
				
				AprioriEntry e = new AprioriEntry(second.idx, nextItems);
				e.evaluate(eval, problem);
				set.add(e.solution);
				
				// add only if node is better than father
				if (first.solution.getObjectives(0) > e.solution.getObjectives(0)) child.add(e);
				
			}
			
			if (!child.children.isEmpty()) result.add(child);
			
		}
		
		return result;
	}
	

}
