package com.moo.operators.mutation;

import java.util.List;

import com.moo.ttp.util.Rnd;

/**
 * This is a basic BitFlipMutation which is allowed on all List<Boolean> objects.
 * There are two objects: there could be a fix probability or it is 1/length of object.
 * 
 * [0,0,1,0] ----> [1,0,1,0]
 *
 */
public class RestrictedPolynomialMutation extends AbstractMutation<List<Integer>> {

	//! the probability that a bit is changed
	protected Double probability = null;
	
	/**
	 * Normal constructor with dynamic probability 
	 */
	public RestrictedPolynomialMutation() {
		super();
	}

	/**
	 * Constructor which fixes the probability for all inputs.
	 * @param probability that a bit is changed
	 */
	public RestrictedPolynomialMutation(Double probability) {
		super();
		this.probability = probability;
	}


	@Override
	protected void mutate_(List<Integer> b) {
		if (probability == null) probability = 1 / (double) b.size();
		for (int i = 0; i < b.size(); i++) {
			if (Rnd.rndDouble() < probability) b.set(i, Rnd.rndInt(0, i));
		}
	}

}
