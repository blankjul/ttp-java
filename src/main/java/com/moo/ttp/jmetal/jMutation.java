

package com.moo.ttp.jmetal;

import org.uma.jmetal.operator.MutationOperator;

import com.moo.ttp.operators.mutation.BitFlipMutation;
import com.moo.ttp.operators.mutation.SwapMutation;

public class jMutation implements MutationOperator<jISolution> {

	
	public void doMutation(jISolution solution) {
			jVariable vars = (jVariable) solution.getVariableValue(0);
			new SwapMutation<>().mutate(vars.tour.get());
			new BitFlipMutation().mutate(vars.b.get());
	} 


	public jISolution execute(jISolution solution) {
		this.doMutation(solution);
		return solution;
	} 
	
} 
