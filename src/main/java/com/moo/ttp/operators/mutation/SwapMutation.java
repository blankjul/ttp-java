package com.moo.ttp.operators.mutation;


import java.util.List;

import com.moo.ttp.util.Rnd;
import com.moo.ttp.util.Util;

/**
 * This is a SwapMutation which allows to define the range of the mutation. It
 * is only allowed on lists otherwise there will be an error thrown.
 * 
 * 	[0,1,2,3,4] -> swap index 1 and 3 -> [0,3,2,1,4]
 * 
 * The limitation of the swap mutation range could be useful the fix some values.
 * For example an array should only be changed in the middle and not in the beginning.
 *
 * @param <T> Type which the List is going to have
 */
public class SwapMutation<T> extends AbstractMutation<List<T>>{

	
	//! minimal index to swap
	protected Integer minIndex = 0;
	
	//! maximal index to swap
	protected Integer maxIndex = null;
	
	
	/**
	 * Create a standard swap mutation
	 */
	public SwapMutation() {
		super();
	}
	
	
	/**
	 * Constructor for only set the minimal index
	 */
	public SwapMutation(Integer minIndex) {
		super();
		this.minIndex = minIndex;
	}

	
	/**
	 * Constructor for setting the whole range of swapping
	 */
	public SwapMutation(Integer minIndex, Integer maxIndex) {
		super();
		this.minIndex = minIndex;
		this.maxIndex = maxIndex;
	}



	@Override
	protected void mutate_(List<T> obj) {
		
		if (maxIndex == null) maxIndex = obj.size()-1;
		
		// search for two random positions
		int a = Rnd.rndInt(minIndex, maxIndex);
		int b = Rnd.rndInt(minIndex, maxIndex);
		
		// swap this two!
		Util.swap(obj, a, b);
		
	}
	

}
