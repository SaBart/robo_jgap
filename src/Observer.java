import robocode.control.events.*;

/* 
 * Robocode Battle Observer
 */
class Observer extends BattleAdaptor {
	int score = 0;
	String name; // robot name

	public Observer(String name) {
		this.name = name;
	}

	// battle completed successfully
	public void onBattleCompleted(BattleCompletedEvent e) {
		score=0;
		for (robocode.BattleResults result : e.getSortedResults()) {
			if (result.getTeamLeaderName().equals(name)) {
				score = result.getScore();
//				System.out.println(result.getScore());
			}
		}
	}

	// information message during the battle
	public void onBattleMessage(BattleMessageEvent e) {
		//System.out.println(e.getMessage());
	}

	//  error message during the battle
	public void onBattleError(BattleErrorEvent e) {
		System.err.println(e.getError());
	}

	public int getScore() {
		return score;
	}
}
