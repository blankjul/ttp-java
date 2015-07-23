package com.moo.ttp.experiment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.qualityindicator.impl.Epsilon;
import org.uma.jmetal.qualityindicator.impl.ErrorRatio;

import com.moo.ttp.jmetal.jISolution;
import com.moo.ttp.util.Pair;

public class Experiment {

	Map<Pair<Problem<jISolution>, Algorithm<List<jISolution>>>, List<List<jISolution>>> fronts;
	Map<Problem<jISolution>, List<jISolution>> ref;
	
	public static final String[] INDICATORS = new String[] {"eps", "error"};
	

	public Experiment() {
		super();
		fronts = new HashMap<Pair<Problem<jISolution>,Algorithm<List<jISolution>>>, List<List<jISolution>>>();
		ref = new HashMap<Problem<jISolution>, List<jISolution>>();
	}

	private <A, B, C> void add(Map<Pair<A, B>, List<C>> m, A a, B b, C c) {
		Pair<A, B> key = Pair.create(a, b);
		if (m.containsKey(key)) {
			List<C> entry = m.get(key);
			entry.add(c);
		} else {
			List<C> entry = new ArrayList<C>();
			entry.add(c);
			m.put(key, entry);
		}
	}

	public void addFront(Problem<jISolution> p, Algorithm<List<jISolution>> a, List<jISolution> front) {
		add(fronts, p, a, front);
	}

	public void addReferenceFront(Problem<jISolution> p, List<jISolution> front) {
		ref.put(p, front);
	}

	public List<List<jISolution>> getFronts(Problem<jISolution> p, Algorithm<List<jISolution>> a) {
		return fronts.get(Pair.create(p, a));
	}

	public List<jISolution> getReferenceFront(Problem<jISolution> p) {
		return ref.get(p);
	}

	public Map<String, double[]> calcIndicator(Problem<jISolution> p, Algorithm<List<jISolution>> a) {
		Map<String, double[]> h = new HashMap<String, double[]>();

		List<List<jISolution>> fronts = getFronts(p, a);

		// add the epsilon values
		Epsilon eps = new Epsilon();
		double[] epsilon = new double[fronts.size()];
		for (int i = 0; i < epsilon.length; i++) {
			epsilon[i] = eps.execute(fronts.get(i), getReferenceFront(p));
		}
		h.put("eps", epsilon);

		// add the error values
		ErrorRatio err = new ErrorRatio();
		double[] error = new double[fronts.size()];
		for (int i = 0; i < epsilon.length; i++) {
			error[i] = err.execute(fronts.get(i), getReferenceFront(p));
		}
		h.put("error", error);

		return h;

	}

}
