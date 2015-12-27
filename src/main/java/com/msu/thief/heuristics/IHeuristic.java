package com.msu.thief.heuristics;

import java.util.Collection;
import java.util.Map;

public interface IHeuristic<T> {

	
	public Map<T, Double> calc(Collection<T> c);
	
}
