package com.msu.thief.variable.tour;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.model.variables.Variable;

/**
 * This class represents the interface of a tour. Since there are different
 * representation of tours which could be all encoded to the permutation
 * representation.
 */
public abstract class Tour<T> extends Variable<T> {

	public Tour(T obj) {
		super(obj);
	}

	public String toString() {
		return obj.toString();
	}

	/**
	 * Calculates of a tour a HashMap which maps the index to the city. e.g.
	 * tour [0,3,2,4,1] will have the map {0:0, 1:4, 2:2, 4:3}.
	 */
	public Map<Integer, Integer> getAsHash() {
		List<Integer> tour = encode();
		Map<Integer, Integer> mCities = new HashMap<>(tour.size());
		for (int i = 0; i < tour.size(); i++) {
			mCities.put(tour.get(i) ,i);
		}
		return mCities;
	}

	abstract public List<Integer> encode();

	abstract public Tour<T> getSymmetric();

}