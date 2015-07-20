//  SwapMutation.java
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
import jmetal.operators.mutation.Mutation;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import moo.ttp.operators.BitFlip;
import moo.ttp.operators.SwapMutation;

public class ThiefMutation extends Mutation {


	private static final long serialVersionUID = 6824079184479036015L;

	@SuppressWarnings("unchecked")
	private static final List<Class<ThiefSolutionType>> VALID_TYPES = Arrays.asList(ThiefSolutionType.class);

	public ThiefMutation() {
		super(new HashMap<String, Object>());
	}
	
	public ThiefMutation(HashMap<String, Object> parameters) {
		super(parameters);
	}

	public void doMutation(Solution solution) throws JMException {
		if (solution.getType().getClass() == ThiefSolutionType.class) {
			ThiefVariable vars = (ThiefVariable) solution.getDecisionVariables()[0];
			SwapMutation.mutate(vars.pi);
			BitFlip.mutate(vars.b);
		} 
		else {
			Configuration.logger_.severe("ThiefMutation.doMutation: invalid type. " + "" + solution.getDecisionVariables()[0].getVariableType());
			Class<String> cls = java.lang.String.class;
			String name = cls.getName();
			throw new JMException("Exception in " + name + ".doMutation()");
		}
	} 


	
	public Object execute(Object object) throws JMException {
		Solution solution = (Solution) object;

		if (!VALID_TYPES.contains(solution.getType().getClass())) {
			Configuration.logger_.severe("ThiefMutation.execute: the solution " + "is not of the right type. The type should be 'ThiefSolutionType'"
					+ ", but " + solution.getType() + " is obtained");
			Class<String> cls = java.lang.String.class;
			String name = cls.getName();
			throw new JMException("Exception in " + name + ".execute()");
		} 

		this.doMutation(solution);
		return solution;
	} 
	
} 
