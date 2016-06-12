import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import java.awt.*;

public class Robot extends AdvancedRobot {
	public void run() {
		setBodyColor(Color.green);
		setGunColor(Color.green);
		setRadarColor(Color.green);
		setScanColor(Color.green);
		while (true) {
			setTurnRight(10000);
			setMaxVelocity(5);
			ahead(10000);
		}
	}
	public void onScannedRobot(ScannedRobotEvent e) {
		fire(3);
	}
	public void onHitRobot(HitRobotEvent e) {
		if (e.getBearing() > -10 && e.getBearing() < 10) {
			fire(3);
		}
		if (e.isMyFault()) {
			turnRight(10);
		}
	}
}
