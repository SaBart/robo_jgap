
package custom;


import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import static robocode.util.Utils.normalRelativeAngleDegrees;

import java.awt.*;



public class EP extends Robot {
	double dist = -6671.340896; 

	
	public void run() {
		
		setBodyColor(Color.orange);
		setGunColor(Color.orange);
		setRadarColor(Color.red);
		setScanColor(Color.red);
		setBulletColor(Color.red);

		
		while (true) {
			turnGunRight(9781.258667);
		}
	}

	
	public void onScannedRobot(ScannedRobotEvent e) {
		
		
		if (e.getDistance() < -6884.416624 && getEnergy() > 5880.783182) {
			fire(-1947.606320);
		} 
		else {
			fire(-4986.980024);
		}
		
		scan();
	}

	
	public void onHitByBullet(HitByBulletEvent e) {
		turnRight(normalRelativeAngleDegrees(-4704.638522 - (getHeading() - e.getHeading())));

		ahead(dist);
		dist *= 726.512536;
		scan();
	}

	
	public void onHitRobot(HitRobotEvent e) {
		double turnGunAmt = normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());

		turnGunRight(turnGunAmt);
		fire(2558.202680);
	}
}
