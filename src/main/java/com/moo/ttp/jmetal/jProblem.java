package com.moo.ttp.jmetal;

import org.uma.jmetal.problem.impl.AbstractGenericProblem;

import com.moo.ttp.problems.TravellingThiefProblem;
import com.moo.ttp.util.Pair;

public class jProblem extends AbstractGenericProblem<jISolution> implements jIProblem {

	public TravellingThiefProblem ttp = null;
	
	
	
	public jProblem(TravellingThiefProblem ttp) {
		this.ttp = ttp;
		this.setNumberOfObjectives(2);
		this.setNumberOfVariables(1);
	}

	public void evaluate(jISolution solution) {
		jVariable var = (jVariable) solution.getVariableValue(0);
		Pair<Double, Double> result = ttp.evaluate(var.pi, var.b);
		solution.setObjective(0, result.first);
		solution.setObjective(1, -result.second);
	}

	public jISolution createSolution() {
		return new jSolution(ttp);
	}
	
	@Override
	public String toString() {
		return ttp.toString();
	}

}
