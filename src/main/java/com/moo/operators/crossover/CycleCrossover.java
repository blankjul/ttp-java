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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.genetics.GeneticAlgorithm;

/**
 * This is the single point crossover where a list with any type could but cut
 * in a half and recombined with another half.
 * 
 * [0,1,2,3,4] and [4,3,2,1,0] and point is one leads to
 * 
 * [0] + [3,2,1,0] = [0,3,2,1,0] and [4] + [1,2,3,4] = [4,1,2,3,4]
 * 
 */
public class CycleCrossover<T> extends AbstractCrossover<List<T>> {

	protected boolean randomStart = true;

	@Override
	protected List<List<T>> crossover_(List<T> a, List<T> b) {

		int length = a.size();

		// and of the children: do a crossover copy to simplify the later
		// processing
		final List<T> child1Rep = new ArrayList<T>(a);
		final List<T> child2Rep = new ArrayList<T>(b);

		// the set of all visited indices so far
		final Set<Integer> visitedIndices = new HashSet<Integer>(length);
		// the indices of the current cycle
		final List<Integer> indices = new ArrayList<Integer>(length);

		// determine the starting index
		int idx = randomStart ? GeneticAlgorithm.getRandomGenerator().nextInt(length) : 0;
		int cycle = 1;

		while (visitedIndices.size() < length) {
			indices.add(idx);

			T item = b.get(idx);
			idx = a.indexOf(item);

			while (idx != indices.get(0)) {
				// add that index to the cycle indices
				indices.add(idx);
				// get the item in the second parent at that index
				item = b.get(idx);
				// get the index of that item in the first parent
				idx = a.indexOf(item);
			}

			// for even cycles: swap the child elements on the indices found in
			// this cycle
			if (cycle++ % 2 != 0) {
				for (int i : indices) {
					T tmp = child1Rep.get(i);
					child1Rep.set(i, child2Rep.get(i));
					child2Rep.set(i, tmp);
				}
			}

			visitedIndices.addAll(indices);
			// find next starting index: last one + 1 until we find an unvisited
			// index
			idx = (indices.get(0) + 1) % length;
			while (visitedIndices.contains(idx) && visitedIndices.size() < length) {
				idx++;
				if (idx >= length) {
					idx = 0;
				}
			}
			indices.clear();
		}

		return new ArrayList<>( Arrays.asList(child1Rep, child2Rep));

	}

}
