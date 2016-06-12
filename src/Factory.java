import javax.tools.*;

import org.jgap.Gene;
import org.jgap.IChromosome;


import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;

import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;


public class Factory {

	private static String file;
	private static String template;
	private static Double[] defaultGenes;

	public Factory(String source, String dest) {
		Factory.file=dest;
		try {
			FileReader fstream = new FileReader(source);
			BufferedReader in = new BufferedReader(fstream);
			
			StringBuilder sb = new StringBuilder();
		    String line;
		    while( (line = in.readLine()) != null) {
		       sb.append(line+"\n");
		    }

		    String template=sb.toString().replaceAll("//.*|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/", ""); // remove comments
		    template=template.replaceAll("package ([^;]+)", "package custom"); // change package name
		    template=template.replaceAll("class ([^ ]+)", "class EP"); // change class name
		    template=template.replaceAll("int", "double"); // int to double
		    
		    List<Double> ds=new ArrayList<>();
		    Pattern p = Pattern.compile("(?>-?\\d+(?:[\\./]\\d+)?)");
		    Matcher m = p.matcher(template.toString());
		    while (m.find()) {
		    	ds.add(Double.parseDouble(m.group()));
		    }
		    defaultGenes= ds.toArray(new Double[ds.size()]);
		    
		    Factory.template= template.replaceAll("(?>-?\\d+(?:[\\./]\\d+)?)","%f");
		    
			in.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public static Double[] getGenes()
	{
		return defaultGenes;
	}
	
	public static String getTemplate()
	{
		return template;
	}

	public static void createProgram(IChromosome chromosome) {
		createCode(chromosome); // create file
		compile(); // compile file
	}

	private static void compile() {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		compiler.run(null, null, null, file); // compile
	}

	private static void createCode(IChromosome chromosome) {
		try {

			FileWriter fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);

			Double[] genes = new Double[chromosome.getGenes().length];

			for (int i = 0; i < genes.length; i++) {
				genes[i] = (Double) chromosome.getGene(i).getAllele();
			}

			out.append(String.format(template, genes));
			out.close();

		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
}