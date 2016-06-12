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
	public static final int MAX_GENERATIONS = 100; // set amount of generations to evolve
	public static final int POPULATION_SIZE = 10; 	// set population size per generation
	public static final int ROUND_COUNT=1;
	public static final String NAME="custom.EP";
	public static final String SOURCE="robots/sample/Fire.java";
	public static final String RESULT="robots/custom/EP.java";
	public static final Boolean GUI=false;

	public void run() throws Exception {
		
		new Factory(SOURCE, RESULT);

		Configuration conf = new DefaultConfiguration(); // GA with default config
		conf.addGeneticOperator(new MutationOperator(conf, 10)); // add crossover operator
		conf.setPreservFittestIndividual(true); // elitism
		
		Fitness fitness = new Fitness(ROUND_COUNT,NAME,GUI);
		conf.setFitnessFunction(fitness); // fitness

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

		// evolve population
		for (int gen = 0; gen < MAX_GENERATIONS; gen++) {
			System.out.println("GEN "+gen);
			population.evolve(); // evolve population
			fittestSolution = population.getFittestChromosome(); // best chromosome
			System.out.printf("\nafter %d generations the best solution is %s \n", gen + 1, fittestSolution);
		}

		Factory.createProgram(fittestSolution); // pass best solution to build
		System.exit(0); // clean exit
	}

	public static void main(String[] args) throws Exception {
		new GA().run(); // run main
	}
}
