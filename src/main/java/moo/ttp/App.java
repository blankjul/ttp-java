package moo.ttp;

import java.util.Iterator;

import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import jmetal.metaheuristics.nsgaII.NSGAII;
import jmetal.operators.selection.SelectionFactory;
import jmetal.util.JMException;
import moo.ttp.jmetal.ThiefCrossover;
import moo.ttp.jmetal.ThiefMutation;
import moo.ttp.jmetal.ThiefProblem;
import moo.ttp.model.Item;
import moo.ttp.model.Map;
import moo.ttp.problems.TravellingThiefProblem;




public class App 
{
	
	
	public static TravellingThiefProblem example() {
		Map m = new Map(4);
        m.set(0,1,5);
        m.set(0,2,6);
        m.set(0,3,6);
        m.set(1,2,5);
        m.set(1,3,6);
        m.set(2,3,4);
        TravellingThiefProblem ttp  = new TravellingThiefProblem(m, 3);
        ttp.addItem(2, new Item(10, 3));
        ttp.addItem(2, new Item(4, 1));
        ttp.addItem(2, new Item(4, 1));
        ttp.addItem(1, new Item(2, 2));
        ttp.addItem(2, new Item(3, 3));
        ttp.addItem(3, new Item(2, 2));
		return ttp;
	}
	
	public static void main(String[] args) throws JMException {
		
		TravellingThiefProblem ttp = example();
        

		NSGAII nsga = new NSGAII(new ThiefProblem("ThiefSolutionType", ttp));
		nsga.setInputParameter("populationSize", 100);
		nsga.setInputParameter("maxEvaluations", 25000);
		nsga.addOperator("crossover", new ThiefCrossover());
		nsga.addOperator("mutation", new ThiefMutation());
		nsga.addOperator("selection",SelectionFactory.getSelectionOperator("BinaryTournament2", null));
		
		
		try {
			SolutionSet s = nsga.execute();
			Iterator<Solution> it = s.iterator();
			while (it.hasNext()) {
				System.out.println(it.next());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (JMException e) {
			e.printStackTrace();
		}
		
		
		
	}
}
