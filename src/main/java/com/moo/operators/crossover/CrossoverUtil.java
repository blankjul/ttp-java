package com.moo.operators.crossover;

import org.apache.commons.math3.genetics.GeneticAlgorithm;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.util.FastMath;

import com.moo.ttp.util.Pair;

public class CrossoverUtil {
	
	/**
	 * Return always a section from a allele, which means a lower and a upper bound.
	 * @param length maximal length
	 * @return pair of integer. lb and ub.
	 */
	public static Pair<Integer, Integer> getSection(int length) {
        final RandomGenerator random = GeneticAlgorithm.getRandomGenerator();
        
        // choose random points, making sure that lb < ub.
        int rnd1 = random.nextInt(length);
        int rnd2;
        do {
            rnd2 = random.nextInt(length);
        } while (rnd1 == rnd2);
        
        // determine the lower and upper bounds
        final int lb = FastMath.min(rnd1, rnd2);
        final int ub = FastMath.max(rnd1, rnd2);
        
        return new Pair<Integer, Integer>(lb, ub);

	}

}
