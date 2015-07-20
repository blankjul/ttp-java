package moo.ttp.moea;

import moo.ttp.adapter.MOEAVariable;

import org.moeaframework.core.Solution;
import org.moeaframework.core.Variation;

public class MOEAVariation implements Variation {

	public int getArity() {
		return 2;
	}

	
	
	public Solution[] evolve(Solution[] parents) {
		

		MOEAVariable var1 = (MOEAVariable) parents[0].getVariable(0);
		MOEAVariable var2 = (MOEAVariable) parents[1].getVariable(0);
		
		
		Solution[] solutions = new Solution[2];
		solutions[0] = new Solution(1, 2);
		//solutions[0].setVariable(0, new MOEAVariable(perm1.toArray(), plan1));
		
		solutions[1] = new Solution(1, 2);
		//solutions[1].setVariable(0, new MOEAVariable(perm2.toArray(), plan2));
		
		return solutions;
	}
	



}
