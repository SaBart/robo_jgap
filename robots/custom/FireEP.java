
package custom;


import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import static robocode.util.Utils.normalRelativeAngleDegrees;

import java.awt.*;



public class FireEP extends Robot {
	double dist = -647.701530; 

	
	public void run() {
		
		setBodyColor(Color.orange);
		setGunColor(Color.orange);
		setRadarColor(Color.red);
		setScanColor(Color.red);
		setBulletColor(Color.red);

		
		while (true) {
			turnGunRight(-8714.345786);
		}
	}

	
	public void onScannedRobot(ScannedRobotEvent e) {
		
		
		if (e.getDistance() < -2239.862303 && getEnergy() > -168.631098) {
			fire(-5673.642643);
		} 
		else {
			fire(1574.377820);
		}
		
		scan();
	}

	
	public void onHitByBullet(HitByBulletEvent e) {
		turnRight(normalRelativeAngleDegrees(-8453.321545 - (getHeading() - e.getHeading())));

		ahead(dist);
		dist *= -8297.950216;
		scan();
	}

	
	public void onHitRobot(HitRobotEvent e) {
		double turnGunAmt = normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());

		turnGunRight(turnGunAmt);
		fire(6641.150826);
	}
}
