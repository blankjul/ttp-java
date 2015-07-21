package com.moo.ttp.experiment;


public class TTPExperiment {

	/*
	
	final static String OUTPUT_DIR = "/home/julesy/output";

	final static String[] ALGORITHMS = new String[] { "Random", "NSGAII" };

	final static String[] PROBLEMS = new String[] { "TTP" };

	final static String[] INDICATORS = new String[] { "hv", "spread", "igd", "eps" };

	final static int NUM_OF_RUNS = 10;

	final static int MAX_EVALUATIONS = 100000;

	static SolutionSet[][][] results = new SolutionSet[PROBLEMS.length][ALGORITHMS.length][NUM_OF_RUNS];

	static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static void main(String[] args) throws JMException, IOException, ClassNotFoundException, jmetal.util.JMException {

		// createDirectories();
		System.out.println();
		Util.write(String.format("%s/experiment.json", OUTPUT_DIR), createExperimentJson());

		for (int i = 0; i < PROBLEMS.length; i++) {

			String problem = PROBLEMS[i];

			NonDominatedSolutionList referenceSet = new NonDominatedSolutionList();

			TravellingThiefProblem p = ProblemFactory.create(problem);
			for (int j = 0; j < ALGORITHMS.length; j++) {
				String algorithm = ALGORITHMS[j];

				Algorithm<List<jISolution>> a = AlgorithmFactory.create(algorithm, p, MAX_EVALUATIONS);

				for (int k = 0; k < NUM_OF_RUNS; k++) {

					a.run();
					List<jISolution> front = a.getResult();
					
					
					//String path = String.format("%s/%s_%s_%s.pf", OUTPUT_DIR, problem, algorithm, k);
					//Util.write(path, gson.toJson(front.writeObjectivesToMatrix()));
					//front.printVariablesToFile(path + "in");

					results[i][j][k] = front;

					// update the reference front
					Iterator<Solution> it = front.iterator();
					while (it.hasNext()) referenceSet.add(it.next());

				} // RUN

			} // ALGORITHM

			Util.write(String.format("%s/%s.pf", OUTPUT_DIR, problem), gson.toJson(referenceSet.writeObjectivesToMatrix()));

			// calculate all the indicators
			double[][] trueFront = referenceSet.writeObjectivesToMatrix();

			for (int y = 0; y < INDICATORS.length; y++) {
				for (int j = 0; j < ALGORITHMS.length; j++) {
					double[] indicator = new double[NUM_OF_RUNS];
					for (int k = 0; k < NUM_OF_RUNS; k++) {
						double[][] mFront = results[i][j][k].writeObjectivesToMatrix();
						indicator[k] = calcIndicator(INDICATORS[y], mFront, trueFront);
					}
					Util.write(String.format("%s/%s_%s.%s", OUTPUT_DIR, problem, ALGORITHMS[j], INDICATORS[y]), gson.toJson(indicator));
				}
			}

		} // PROBLEM

	}

	public static double calcIndicator(String type, double[][] mFront, double[][] trueFront) {
		if (type.equals("hv"))
			return new Hypervolume().hypervolume(mFront, trueFront, 2);
		else if (type.equals("spread"))
			return new Spread().spread(mFront, trueFront, 2);
		else if (type.equals("igd"))
			return new InvertedGenerationalDistance().invertedGenerationalDistance(mFront, trueFront, 2);
		else if (type.equals("eps"))
			return new Epsilon().epsilon(mFront, trueFront, 2);
		else
			throw new RuntimeException("Unknown indicator!");
	}

	
	public static void addNamesToJson(JsonObject obj, String propertry, String[] names) {
		JsonArray JNames = new JsonArray();
		obj.add(propertry, JNames);
		for (String n : names) {
			JNames.add(new JsonPrimitive(n));
		}
	}
	
	public static JsonObject createExperimentJson() {
		JsonObject jExperiment = new JsonObject();
		
		addNamesToJson(jExperiment, "algorithms", ALGORITHMS);
		addNamesToJson(jExperiment, "problems", PROBLEMS);
		addNamesToJson(jExperiment, "indicators", INDICATORS);
		
		for (String problem : PROBLEMS) {
			JsonObject jProblem = new JsonObject();
			jExperiment.add(problem, jProblem);
			
			JsonArray jFronts = new JsonArray();
			jProblem.add("fronts", jFronts);
			
			for (String algorithm : ALGORITHMS) {
				for (int k = 0; k < NUM_OF_RUNS; k++) {
					jFronts.add(new JsonPrimitive(String.format("%s/%s_%s_%s.pf", OUTPUT_DIR, problem, algorithm, k)));
				}
			}

			for (int y = 0; y < INDICATORS.length; y++) {
				JsonArray jIndicator = new JsonArray();
				jProblem.add(INDICATORS[y], jIndicator);
				for (int j = 0; j < ALGORITHMS.length; j++) {
					jIndicator.add(new JsonPrimitive(String.format("%s/%s_%s.%s", OUTPUT_DIR, problem, ALGORITHMS[j], INDICATORS[y])));
				}
			}
		}
		return jExperiment;
	}

	public static void createDirectories() {
		// create for each algorithm
		for (String algorithm : ALGORITHMS) {
			new File(String.format("%s/%s", OUTPUT_DIR, algorithm)).mkdirs();
			for (String problem : PROBLEMS) {
				new File(String.format("%s/%s/%s", OUTPUT_DIR, algorithm, problem)).mkdirs();
			}
		}
		new File(String.format("%s/%s", OUTPUT_DIR, "referenceFronts")).mkdirs();
	}
	
	*/

}
