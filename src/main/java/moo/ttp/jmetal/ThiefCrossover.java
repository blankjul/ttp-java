//  SBXCrossover.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package moo.ttp.jmetal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import jmetal.core.Solution;
import jmetal.core.Variable;
import jmetal.operators.crossover.Crossover;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import moo.ttp.operators.OnePointCrossover;
import moo.ttp.operators.PMXCrossover;
import moo.ttp.util.Pair;

public class ThiefCrossover extends Crossover {

	private static final long serialVersionUID = 1763711775865494445L;

	@SuppressWarnings("unchecked")
	private static final List<Class<ThiefSolutionType>> VALID_TYPES = Arrays.asList(ThiefSolutionType.class);

	
	public ThiefCrossover() {
		super(new HashMap<String, Object>());
	}
	
	public ThiefCrossover(HashMap<String, Object> parameters) {
		super(parameters);
	}

	public Solution[] doCrossover(Solution parent1, Solution parent2) throws JMException {
		Solution[] offSpring = new Solution[2];
		offSpring[0] = new Solution(parent1);
		offSpring[1] = new Solution(parent2);
		
		ThiefVariable p1 = (ThiefVariable) parent1.getDecisionVariables()[0];
		ThiefVariable p2 = (ThiefVariable) parent2.getDecisionVariables()[0];
		
		Pair<Integer[], Integer[]> tour = PMXCrossover.crossover(p1.pi, p2.pi);
		Pair<Boolean[], Boolean[]> b = OnePointCrossover.crossover(p1.b, p2.b);
		
		offSpring[0].setDecisionVariables(new Variable[] {new ThiefVariable(tour.first, b.first)});
		offSpring[1].setDecisionVariables(new Variable[] {new ThiefVariable(tour.second, b.second)});
		
		return offSpring;
	}

	/**
	 * Executes the operation
	 * 
	 * @param object
	 *            An object containing an array of two parents
	 * @return An object containing the offSprings
	 */
	public Object execute(Object object) throws JMException {
		Solution[] parents = (Solution[]) object;

		if (parents.length != 2) {
			Configuration.logger_.severe("ThiefCrossover.execute: operator needs two " + "parents");
			Class<String> cls = java.lang.String.class;
			String name = cls.getName();
			throw new JMException("Exception in " + name + ".execute()");
		}

		if (!(VALID_TYPES.contains(parents[0].getType().getClass()))) {
			Configuration.logger_.severe("ThiefCrossover.execute: the solutions " + "type " + parents[0].getType() + " is not allowed with this operator");
			Class<String> cls = java.lang.String.class;
			String name = cls.getName();
			throw new JMException("Exception in " + name + ".execute()");
		}

		Solution[] offSpring = doCrossover(parents[0], parents[1]);
		return offSpring;
	}

}
