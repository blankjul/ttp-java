package com.msu.thief.visualize.js;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.thief.variable.TTPVariable;

public class NonDominatedSetToJson {

	// ! string writer for getting the result
	protected StringWriter sw = null;

	// ! json generator object
	protected JsonGenerator json = null;;
	
	public NonDominatedSetToJson() throws IOException {
		sw = new StringWriter();
		json = new JsonFactory().createGenerator(sw).useDefaultPrettyPrinter();
		json.writeStartObject();
	}

	public NonDominatedSetToJson add(String name, SolutionSet set, boolean colored, Map<Solution, String> mToNode) throws IOException {
		
		json.writeFieldName(name);
		json.writeStartArray();

		if (colored) {
			Multimap<List<Integer>, Solution> multi = hashTours(set);
			for (List<Integer> tour : multi.keySet()) {
				SolutionSet setToAdd = new SolutionSet(multi.get(tour));
				json.writeStartObject();
				appendNonDominatedSet( setToAdd, mToNode);
				json.writeObjectField("name", Arrays.toString(tour.toArray()));
				json.writeEndObject();
			}
		} else {
			json.writeStartObject();
			appendNonDominatedSet(set, mToNode);
			json.writeObjectField("name", name);
			json.writeEndObject();
		}

		json.writeEndArray();
		
		
		return this;
	}

	public String getResult() throws IOException {
		json.close();
		return sw.toString();
	}

	private Multimap<List<Integer>, Solution> hashTours(SolutionSet set) {
		Multimap<List<Integer>, Solution> hash = HashMultimap.create();
		for (Solution s : set) {
			TTPVariable var = (TTPVariable) s.getVariable();
			hash.put(var.get().first.encode(), s);
		}
		return hash;
	}

	public void appendNonDominatedSet(SolutionSet set, Map<Solution, String> mID) throws IOException {

		json.writeArrayFieldStart("x");
		for (Solution s : set) {
			json.writeNumber(s.getObjectives(0));
		}
		json.writeEndArray();

		json.writeArrayFieldStart("y");
		for (Solution s : set) {
			json.writeNumber(s.getObjectives(1));
		}
		json.writeEndArray();

		json.writeObjectField("type", "scatter");
		json.writeObjectField("mode", "markers");
		json.writeObjectFieldStart("marker");
		json.writeObjectField("size", 16);
		json.writeEndObject();

		json.writeArrayFieldStart("id");
		for (Solution s : set) {
			json.writeObject(mID.get(s));
		}
		json.writeEndArray();

	}

}
