package com.msu.thief.problems.variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.moo.interfaces.IEvolutionaryVariable;
import com.msu.moo.model.variable.AVariable;
import com.msu.thief.exceptions.VariableNotValidException;
import com.msu.thief.util.StringUtil;

/**
 * The Tour provides an implementation of a tour that saves directly the
 * permutation array.
 */
public class Tour extends AVariable<List<Integer>> implements IEvolutionaryVariable<List<Integer>>{


	/**
	 * Create a Tour using a permutation vector. Starting city is not given!! So
	 * the zero is added to the beginning.
	 * 
	 * @param list
	 *            tour represented by permutation vector
	 */
	public Tour(List<Integer> list) {
		super(list);
	}

	
	/**
	 * @param idx
	 *            index to get the city of
	 * @return returns the ith city which is visited by the salesman.
	 */
	public int ith(int idx) {
		return obj.get(idx);
	}
	
	
	/**
	 * @return number of cities which are visited
	 */
	public int numOfCities() {
		return obj.size();
	}

	/**
	 * @return the symmetric tour
	 */
	public Tour getSymmetric() {
		List<Integer> sym = new ArrayList<>(obj.size());
		sym.add(0);
		for (int i = obj.size() - 1; i > 0; i--) {
			sym.add(obj.get(i));
		}
		return new Tour(sym);
	}

	public Tour copy() {
		return new Tour(new ArrayList<>(obj));
	}

	/**
	 * @return encode the tour to a list of integer
	 */
	public List<Integer> decode() {
		return obj;
	}

	/**
	 * @return false if empty or starts not with 0. else true.
	 */
	public boolean startsWithZero() {
		if (obj.isEmpty())
			return false;
		else
			return obj.get(0) == 0;
	}

	/**
	 * @return true if the tour is a valid permutation and no duplicates are
	 *         contained.
	 */
	public boolean isPermutation() {

		boolean[] b = new boolean[obj.size()];
		Arrays.fill(b, false);

		for (int i = 0; i < obj.size(); i++) {
			int idx = obj.get(i);
			// if the boolean flag is set, we had this value before
			if (idx > obj.size() - 1 || b[idx]) {
				return false;
			}
			b[idx] = true;
		}
		// no values twice
		return true;
	}

	
	public void set(List<Integer> list) {
		this.obj = list;
	}


	/**
	 * Checks whether the tour is valid. The city size has to match, it has to
	 * start with 0 and no duplicates are allowed. Otherwise this method will
	 * throw an exception.
	 */
	public void validate(int numOfCities) {

		if (obj.size() != numOfCities)
			throw new VariableNotValidException(String
					.format("The tour size %s does not match with the number of cities %s", obj.size(), numOfCities));

		if (!startsWithZero())
			throw new VariableNotValidException(String.format("Tour has to start with 0. %s", this));

		if (!isPermutation())
			throw new VariableNotValidException(
					String.format("Tour is not a permutation and contains duplicates", this));

	}
	
	/**
	 * Calculates of a tour a HashMap which maps the index to the city. e.g.
	 * tour [0,3,2,4,1] will have the map {0:0, 1:4, 2:2, 4:3}.
	 */
	public Map<Integer, Integer> getAsHash() {
		List<Integer> tour = decode();
		Map<Integer, Integer> mCities = new HashMap<>(tour.size());
		for (int i = 0; i < tour.size(); i++) {
			mCities.put(tour.get(i) ,i);
		}
		return mCities;
	}


	@Override
	public String toString() {
		return Arrays.toString(obj.toArray());
	}
	
	/**
	 * Create a tour by parsing a string.
	 * 
	 * @param s
	 *            string that contains the tour like [0,3,2,1]
	 */
	public static Tour createFromString(String s) {
		return new Tour(StringUtil.parseAsIntegerList(s));
	}


	@Override
	public Tour build(List<Integer> obj) {
		return new Tour(obj);
	}

	
	
	
	

}
