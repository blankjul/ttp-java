

package com.moo.ttp.jmetal;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.util.JMetalException;

import com.moo.ttp.operators.OnePointCrossover;
import com.moo.ttp.operators.PMXCrossover;
import com.moo.ttp.util.Pair;

public class jCrossover implements CrossoverOperator<jISolution> {



	public List<jISolution> doCrossover(jISolution parent1, jISolution parent2)  {
		
	    List<jISolution> offspring = new ArrayList<jISolution>(2);
	    offspring.add((jISolution) parent1.copy());
	    offspring.add((jISolution) parent2.copy());
		
		jVariable p1 = (jVariable) parent1.getVariableValue(0);
		jVariable p2 = (jVariable) parent2.getVariableValue(0);
		
		Pair<Integer[], Integer[]> tour = PMXCrossover.crossover(p1.pi, p2.pi);
		Pair<Boolean[], Boolean[]> b = OnePointCrossover.crossover(p1.b, p2.b);
		
		offspring.get(0).setVariableValue(0, new jVariable(tour.first, b.first));
		offspring.get(1).setVariableValue(0, new jVariable(tour.second, b.second));
		
		offspring.get(0).removeConstraintViolations();
		offspring.get(1).removeConstraintViolations();
		
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
