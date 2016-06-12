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
	public static final int POPULATION_SIZE = 5; 	// set population size per generation
	public static final int ROUND_COUNT=1; // number of rounds
	public static final String NAME="custom.EP*"; // package.name, star at the end required
	public static final String SOURCE="robots/sample/Fire.java"; // source code of evolved robot
	public static final String RESULT="robots/custom/EP.java"; // where to save evolved robot
	public static final Boolean GUI=false; // show gui
	public static final String[] ENEMIES=new String[]{"sample.RamFire","sample.Walls","sample.SpinBot", "sample.Tracker"}; // list of enemies to compete against

	public void run() throws Exception {
		
		new Factory(SOURCE, RESULT);

		Configuration conf = new DefaultConfiguration(); // GA with default config
		conf.addGeneticOperator(new MutationOperator(conf)); // add mutation with dynamic rate
		conf.addGeneticOperator(new CrossoverOperator(conf)); // add crossover with dynamic rate
		conf.addNaturalSelector(new TournamentSelector(conf, 2, 0.1), false); // add tournament selection, from 2 select the better one with prob=0.1, execute last
		conf.setPreservFittestIndividual(true); // elitism
		
		Fitness F = new Fitness(ROUND_COUNT,NAME,GUI,ENEMIES);
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

		// evolve population
		for (int gen = 0; gen < MAX_GENERATIONS; gen++) {
			population.evolve(); // evolve population
			double fitness=0;
			for (IChromosome ch:population.getChromosomes())
			{
				fitness+=ch.getFitnessValue();
			
			}
			fittestSolution = population.getFittestChromosome(); // best chromosome
			System.out.println(fittestSolution.getFitnessValue()+" "+ fitness/population.getChromosomes().length);
			//System.out.printf("\nafter %d generations the best solution is %s \n", gen + 1, fittestSolution);
		}

		Factory.createProgram(fittestSolution); // pass best solution to build
		System.exit(0); // clean exit
	}

	public static void main(String[] args) throws Exception {
		new GA().run(); // run main
	}
}
