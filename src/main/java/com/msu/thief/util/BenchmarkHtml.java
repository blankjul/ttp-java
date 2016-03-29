package com.msu.thief.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;

import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominator;
import com.msu.moo.util.FileCollectorParser;
import com.msu.thief.io.thief.reader.JsonThiefProblemReader;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.util.visualization.VariableAsHtml;

public class BenchmarkHtml {

	public static void main(String[] args) throws IOException {

		BasicConfigurator.configure();
		Map<String, SingleObjectiveThiefProblem> mProblem = new LinkedHashMap<>();

		// create a map with all problem instances
		FileCollectorParser<AbstractThiefProblem> fcp = new FileCollectorParser<>();
		fcp.add("../new/", "single-*.json", new JsonThiefProblemReader());
		for (AbstractThiefProblem p : fcp.collect()) {
			System.out.println(p.getName());
			mProblem.put(p.getName(), (SingleObjectiveThiefProblem) p);
		}

		// get the best from the csv file
		Map<String, Solution<TTPVariable>> mVariable = readBestFromCSV("resources/best.csv", mProblem);

		// print the best as html

		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"responstable.css\">");
		sb.append("</head>");
		sb.append("<table style=\"width: 50%;\" class=\"responstable\">");
		sb.append("<th> Name </th>");
		sb.append("<th> G(pi,z) </th>");
		sb.append("<th> Constraints </th>");
		sb.append("<th> Link </th>");
		

		for (Entry<String, Solution<TTPVariable>> e : mVariable.entrySet()) {
			AbstractThiefProblem p = mProblem.get(e.getKey());
			Solution<TTPVariable> s = e.getValue();

			sb.append("<tr>");
			sb.append("<td> " + p.getName() + " </td>");
			sb.append("<td> " + s.getObjective(0) + " </td>");
			sb.append("<td> " + s.getSumOfConstraintViolation() + " </td>");
			sb.append(String.format("<td><a href=\"%s\">%s</a> </td>", String.format("%s.html", p.getName()), "here"));
			sb.append("</tr>");

			VariableAsHtml.write(p, e.getValue(), String.format("../html/%s.html", p.getName()));
		}

		sb.append("</table>");
		sb.append("</body>");
		sb.append("</html>");
		
		
		FileUtils.writeStringToFile(new File("../html/index.html"), sb.toString());
		System.out.println(sb.toString());

	}

	public static Map<String, Solution<TTPVariable>> readBestFromCSV(String csvFile, Map<String, SingleObjectiveThiefProblem> mProblem) {

		Map<String, Solution<TTPVariable>> mVariable = new LinkedHashMap<>();

		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",(?=([^\"]|\"[^\"]*\")*$)";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				if (!line.contains(","))
					continue;

				// use comma as separator
				String[] a = line.split(cvsSplitBy);

				final String name = a[0];
				final TTPVariable var = TTPVariable.createFromString(a[1].replace("\"", ""));
				final SingleObjectiveThiefProblem problem = mProblem.get(name);

				final Solution<TTPVariable> s = problem.evaluate(var);

				// if there is a solution in the map
				if (!mVariable.containsKey(name)) {
					mVariable.put(name, s);
				} else {
					if (SolutionDominator.isDominating(s, mVariable.get(name)))
						mVariable.put(name, s);
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return mVariable;

	}

}
