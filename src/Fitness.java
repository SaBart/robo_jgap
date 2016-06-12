import org.jgap.*;
import org.jgap.impl.*;

import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;

public class Fitness extends FitnessFunction {

	int round_count;
	String name;
	Boolean gui;
	
	public Fitness(int round_count, String name, Boolean gui)
	{
		this.round_count=round_count;
		this.name=name;
		this.gui=gui;
	}
	
	@Override
	protected double evaluate(IChromosome chromosome) {
		Factory.createProgram(chromosome); // build robot
		RobocodeEngine engine = new RobocodeEngine(new java.io.File("")); // create robocode engine
		Observer observer=new Observer(name);
		engine.addBattleListener(observer); // add battle listener to engine
		engine.setVisible(gui); // show GUI 
		BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600); // battlefield size
		RobotSpecification[] selectedRobots = engine.getLocalRepository("sample.Fire,"+name+"*"); // robots in battle
		BattleSpecification battleSpec = new BattleSpecification(round_count, battlefield, selectedRobots);
		engine.runBattle(battleSpec, true); // run battle
		engine.close(); // clean up engine
		int fitness = observer.getScore(); // set fitness score
		return fitness > 0 ? fitness : 0; // return fitness score if it's over 0
	}
}
