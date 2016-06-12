
package custom;


import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import static robocode.util.Utils.normalRelativeAngleDegrees;

import java.awt.*;



public class EP extends Robot {
	double dist = 8034.545207; 

	
	public void run() {
		
		setBodyColor(Color.orange);
		setGunColor(Color.orange);
		setRadarColor(Color.red);
		setScanColor(Color.red);
		setBulletColor(Color.red);

		
		while (true) {
			turnGunRight(-2401.566248);
		}
	}

	
	public void onScannedRobot(ScannedRobotEvent e) {
		
		
		if (e.getDistance() < -3101.260077 && getEnergy() > -6668.942822) {
			fire(9668.000205);
		} 
		else {
			fire(-1636.323447);
		}
		
		scan();
	}

	
	public void onHitByBullet(HitByBulletEvent e) {
		turnRight(normalRelativeAngleDegrees(-6735.195234 - (getHeading() - e.getHeading())));

		ahead(dist);
		dist *= -9641.669615;
		scan();
	}

	
	public void onHitRobot(HitRobotEvent e) {
		double turnGunAmt = normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());

		turnGunRight(turnGunAmt);
		fire(9547.378048);
	}
}
