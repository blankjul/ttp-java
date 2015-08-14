package com.moo.ttp.jmetal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.uma.jmetal.solution.Solution;

import com.moo.ttp.problems.ttp.TravellingThiefProblem;

public class jSolution implements jISolution {

	/// Variable for saving attributes -> jMetal
	protected Map<Object, Object> attributes = new HashMap<Object, Object>();
	
	/// objective values for each solution
	protected double[] objectives = null;
	
	/// variable that are the input
	protected jVariable variables = null;
	
	/// the traveling thief problem which determines the constrains for this solution
	protected TravellingThiefProblem ttp = null; 
	

	/**
	 * Copy constructor for the solutions
	 * @param s jSolution to be copied.
	 */
	public jSolution(jSolution s) {
		objectives = s.objectives.clone();
		variables = (jVariable) s.getVariableValue(0).deepCopy();
		this.ttp = s.ttp;
	}

	/**
	 * Create a new solution according to the given problem
	 * @param ttp that defines the length of pi and b
	 */
	public jSolution(TravellingThiefProblem ttp) {
		this.ttp = ttp;
		objectives = new double[2];
		variables = new jVariable(ttp.numOfCities(), ttp.numOfItems());
	}
	

	public void setObjective(int index, double value) {
		objectives[index] = value;
	}

	public double getObjective(int index) {
		return objectives[index];
	}

	public jVariable getVariableValue(int index) {
		return variables;
	}

	public void setVariableValue(int index, jVariable value) {
		variables = value;
	}

	public String getVariableValueString(int index) {
		return variables.toString();
	}

	public int getNumberOfVariables() {
		return 1;
	}

	public int getNumberOfObjectives() {
		return 2;
	}

	public double getOverallConstraintViolationDegree() {
		return 0;
	}

	public void setOverallConstraintViolationDegree(double violationDegree) {
	}

	public int getNumberOfViolatedConstraints() {
		return 0;
	}

	public void setNumberOfViolatedConstraints(int numberOfViolatedConstraints) {
	}

	public Solution<jVariable> copy() {
		return new jSolution(this);
	}

	public void setAttribute(Object id, Object value) {
		attributes.put(id, value);
	}

	public Object getAttribute(Object id) {
		return attributes.get(id);
	}
	
	@Override
	public String toString() {
		return variables.toString() + " -> " + Arrays.toString(objectives);
	}
	
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		jSolution that = (jSolution) o;

		if (!attributes.equals(that.attributes))
			return false;
		if (!Arrays.equals(objectives, that.objectives))
			return false;
		if (!variables.equals(that.variables))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = Arrays.hashCode(objectives);
		result = 31 * result + variables.hashCode();
		result = 31 * result + attributes.hashCode();
		return result;
	}

	
	
	

}
