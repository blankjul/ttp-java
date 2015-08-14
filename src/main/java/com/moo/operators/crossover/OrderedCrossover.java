/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.moo.operators.crossover;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.genetics.GeneticAlgorithm;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.util.FastMath;


public class OrderedCrossover<T> extends AbstractCrossover<List<T>> {


	@Override
	protected List<List<T>> crossover_(List<T> p1, List<T> p2) {

		final int length = p1.size();
		
		
        // array representations of the parents
        final List<T> parent1Rep = p1;
        final List<T> parent2Rep = p2;
        
        // and of the children
        final List<T> child1 = new ArrayList<T>(length);
        final List<T> child2 = new ArrayList<T>(length);
        // sets of already inserted items for quick access
        final Set<T> child1Set = new HashSet<T>(length);
        final Set<T> child2Set = new HashSet<T>(length);

        final RandomGenerator random = GeneticAlgorithm.getRandomGenerator();
        // choose random points, making sure that lb < ub.
        int a = random.nextInt(length);
        int b;
        do {
            b = random.nextInt(length);
        } while (a == b);
        // determine the lower and upper bounds
        final int lb = FastMath.min(a, b);
        final int ub = FastMath.max(a, b);

        // add the subLists that are between lb and ub
        child1.addAll(parent1Rep.subList(lb, ub + 1));
        child1Set.addAll(child1);
        child2.addAll(parent2Rep.subList(lb, ub + 1));
        child2Set.addAll(child2);

        // iterate over every item in the parents
        for (int i = 1; i <= length; i++) {
            final int idx = (ub + i) % length;

            // retrieve the current item in each parent
            final T item1 = parent1Rep.get(idx);
            final T item2 = parent2Rep.get(idx);

            // if the first child already contains the item in the second parent add it
            if (!child1Set.contains(item2)) {
                child1.add(item2);
                child1Set.add(item2);
            }

            // if the second child already contains the item in the first parent add it
            if (!child2Set.contains(item1)) {
                child2.add(item1);
                child2Set.add(item1);
            }
        }

        // rotate so that the original slice is in the same place as in the parents.
        Collections.rotate(child1, lb);
        Collections.rotate(child2, lb);

		// create the results
		List<List<T>> result = new ArrayList<>();
		result.add(child1);
		result.add(child2);
		return result;


	}

}
