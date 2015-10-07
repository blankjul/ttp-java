package com.msu.visualize.js;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.msu.io.AWriter;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.thief.variable.TTPVariable;

public class NonDominatedSetWriter extends AWriter<NonDominatedSolutionSet> {

	@Override
	protected void write_(NonDominatedSolutionSet set, OutputStream os) throws IOException {


		JsonGenerator json;
		json = new JsonFactory().createGenerator(os, JsonEncoding.UTF8).useDefaultPrettyPrinter();
		json.writeStartObject();
		
		
		Map<Solution, Integer> mID = new HashMap<>();
		int counter = 0;
		for(Solution s : set.getSolutions()) mID.put(s, counter++);
		
		
		Multimap<List<Integer>, Solution> multi = hashTours(set.getSolutions());
		json.writeFieldName("data");
		json.writeStartArray();
		for (List<Integer> tour : multi.keySet()) {
			SolutionSet setToAdd = new SolutionSet(multi.get(tour));
			json.writeStartObject();
			appendNonDominatedSet(json, setToAdd, mID);
			json.writeObjectField("name", Arrays.toString(tour.toArray()));
			json.writeEndObject();
			
		}
		json.writeEndArray();
		
		json.writeEndObject();
		json.close();
	}
	
	private Multimap<List<Integer>, Solution> hashTours(SolutionSet set) {
		Multimap<List<Integer>, Solution> hash = HashMultimap.create();
		for (Solution s : set) {
			TTPVariable var = (TTPVariable) s.getVariable();
			hash.put(var.get().first.encode(), s);
		}
		return hash;
	}
	
	private Multimap<List<Double>, Solution> hashObjectives(SolutionSet set) {
		Multimap<List<Double>, Solution> hash = HashMultimap.create();
		for (Solution s : set) {
			hash.put(s.getObjective(), s);
		}
		return hash;
	}
	

	public void appendNonDominatedSet(JsonGenerator json, SolutionSet set, Map<Solution, Integer> mID) throws IOException {
		

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
			json.writeNumber(mID.get(s));
		}
		json.writeEndArray();
		

	}


	

}
