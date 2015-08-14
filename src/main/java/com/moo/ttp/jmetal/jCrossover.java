

package com.moo.ttp.jmetal;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.util.JMetalException;

import com.moo.ttp.operators.crossover.PMXCrossover;
import com.moo.ttp.operators.crossover.SinglePointCrossover;

public class jCrossover implements CrossoverOperator<jISolution> {


	public List<jISolution> doCrossover(jISolution parent1, jISolution parent2)  {
		
	    List<jISolution> offspring = new ArrayList<jISolution>(2);
	    offspring.add((jISolution) parent1.copy());
	    offspring.add((jISolution) parent2.copy());
		
		jVariable p1 = (jVariable) parent1.getVariableValue(0);
		jVariable p2 = (jVariable) parent2.getVariableValue(0);
		
		List<List<Integer>> tour = new PMXCrossover<Integer>().crossover(p1.tour.get(), p2.tour.get());
		List<List<Boolean>> plan = new SinglePointCrossover<Boolean>().crossover(p1.b.get(), p2.b.get());
		
		offspring.get(0).getVariableValue(0).tour.set(tour.get(0));
		offspring.get(1).getVariableValue(0).tour.set(tour.get(1));
		
		offspring.get(0).getVariableValue(0).b.set(plan.get(0));
		offspring.get(1).getVariableValue(0).b.set(plan.get(1));

		return offspring;
	}

	public List<jISolution> execute(List<jISolution> parents) {
		if (null == parents) {
	      throw new JMetalException("Null parameter") ;
	    } else if (parents.size() != 2) {
	      throw new JMetalException("There must be two parents instead of " + parents.size()) ;
	    }
	    return doCrossover(parents.get(0), parents.get(1)) ;
	}

}
