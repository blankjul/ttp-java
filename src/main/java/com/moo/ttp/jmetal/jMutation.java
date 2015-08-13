

package com.moo.ttp.jmetal;

import org.uma.jmetal.operator.MutationOperator;

import com.moo.ttp.operators.BitFlip;
import com.moo.ttp.operators.SwapMutation;

public class jMutation implements MutationOperator<jISolution> {


	public void doMutation(jISolution solution) {
			jVariable vars = (jVariable) solution.getVariableValue(0);
			SwapMutation.mutate(vars.tour.encode());
			BitFlip.mutate(vars.b);
	} 


	public jISolution execute(jISolution solution) {
		this.doMutation(solution);
		solution.removeConstraintViolations();
		return solution;
	} 
	
} 
