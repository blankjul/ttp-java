package com.msu.thief;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msu.moo.Configuration;
import com.msu.moo.fonseca.EmpiricalAttainmentFunction;
import com.msu.moo.fonseca.Hypervolume;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.solution.NonDominatedSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.Range;

public class ExperimentEvaluation {

	public static void main(String[] args) throws JsonProcessingException, IOException {

		BasicConfigurator.configure();
		Configuration.PATH_TO_EAF = "../moo-java/vendor/bin/eaf";
		Configuration.PATH_TO_HYPERVOLUME = "../moo-java/vendor/bin/hv";

		BufferedReader br = new BufferedReader(new FileReader("experiment/experiment.json"));
		JsonNode root = new ObjectMapper().readTree(br);

		List<String> algorithms = new ArrayList<>();
		for (JsonNode n : root.findValue("algorithms")) {
			algorithms.add(n.asText());
		}

		List<String> problems = new ArrayList<>();
		for (JsonNode n : root.findValue("problems")) {
			problems.add(n.asText());
		}

		final int iterations = root.findValue("iterations").asInt();

		for (String p : problems) {

			HashMap<String, SolutionSet<ISolution<IVariable>>> m = new HashMap<>();
			HashMap<String, SolutionSet<ISolution<Object>>> median = new HashMap<>();
			HashMap<String, SolutionSet<ISolution<Object>>> normalized = new HashMap<>();

			for (String a : algorithms) {
				SolutionSet<ISolution<IVariable>> set = new SolutionSet<>();
				for (int i = 0; i < iterations; i++) {

					// read the Pareto front
					br = new BufferedReader(new FileReader(String.format("experiment/%s_%s_%s", p, a, i)));
					String line;
					while ((line = br.readLine()) != null) {
						String[] array = line.split(",");
						Solution<IVariable> solution = new Solution<IVariable>(null,
								Arrays.asList(Double.valueOf(array[0]), Double.valueOf(array[1])));
						set.add(solution);
					}
					m.put(String.format("%s_%s_%s", p, a, i), set);
				}
			}

			// get the whole range for normalization
			NonDominatedSet<ISolution<Object>> all = new NonDominatedSet<>();
			for (String a : algorithms) {
				Collection<Collection<ISolution<IVariable>>> frontsOfIterations = new ArrayList<>();
				for (int i = 0; i < iterations; i++) {

					Collection<ISolution<IVariable>> front = m.get(String.format("%s_%s_%s", p, a, i));
					frontsOfIterations.add(front);

				}
				NonDominatedSet<ISolution<Object>> medianFront = EmpiricalAttainmentFunction
						.calculate(frontsOfIterations);
				median.put(String.format("%s_%s", p, a), medianFront.getSolutions());

				for (ISolution<Object> s : medianFront.getSolutions()) {
					all.add(s);
				}

			}

			Range<Double> ranges = all.getSolutions().getRange();

			
			
			// caclulate the normalized fronts
			for (String a : algorithms) {
				SolutionSet<ISolution<Object>> normalizedFront = new SolutionSet<>();
				for (ISolution<Object> s : median.get(String.format("%s_%s", p, a))) {
					List<Double> obj = new ArrayList<>();
					for (int i = 0; i < s.getObjectives().size(); i++) {
						double value = (s.getObjective(i) - ranges.getMinimum(i))
								/ (ranges.getMaximum(i) - ranges.getMinimum(i));
						if (value < 0 ) value = 0;
						else if (value > 1) value = 1;
						obj.add(value);
						normalizedFront.add(new Solution<Object>(null, obj));
					}
				}
				normalized.put(String.format("%s_%s", p, a), normalizedFront);

				System.out.println(String.format("%s,%s,%f", p,a, Hypervolume.calculate(normalized.get(String.format("%s_%s", p,a)))));
			}
			
			
			
			// print out the median fronts in a file
			try (PrintWriter out = new PrintWriter(String.format("experiment/%s", p))) {
				out.println("algorithm,time,profit");
				for (String a : algorithms) {
					for (ISolution<Object> s : median.get(String.format("%s_%s", p,a))) {
						out.println(String.format("%s,%f,%f", a, s.getObjective(0), s.getObjective(1)));
					}
				}
				out.flush();
				out.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			
			
			

			System.out.println();

		}

	}

}
