package com.msu.visualize;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;

import org.apache.commons.lang3.StringEscapeUtils;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.msu.algorithms.ExhaustiveThief;
import com.msu.io.AWriter;
import com.msu.io.reader.JsonThiefReader;
import com.msu.io.writer.JsonThiefProblemWriter;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.Pair;
import com.msu.thief.ThiefProblem;
import com.msu.visualize.js.NonDominatedSetWriter;
import com.msu.visualize.js.TTPVariableWriter;

public class JavaScriptThiefVisualizer extends AWriter<Pair<ThiefProblem, NonDominatedSolutionSet>> {



	@Override
	protected void write_(Pair<ThiefProblem, NonDominatedSolutionSet> pair, OutputStream os) throws IOException {
		HashMap<String, Object> scopes = new HashMap<String, Object>();
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		new NonDominatedSetWriter().write(pair.second, stream);
	    scopes.put("dScatter", stream.toString());
	    
	    stream = new ByteArrayOutputStream();
		new TTPVariableWriter().write(pair, stream);
	    scopes.put("dThief", stream.toString());
	    
	    
	    Writer writer = new OutputStreamWriter(os);
	    MustacheFactory mf = new DefaultMustacheFactory();
	    Mustache mustache = mf.compile("resources/visualize.html");
	    mustache.execute(writer, scopes);
	    writer.flush();
		
	}
	
	


	public static void main(String[] args) throws IOException {
		ThiefProblem problem = new JsonThiefReader().read("../ttp-benchmark/my_publication_coordinates.ttp");
		NonDominatedSolutionSet set = new ExhaustiveThief().run(problem);
		new JavaScriptThiefVisualizer().write(Pair.create(problem, set), "/home/julesy/visualize.html");
		
	}








}
