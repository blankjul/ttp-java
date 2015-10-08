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
import com.msu.io.AWriter;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Pair;
import com.msu.moo.util.events.EventDispatcher;
import com.msu.moo.util.events.IListener;
import com.msu.moo.util.events.ProblemFinishedEvent;
import com.msu.thief.ThiefProblem;

public class JavaScriptThiefVisualizer extends AWriter<Pair<AExperiment, IProblem>>implements IListener<ProblemFinishedEvent> {

	protected String folder = null;

	public JavaScriptThiefVisualizer(String folder) {
		EventDispatcher.getInstance().register(ProblemFinishedEvent.class, this);
		this.folder = folder;
	}

	@Override
	protected void write_(Pair<AExperiment, IProblem> pair, OutputStream os) throws IOException {

		AExperiment experiment = pair.first;
		IProblem problem = pair.second;

		StringWriter algorithmsToJson = new StringWriter();
		JsonGenerator json = new JsonFactory().createGenerator(algorithmsToJson).useDefaultPrettyPrinter();
		json.writeStartArray();
		for (IAlgorithm algorithm : experiment.getAlgorithms())
			json.writeString(algorithm.toString());
		json.writeEndArray();
		json.close();

		TTPVariableToJson varToJson = new TTPVariableToJson((ThiefProblem) problem);

		// hash all solutions of this problem instance to create a unique id!
		Map<Solution, String> mID = new HashMap<>();
		int counter = 0;
		for (IAlgorithm algorithm : experiment.getAlgorithms()) {
			for (NonDominatedSolutionSet set : experiment.getResult().get(problem, algorithm)) {
				for (Solution s : set.getSolutions()) {
					String name = String.format("n%s", counter);
					mID.put(s, name);
					varToJson.add(s, name);
					++counter;
				}
			}
		}

		// add for all the results the colored and the non colored version to
		// the scatter plot
		NonDominatedSetToJson setToJson = new NonDominatedSetToJson();
		for (IAlgorithm algorithm : experiment.getAlgorithms()) {
			for (NonDominatedSolutionSet set : experiment.getResult().get(problem, algorithm)) {
				setToJson.add(String.format("%s", algorithm.toString()), set.getSolutions(), false, mID);
				setToJson.add(String.format("%s_color", algorithm.toString()), set.getSolutions(), true, mID);
			}
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
		write(Pair.create(event.getExperiment(), event.getProblem()), String.format("%s/%s.html", folder, event.getProblem()));
	}

}
