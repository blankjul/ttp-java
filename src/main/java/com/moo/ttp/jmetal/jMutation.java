

package com.moo.ttp.jmetal;

import java.util.List;

import org.uma.jmetal.operator.MutationOperator;

import com.moo.operators.mutation.AbstractMutation;
import com.moo.operators.mutation.BitFlipMutation;
import com.moo.operators.mutation.SwapMutation;

public class jMutation implements MutationOperator<jISolution> {

	
	AbstractMutation<List<Integer>> mtour = new SwapMutation<>();
	AbstractMutation<List<Boolean>> mpacking = new BitFlipMutation();
	
	
	public jMutation() {
		super();
	}


	public jMutation(AbstractMutation<List<Integer>> mtour, AbstractMutation<List<Boolean>> mpacking) {
		super();
		this.mtour = mtour;
		this.mpacking = mpacking;
	}


	public void doMutation(jISolution solution) {
			jVariable vars = (jVariable) solution.getVariableValue(0);
			mtour.mutate(vars.tour.get());
			mpacking.mutate(vars.b.get());
	} 


	public jISolution execute(jISolution solution) {
		this.doMutation(solution);
		return solution;
	} 
	
} 
