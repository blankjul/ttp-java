package moo.ttp.jmetal;

import jmetal.core.Problem;
import jmetal.core.SolutionType;
import jmetal.core.Variable;



public class ThiefSolutionType extends SolutionType {

	public ThiefSolutionType(Problem problem) {
		super(problem);
	}

	@Override
	public Variable[] createVariables() throws ClassNotFoundException {
		ThiefProblem p = (ThiefProblem) this.problem_;
		Variable[] vars = new Variable[1];
		vars[0] = new ThiefVariable(p.ttp.numOfCities(), p.ttp.numOfItems());
		return vars;
	}

}
