import robocode.control.events.*;

/* 
 * Robocode Battle Observer
 */
class Observer extends BattleAdaptor {
	int robotScore = 0;
	int enemyScore = 0;
	String name; // robot name

	public Observer(String name) {
		this.name = name;
	}

	// Called when the battle is completed successfully with battle results
	public void onBattleCompleted(BattleCompletedEvent e) {
		System.out.println("Battle completed");
		// Print out the sorted results with the robot names
		System.out.println("Battle results: ");
		for (robocode.BattleResults result : e.getSortedResults()) {
			if (result.getTeamLeaderName().equals(name)) {
				robotScore = result.getScore();
			}
			System.out.println(result.getTeamLeaderName() + "=" + result.getScore());
		}
	}

	// Called when the game sends out an information message during the battle
	public void onBattleMessage(BattleMessageEvent e) {
		System.out.println("Msg> " + e.getMessage());
	}

	// Called when the game sends out an error message during the battle
	public void onBattleError(BattleErrorEvent e) {
		System.err.println("Err> " + e.getError());
	}

	public int getScore() {
		return robotScore;
	}
}
