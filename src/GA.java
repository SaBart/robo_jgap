import java.io.BufferedWriter;
import java.io.FileWriter;

import org.jgap.*;
import org.jgap.impl.*;

import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;

/* 
 * Sam Ternent
 * RoboCodeGATemplate
 * Simple config for starting a project with RoboCode and JGAP
 */

@SuppressWarnings("serial")
public class GA {
	public static final int MAX_GENERATIONS =100; // set amount of generations to evolve
	public static final int POPULATION_SIZE = 20; 	// set population size per generation
	public static final int ROUND_COUNT=1; // number of rounds
	public static final String NAME="AvgXFireEP"; // robot name
	public static final String PACKAGE="custom"; // package name
	public static final String SOURCE="robots/sample/Fire.java"; // source code of evolved robot
	public static final String DIRECTORY="robots"; // where to save evolved robot
	public static final String LOG="robots/"+PACKAGE+"/" + NAME +"_fitness.txt"; // log fitness
	public static final Boolean GUI=false; // show gui
	public static final String[] ENEMIES=new String[]{"sample.RamFire","sample.Walls","sample.SpinBot", "sample.Fire"}; // list of enemies to compete against

	public void run() throws Exception {
		
		new Factory(SOURCE, DIRECTORY, PACKAGE, NAME);

		Configuration conf = new DefaultConfiguration(); // GA with default config
		conf.addGeneticOperator(new MutationOperator(conf, 10)); // add mutation with prob rate 1/10
		conf.addGeneticOperator(new AveragingCrossoverOperator(conf)); // add crossover with dynamic rate
		conf.addNaturalSelector(new TournamentSelector(conf, 2, 0.1), false); // add tournament selection, from 2 select the better one with prob=0.1, execute last
		conf.setPreservFittestIndividual(true); // elitism
		
		Fitness F = new Fitness(ROUND_COUNT,PACKAGE + "." +NAME+"*",GUI,ENEMIES);
		conf.setFitnessFunction(F); // fitness

		// set up sample genes - add multiple genes to the array
		Gene[] sampleGenes = new Gene[Factory.getGenes().length];

		for (int i=0;i<sampleGenes.length;i++) {
			sampleGenes[i] = new DoubleGene(conf,-10000,10000);
		}
		IChromosome sampleChromosome = new Chromosome(conf, sampleGenes); // create chromosome template
		conf.setSampleChromosome(sampleChromosome); // set chromosome template
	
		// set random population
		conf.setPopulationSize(POPULATION_SIZE); // create a population
		Genotype population = Genotype.randomInitialGenotype(conf);
		IChromosome fittestSolution = null;

		try {
			// logging fitness
			FileWriter fstream = new FileWriter(LOG);
			BufferedWriter out = new BufferedWriter(fstream);
			
			// evolve population
			for (int gen = 0; gen < MAX_GENERATIONS; gen++) {
				population.evolve(); // evolve population
				double fitness=0;
				for (IChromosome ch:population.getChromosomes())
				{
					fitness+=ch.getFitnessValue();
				
				}
				fittestSolution = population.getFittestChromosome(); // best chromosome
				System.out.println(gen +" "+ fittestSolution.getFitnessValue()+" "+ fitness/population.getChromosomes().length);
				out.append(gen +" "+ fittestSolution.getFitnessValue()+" "+ fitness/population.getChromosomes().length +"\n");
				//System.out.printf("\nafter %d generations the best solution is %s \n", gen + 1, fittestSolution);
			}
			out.close();
			Factory.createProgram(fittestSolution); // pass best solution to build
			System.exit(0); // clean exit
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	public static void main(String[] args) throws Exception {
		new GA().run(); // run main
	}
}
