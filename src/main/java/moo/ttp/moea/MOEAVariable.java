package moo.ttp.moea;

import org.moeaframework.core.Variable;

public class MOEAVariable implements Variable {
	


	private static final long serialVersionUID = -1059809008416456642L;
	
	private Integer[] pi; 
	private Boolean[] pickingPlan;

	
	public MOEAVariable(Integer[] pi, Boolean[] pickingPlan) {
		super();
		this.pi = pi;
		this.pickingPlan = pickingPlan;
	}


	public Variable copy() {
		return new MOEAVariable(pi, pickingPlan);
	}


	public Integer[] getPi() {
		return pi;
	}


	public Boolean[] getPickingPlan() {
		return pickingPlan;
	}
	
	

}
