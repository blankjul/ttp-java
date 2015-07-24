package com.moo.ttp.jmetal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.uma.jmetal.solution.Solution;

public class jSolution implements jISolution {

	public Map<Object, Object> attributes = new HashMap<Object, Object>();
	public double[] objectives = null;
	public jVariable vars = null;

	public jSolution(jSolution s) {
		objectives = new double[s.getNumberOfObjectives()];
		for (int i = 0; i < objectives.length; i++) {
			objectives[i] = s.getObjective(i);
		}
		vars = (jVariable) s.getVariableValue(0).deepCopy();
	}

	public jSolution(int numOfCities, int numOfItems) {
		objectives = new double[2];
		vars = new jVariable(numOfCities, numOfItems);
	}

	public void setObjective(int index, double value) {
		objectives[index] = value;
	}

	public double getObjective(int index) {
		return objectives[index];
	}

	public jVariable getVariableValue(int index) {
		return vars;
	}

	public void setVariableValue(int index, jVariable value) {
		vars = value;
	}

	public String getVariableValueString(int index) {
		return vars.toString();
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
		return vars.toString() + " -> " + Arrays.toString(objectives);
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
		if (!vars.equals(that.vars))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = Arrays.hashCode(objectives);
		result = 31 * result + vars.hashCode();
		result = 31 * result + attributes.hashCode();
		return result;
	}
	

}
