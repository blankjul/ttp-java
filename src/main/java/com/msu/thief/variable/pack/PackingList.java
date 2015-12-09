package com.msu.thief.variable.pack;

import java.util.List;
import java.util.Set;

import com.msu.model.variables.Variable;

/**
 * This general class provides a packing list. The implementation could be
 * realized with several structure.
 *
 * @param <T>
 *            structure that is used internally
 */
public abstract class PackingList<T> extends Variable<T> {

	/**
	 * Initialize PackingList by using the internal structure
	 * 
	 * @param obj
	 */
	public PackingList(T obj) {
		super(obj);
	}
	

	/**
	 * Every PackingList could be encoded to an binary decision vector.
	 * 
	 * @return binary decision vector
	 */
	abstract public List<Boolean> encode();

	/**
	 * @return set which contains all indices of items which are picked
	 */
	abstract public Set<Integer> toIndexSet();

	/**
	 * @return all items which are missing at the current picking vector
	 */
	abstract public Set<Integer> getNotPickedItems();

	/**
	 * @param index
	 *            of the item
	 * @return true if item is picked else false
	 */
	abstract public boolean isPicked(int index);

	/**
	 * @return whether any item is picked or not
	 */
	abstract public boolean isAnyPicked();

}