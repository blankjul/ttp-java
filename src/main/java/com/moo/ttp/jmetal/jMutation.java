

package com.moo.ttp.jmetal;

import java.util.List;

import org.uma.jmetal.operator.MutationOperator;

import com.moo.ttp.operators.crossover.AbstractCrossover;
import com.moo.ttp.operators.mutation.AbstractMutation;
import com.moo.ttp.operators.mutation.SwapMutation;

public class jMutation implements MutationOperator<jISolution> {

	
	protected AbstractCrossover<T> c;
	
	protected AbstractMutation<T> m;

	public void doMutation(jISolution solution) {
			jVariable vars = (jVariable) solution.getVariableValue(0);
			SwapMutation.mutate(vars.tour.encode());
			BitFlip.mutate(vars.b);
	} 


	public jISolution execute(jISolution solution) {
		this.doMutation(solution);
		return solution;
	} 
	
} 
