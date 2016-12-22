package com.msu.thief.experiment;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.algorithms.builder.NSGAIIBuilder;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.experiment.ExperimentCallback;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.solution.NonDominatedSet;
import com.msu.moo.util.Builder;
import com.msu.moo.util.FileCollectorParser;
import com.msu.thief.algorithms.MultiObjectiveGreedy;
import com.msu.thief.algorithms.subproblems.AlgorithmUtil;
import com.msu.thief.ea.ThiefCrossover;
import com.msu.thief.ea.ThiefCrossoverLocalOptimizeTour;
import com.msu.thief.ea.ThiefFactory;
import com.msu.thief.ea.ThiefMutation;
import com.msu.thief.ea.factory.PackOptimalFactory;
import com.msu.thief.ea.factory.TourOptimalFactory;
import com.msu.thief.ea.factory.TourOptimalWithSwapFactory;
import com.msu.thief.ea.factory.TourTwoOptFactory;
import com.msu.thief.ea.operators.PackBitflipMutation;
import com.msu.thief.ea.operators.PackSinglePointCrossover;
import com.msu.thief.ea.operators.TourEdgeRecombinationCrossover;
import com.msu.thief.ea.operators.TourFixedCrossover;
import com.msu.thief.ea.operators.TourSwapMutation;
import com.msu.thief.io.thief.reader.JsonThiefProblemReader;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.MultiObjectiveThiefProblem;
import com.msu.thief.problems.variable.TTPVariable;

public class BenchmarkEMO2017
		extends AExperiment<NonDominatedSet<ISolution<TTPVariable>>, TTPVariable, MultiObjectiveThiefProblem> {

	List<IAlgorithm<NonDominatedSet<ISolution<TTPVariable>>, TTPVariable, MultiObjectiveThiefProblem>> algorithms;
	List<MultiObjectiveThiefProblem> problems;
	private Integer iterations = 0;

	@Override
	protected void setProblems(List<MultiObjectiveThiefProblem> problems) {
		FileCollectorParser<AbstractThiefProblem> fcp = new FileCollectorParser<>();
		fcp.add("../ttp-benchmark/", "*.json", new JsonThiefProblemReader());
		fcp.collect().forEach(p -> problems.add((MultiObjectiveThiefProblem) p));

		this.problems = problems;

		try (FileWriter fw = new FileWriter("experiment/all")) {

			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);

			out.println(String.format("%s,%s,%s,%s,%s", "problem", "algorithm", "run",
					"time", "profit"));

			out.flush();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void setAlgorithms(MultiObjectiveThiefProblem problem,
			List<IAlgorithm<NonDominatedSet<ISolution<TTPVariable>>, TTPVariable, MultiObjectiveThiefProblem>> algorithms) {

		Builder<MultiObjectiveGreedy> b = new Builder<>(MultiObjectiveGreedy.class);
		b.set("name", "Greedy");
		algorithms.add(b.build());
		

		NSGAIIBuilder<TTPVariable, MultiObjectiveThiefProblem> builder = new NSGAIIBuilder<>();
		
		
		
		builder.set("name", "NSGAII-OPT-FIXED");
		builder.set("populationSize", 100);
		builder.set("probMutation", 0.3);
		builder.set("factory", new ThiefFactory(new TourOptimalFactory(problem), new PackOptimalFactory(problem)));
		builder.set("mutation", new ThiefMutation(new TourSwapMutation(), new PackBitflipMutation(problem)));
		builder.set("crossover", new ThiefCrossover(new TourFixedCrossover(), new PackSinglePointCrossover(problem)));
		builder.set("elimanateDuplicates", true);
		algorithms.add(builder.build());
		
		
		builder.set("name", "NSGAII-OPT-EDGE");
		builder.set("populationSize", 100);
		builder.set("probMutation", 0.3);
		builder.set("factory", new ThiefFactory(new TourOptimalFactory(problem), new PackOptimalFactory(problem)));
		builder.set("mutation", new ThiefMutation(new TourSwapMutation(), new PackBitflipMutation(problem)));
		builder.set("crossover", new ThiefCrossover(new TourEdgeRecombinationCrossover(), new PackSinglePointCrossover(problem)));
		builder.set("elimanateDuplicates", true);
		algorithms.add(builder.build());

		
		builder.set("name", "NSGAII-2OPT-EDGE");
		builder.set("populationSize", 100);
		builder.set("probMutation", 0.3);
		builder.set("factory", new ThiefFactory(new TourTwoOptFactory(problem), new PackOptimalFactory(problem)));
		builder.set("mutation", new ThiefMutation(new TourSwapMutation(), new PackBitflipMutation(problem)));
		builder.set("crossover", new ThiefCrossover(new TourEdgeRecombinationCrossover(), new PackSinglePointCrossover(problem)));
		builder.set("elimanateDuplicates", true);
		algorithms.add(builder.build());


		
		
		builder.set("name", "NSGAII-LOCAL-TOUR");
		builder.set("populationSize", 100);
		builder.set("probMutation", 0.3);
		builder.set("factory",
				new ThiefFactory(new TourOptimalWithSwapFactory(problem), new PackOptimalFactory(problem)));
		builder.set("mutation", new ThiefMutation(new TourSwapMutation(), new PackBitflipMutation(problem)));
		builder.set("crossover", new ThiefCrossoverLocalOptimizeTour(new PackSinglePointCrossover(problem), problem,
				AlgorithmUtil.calcBestTour(problem)));
		builder.set("elimanateDuplicates", true);
		// algorithms.add(builder.build());
		
		
		this.algorithms = algorithms;


	}

	@Override
	protected void analyse(
			ExperimentCallback<NonDominatedSet<ISolution<TTPVariable>>, TTPVariable, MultiObjectiveThiefProblem> callback) {
		super.analyse(callback);

		final String pathToFile = String.format("experiment/%s_%s_%s", callback.problem, callback.algorithm,
				callback.k);

		try (PrintWriter out = new PrintWriter(pathToFile)) {

			for (ISolution<TTPVariable> s : callback.result) {
				out.println(String.format("%f,%f,\"%s\"", s.getObjective(0), s.getObjective(1), s.getVariable()));
			}

			out.flush();
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try (FileWriter fw = new FileWriter("experiment/all", true)) {

			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);

			for (ISolution<TTPVariable> s : callback.result) {
				out.println(String.format("%s,%s,%s,%f,%f", callback.problem, callback.algorithm, callback.k,
						s.getObjective(0), -s.getObjective(1)));
			}

			out.flush();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		iterations = Math.max(iterations, callback.k);

	}

	@Override
	public void finalize() {
		super.finalize();

		final String pathToFile = "experiment/experiment.json";

		try {
			FileOutputStream fos = new FileOutputStream(pathToFile);
			JsonGenerator json = new JsonFactory().createGenerator(fos, JsonEncoding.UTF8);

			DefaultPrettyPrinter pp = new DefaultPrettyPrinter();
			pp.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
			json.setPrettyPrinter(pp);

			json.writeStartObject();

			json.writeArrayFieldStart("algorithms");
			for (IAlgorithm<NonDominatedSet<ISolution<TTPVariable>>, TTPVariable, MultiObjectiveThiefProblem> a : algorithms) {
				json.writeString(a.toString());
			}
			json.writeEndArray();

			json.writeArrayFieldStart("problems");
			for (MultiObjectiveThiefProblem a : problems) {
				json.writeString(a.toString());
			}
			json.writeEndArray();

			json.writeObjectField("iterations", iterations + 1);

			json.writeEndObject();

			json.close();
			fos.close();

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(String.format("Could not write to file %s.", pathToFile));
		}

	}

}