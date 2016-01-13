package com.msu.thief.problems.variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.interfaces.IVariable;
import com.msu.thief.exceptions.VariableNotValidException;
import com.msu.thief.util.StringUtil;

/**
 * The Tour provides an implementation of a tour that saves directly the
 * permutation array.
 */
public class Tour implements IVariable{

	// ! list where the tour is saved
	protected List<Integer> list = null;

	/**
	 * Create a Tour using a permutation vector. Starting city is not given!! So
	 * the zero is added to the beginning.
	 * 
	 * @param list
	 *            tour represented by permutation vector
	 */
	public Tour(List<Integer> list) {
		this.list = list;
	}

	
	/**
	 * @param idx
	 *            index to get the city of
	 * @return returns the ith city which is visited by the salesman.
	 */
	public int ith(int idx) {
		return list.get(idx);
	}
	
	
	/**
	 * @return number of cities which are visited
	 */
	public int numOfCities() {
		return list.size();
	}

	/**
	 * @return the symmetric tour
	 */
	public Tour getSymmetric() {
		List<Integer> sym = new ArrayList<>(list.size());
		sym.add(0);
		for (int i = list.size() - 1; i > 0; i--) {
			sym.add(list.get(i));
		}
		return new Tour(sym);
	}

	public Tour copy() {
		return new Tour(new ArrayList<>(list));
	}

	/**
	 * @return encode the tour to a list of integer
	 */
	public List<Integer> encode() {
		return list;
	}

	/**
	 * @return false if empty or starts not with 0. else true.
	 */
	public boolean startsWithZero() {
		if (list.isEmpty())
			return false;
		else
			return list.get(0) == 0;
	}

	/**
	 * @return true if the tour is a valid permutation and no duplicates are
	 *         contained.
	 */
	public boolean isPermutation() {

		boolean[] b = new boolean[list.size()];
		Arrays.fill(b, false);

		for (int i = 0; i < list.size(); i++) {
			int idx = list.get(i);
			// if the boolean flag is set, we had this value before
			if (idx > list.size() - 1 || b[idx]) {
				return false;
			}
			b[idx] = true;
		}
		// no values twice
		return true;
	}

	
	public void set(List<Integer> list) {
		this.list = list;
	}


	/**
	 * Checks whether the tour is valid. The city size has to match, it has to
	 * start with 0 and no duplicates are allowed. Otherwise this method will
	 * throw an exception.
	 */
	public void validate(int numOfCities) {

		if (list.size() != numOfCities)
			throw new VariableNotValidException(String
					.format("The tour size %s does not match with the number of cities %s", list.size(), numOfCities));

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
		List<Integer> tour = encode();
		Map<Integer, Integer> mCities = new HashMap<>(tour.size());
		for (int i = 0; i < tour.size(); i++) {
			mCities.put(tour.get(i) ,i);
		}
		return mCities;
	}

	

	@Override
	public int hashCode() {
		return list.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof Tour))
			return false;
		Tour obj = (Tour) other;
		return list.equals(obj.list);
	}

	@Override
	public String toString() {
		return Arrays.toString(list.toArray());
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

	// TODO: fix inheritance problem
	
	@Override
	public Object get() {
		return null;
	}


	@Override
	public void set(Object obj) {
	}


	@Override
	public <V extends IVariable> V cast(Class<V> clazz) {
		return null;
	}

	
	

}
