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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.moo.ttp.util.Rnd;
import com.rits.cloning.Cloner;

public class EdgeRecombinationCrossover<T> extends AbstractCrossover<List<T>> {

	private void addEdgesToMap(Map<T, HashSet<T>> map, List<T> l) {

		final int length = l.size();
		for (int i = 0; i < length; i++) {

			// get index from last and next
			int last = (i + length - 1) % length;
			int next = (i + 1) % length;

			HashSet<T> set = null;
			T key = l.get(i);
			if (map.containsKey(key))
				set = map.get(key);
			else {
				set = new HashSet<>();
				map.put(key, set);
			}

			set.add(l.get(last));
			set.add(l.get(next));

		}
	}

	protected List<T> crossover_(Map<T, HashSet<T>> map, List<T> l, int point) {

		List<T> child = new ArrayList<>();
		T next = l.get(point);

		while (child.size() + 1 < l.size()) {

			// always add the next item
			child.add(next);
			
			// get all possible next items
			ArrayList<T> nextList = new ArrayList<>(map.get(next));

			// remove all occurrences of last element from the map if not empty
			map.remove(next);
			for (T entry : nextList) {
				map.get(entry).remove(next);
			}
			
			// if there is no next node of the next one choose random!
			if (nextList.isEmpty()) {
				List<T> asList = new ArrayList<>(map.keySet());
				Collections.shuffle(asList);
				next = asList.get(0);
				
			// else choose node with less spread of parents
			} else {

				// choose the next best node
				int minimalSize = Integer.MAX_VALUE;
				List<T> choice = new ArrayList<>();
				for (T item : nextList) {

					int size = map.get(item).size();
					if (size == 0) size = Integer.MAX_VALUE;
					// if real new minimum clear the list
					if (size < minimalSize) {
						choice.clear();
						minimalSize = size;
					}
					// if just equal do not clear -> tie decision!
					if (size <= minimalSize)
						choice.add(item);
				}

				int idx = Rnd.rndInt(choice.size());
				next = choice.get(idx);

			}
			
		}
		
		// add last node to get the whole path
		child.add(next);

		return child;

	}

	@Override
	protected List<List<T>> crossover_(List<T> a, List<T> b) {

		// create combined edge map
		Map<T, HashSet<T>> map = new HashMap<>();
		addEdgesToMap(map, a);
		addEdgesToMap(map, b);
		
		// for creating a deep clone
		Cloner cloner=new Cloner();

		return new ArrayList<>(Arrays.asList(crossover_(cloner.deepClone(map), a, 0),
				crossover_(cloner.deepClone(map), b, 0)));

	}

}
