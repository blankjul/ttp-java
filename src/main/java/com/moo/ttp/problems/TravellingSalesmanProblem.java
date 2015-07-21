package com.moo.ttp.problems;
import com.moo.ttp.model.Map;

public class TravellingSalesmanProblem {
	
	private Map map;

	public TravellingSalesmanProblem(Map map) {
		super();
		this.map = map;
	}
	
	public int evaluate(Integer[] pi) {
		int sumDistances = 0;
		for (int i = 0; i < pi.length - 1; i++) {
			sumDistances += map.get(pi[i], pi[i+1]);
		}
		sumDistances += map.get(pi[pi.length-1], pi[0]);
		return sumDistances;
		
	}
	
	
	

}
