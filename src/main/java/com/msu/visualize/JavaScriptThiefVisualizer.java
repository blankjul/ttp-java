package com.msu.visualize;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.msu.io.AWriter;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.Pair;
import com.msu.thief.ThiefProblem;
import com.msu.visualize.js.NonDominatedSetWriter;
import com.msu.visualize.js.TTPVariableWriter;

public class JavaScriptThiefVisualizer extends AWriter<Pair<ThiefProblem, SolutionSet>> {



	@Override
	protected void write_(Pair<ThiefProblem, SolutionSet> pair, OutputStream os) throws IOException {
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
	






}
