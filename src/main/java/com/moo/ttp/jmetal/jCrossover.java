

package com.moo.ttp.jmetal;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.util.JMetalException;

import com.moo.ttp.model.tour.StandardTour;
import com.moo.ttp.operators.crossover.AbstractCrossover;
import com.moo.ttp.operators.crossover.PMXCrossover;
import com.moo.ttp.operators.crossover.SinglePointCrossover;

public class jCrossover implements CrossoverOperator<jISolution> {


	public List<jISolution> doCrossover(jISolution parent1, jISolution parent2)  {
		
	    List<jISolution> offspring = new ArrayList<jISolution>(2);
	    offspring.add((jISolution) parent1.copy());
	    offspring.add((jISolution) parent2.copy());
		
		jVariable p1 = (jVariable) parent1.getVariableValue(0);
		jVariable p2 = (jVariable) parent2.getVariableValue(0);
		
		List<List<Integer>> tour = new PMXCrossover<Integer>().crossover(p1.tour.encode(), p2.tour.encode());
		List<List<Boolean>> b = new SinglePointCrossover<Boolean>().crossover(p1.b, p2.b);
		
		offspring.get(0).setVariableValue(0, new jVariable(p1.tour.create(tour.get(0)), b.first));
		offspring.get(1).setVariableValue(0, new jVariable(p1.tour.create(tour.get(1)), b.second));
		
		
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
