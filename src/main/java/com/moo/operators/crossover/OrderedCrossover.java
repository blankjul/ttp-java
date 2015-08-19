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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.moo.ttp.util.Pair;


public class OrderedCrossover<T> extends AbstractCrossover<List<T>> {


	
	protected List<T> crossover_(List<T> a, List<T> b, int lb, int ub) {
		
		// create structures for the child
		final int length = a.size();
		final List<T> child = new ArrayList<T>(length);
		final Set<T> hash = new HashSet<T>(length);
		
		// add connected values of the first parent -> from lb to ub
		child.addAll(a.subList(lb, ub + 1));
		hash.addAll(child);
		
	     // iterate over every item from the parent starting right from ub
        for (int i = 1; i <= length; i++) {
            final int idx = (ub + i) % length;
            final T item = b.get(idx);

            // if the first child already contains the item in the second parent add it
            if (!hash.contains(item)) {
                child.add(item);
                hash.add(item);
            }
        }
        
        Collections.rotate(child, lb);
		return child;
	}
	
	
	@Override
	protected List<List<T>> crossover_(List<T> a, List<T> b) {
        Pair<Integer, Integer> bounds = CrossoverUtil.getSection(a.size());

		return new ArrayList<>( Arrays.asList(crossover_(a, b, bounds.first, bounds.second), 
				crossover_(b, a, bounds.first, bounds.second)));

	}

}
