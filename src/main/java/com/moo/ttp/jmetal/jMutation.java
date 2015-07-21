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

package com.moo.ttp.jmetal;

import org.uma.jmetal.operator.MutationOperator;

import com.moo.ttp.operators.BitFlip;
import com.moo.ttp.operators.SwapMutation;

public class jMutation implements MutationOperator<jISolution> {


	public void doMutation(jISolution solution) {
			jVariable vars = (jVariable) solution.getVariableValue(0);
			SwapMutation.mutate(vars.pi);
			BitFlip.mutate(vars.b);

	} 


	public jISolution execute(jISolution solution) {
		this.doMutation(solution);
		return solution;
	} 
	
} 
