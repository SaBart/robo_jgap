
package custom;


import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import static robocode.util.Utils.normalRelativeAngleDegrees;

import java.awt.*;



public class AvgXFireEP extends Robot {
	double dist = 1272.880440; 

	
	public void run() {
		
		setBodyColor(Color.orange);
		setGunColor(Color.orange);
		setRadarColor(Color.red);
		setScanColor(Color.red);
		setBulletColor(Color.red);

		
		while (true) {
			turnGunRight(3377.906443);
		}
	}

	
	public void onScannedRobot(ScannedRobotEvent e) {
		
		
		if (e.getDistance() < -4214.932171 && getEnergy() > -1307.528431) {
			fire(-501.873424);
		} 
		else {
			fire(5135.311270);
		}
		
		scan();
	}

	
	public void onHitByBullet(HitByBulletEvent e) {
		turnRight(normalRelativeAngleDegrees(-7212.486865 - (getHeading() - e.getHeading())));

		ahead(dist);
		dist *= 3094.767497;
		scan();
	}

	
	public void onHitRobot(HitRobotEvent e) {
		double turnGunAmt = normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());

		turnGunRight(turnGunAmt);
		fire(3737.971648);
	}
}
