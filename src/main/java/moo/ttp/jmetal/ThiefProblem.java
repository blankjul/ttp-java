package moo.ttp.jmetal;

import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.util.JMException;
import moo.ttp.problems.TravellingThiefProblem;
import moo.ttp.util.Pair;

public class ThiefProblem extends Problem {

	private static final long serialVersionUID = -3964087384843545890L;
	
	public TravellingThiefProblem ttp = null;

	
	
	public ThiefProblem(String solutionType, TravellingThiefProblem ttp) {
		this(solutionType);
		this.ttp = ttp;
	}


	public ThiefProblem(String solutionType) {
		numberOfVariables_ = 1;
		numberOfObjectives_ = 2;
		numberOfConstraints_ = 0;
		problemName_ = "TravellingThiefProblem";

		if (solutionType.compareTo("ThiefSolutionType") == 0)
			solutionType_ = new ThiefSolutionType(this);
		else {
			System.out.println("Error: solution type " + solutionType + " invalid");
			System.exit(-1);
		}
	} 
	

	@Override
	public void evaluate(Solution solution) throws JMException {
		if (ttp == null) throw new RuntimeException("Please define the TTP problem!");
		ThiefVariable var = (ThiefVariable) solution.getDecisionVariables()[0];
		Pair<Double,Double> result = ttp.evaluate(var.pi, var.b);
		solution.setObjective(0, result.first);
		solution.setObjective(1, - result.second);
	} 


	
	
} 
