/**
 * This package contains all the TSP stuff which is useful to solve or handle the TSP problem.
 * 
 * The salesman has to visit each city exactly once. The mathematical formulation:
 * 
 * \f$min \; f(\pi,v) = \sum_{i=1}^{n} \frac{ d(\pi_i, \pi_{i+1})}{v(\pi_i)}\f$ with subject to 
 * 
 * \f$s.t. \; \pi = (\pi_1, \pi_2, ..., \pi_n) \in P_n \f$ 
 * 
 *  The package contains a few example scenarios taken from the
 *  <a href="http://comopt.ifi.uni-heidelberg.de/software/TSPLIB95/">TSPLIB</a> which are on the 
 *  one hand for testing the correctness and on the other hand as scenario examples for the algorithms.
 *  
 *  
 *  
 *  
 *  
 *  
 * 
 */
package com.msu.tsp;