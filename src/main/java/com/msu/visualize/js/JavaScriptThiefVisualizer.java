package com.msu.visualize.js;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.problems.ThiefProblem;
import com.msu.util.Pair;
import com.msu.util.events.IListener;
import com.msu.util.events.impl.EventDispatcher;
import com.msu.util.events.impl.ProblemFinishedEvent;
import com.msu.util.io.AWriter;

public class JavaScriptThiefVisualizer extends AWriter<Pair<ProblemFinishedEvent, Integer>>implements IListener<ProblemFinishedEvent> {

	protected String folder = null;

	protected boolean trueFront = true;

	public JavaScriptThiefVisualizer(String folder) {
		EventDispatcher.getInstance().register(ProblemFinishedEvent.class, this);
		this.folder = folder;
	}

	@Override
	protected void write_(Pair<ProblemFinishedEvent, Integer> pair, OutputStream os) throws IOException {

		final AExperiment experiment = pair.first.getExperiment();
		final IProblem problem = pair.first.getProblem();
		final int run = pair.second;

		StringWriter algorithmsToJson = new StringWriter();
		JsonGenerator json = new JsonFactory().createGenerator(algorithmsToJson).useDefaultPrettyPrinter();
		json.writeStartArray();
		for (IAlgorithm algorithm : experiment.getAlgorithms())
			json.writeString(algorithm.toString());
		if (trueFront)
			json.writeString("True Front");

		json.writeEndArray();
		json.close();

		TTPVariableToJson varToJson = new TTPVariableToJson((ThiefProblem) problem);

		// hash all solutions of this problem instance to create a unique
		// id!
		Map<Solution, String> mID = new HashMap<>();
		int counter = 0;
		for (IAlgorithm algorithm : experiment.getAlgorithms()) {
			NonDominatedSolutionSet set = experiment.getResult().get(problem, algorithm, run);
			for (Solution s : set.getSolutions()) {
				String name = String.format("n%s", counter++);
				mID.put(s, name);
				varToJson.add(s, name);
			}
		}

		NonDominatedSetToJson setToJson = new NonDominatedSetToJson();

		// add the true front
		if (trueFront) {

			for (Solution s : pair.first.getTrueFront().getSolutions()) {
				String name = String.format("n%s", counter++);
				mID.put(s, name);
				varToJson.add(s, name);
			}

			setToJson.add(String.format("%s", "True Front"), pair.first.getTrueFront().getSolutions(), false, mID);
			setToJson.add(String.format("%s_color", "True Front"), pair.first.getTrueFront().getSolutions(), true, mID);
		}

		// add for all the results the colored and the non colored version
		// to
		// the scatter plot

		for (IAlgorithm algorithm : experiment.getAlgorithms()) {
			NonDominatedSolutionSet set = experiment.getResult().get(problem, algorithm, run);
			setToJson.add(String.format("%s", algorithm.toString()), set.getSolutions(), false, mID);
			setToJson.add(String.format("%s_color", algorithm.toString()), set.getSolutions(), true, mID);
		}

		HashMap<String, Object> scopes = new HashMap<String, Object>();

		scopes.put("dAlgorithms", algorithmsToJson.toString());
		scopes.put("dScatter", setToJson.getResult());
		scopes.put("dThief", varToJson.getResult());

		Writer writer = new OutputStreamWriter(os);
		MustacheFactory mf = new DefaultMustacheFactory();
		Mustache mustache = mf.compile("resources/visualize.html");
		mustache.execute(writer, scopes);
		writer.flush();

	}

	@Override
	public void handle(ProblemFinishedEvent event) {

		NonDominatedSolutionSet trueFront = new NonDominatedSolutionSet();
		for (IAlgorithm algorithm : event.getExperiment().getAlgorithms()) {
			for (NonDominatedSolutionSet set : event.getExperiment().getResult().get(event.getProblem(), algorithm)) {
				trueFront.addAll(set.getSolutions());
			}
		}

		for (int i = 0; i < event.getNumOfRuns(); i++) {
			write(Pair.create(event, i), String.format("%s/%s_%s.html", folder, event.getProblem(), i));
		}
	}

}
