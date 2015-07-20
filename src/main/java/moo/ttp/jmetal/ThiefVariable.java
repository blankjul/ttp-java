package moo.ttp.jmetal;

import java.util.Arrays;

import jmetal.core.Variable;
import moo.ttp.util.Util;

public class ThiefVariable extends Variable {
	


	private static final long serialVersionUID = -1059809008416456642L;
	
	public Integer[] pi; 
	public Boolean[] b;
	

	public ThiefVariable(int numOfCities, int numOfItems) {
		this.pi = Util.createRandomTour(numOfCities);
		this.b = Util.createRandomPickingPlan(numOfItems);
	}
	
	public ThiefVariable(Integer[] pi, Boolean[] b) {
		super();
		this.pi = pi;
		this.b = b;
	}

	@Override
	public Variable deepCopy() {
		return new ThiefVariable(pi.clone(), b.clone());
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(Arrays.toString(pi));
		sb.append("->");
		sb.append(Arrays.toString(b));
		return sb.toString();
	}
	
	

}
