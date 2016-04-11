package physics;

import java.math.BigDecimal;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * PhysicsMathmatics
 * 
 * @author Ivan Frasure
 * 
 */
public class PhysicsMath {

	/**
	 * 3.0v Calls a method to calculate things - just plots points for data set
	 * to return to graphics method.
	 */
	static XYDataset velocityTimeDataSet(double acel, double pos, double vel,
			int pts, double dragCo, double objR, double objM) {
		final XYSeries trial = new XYSeries("Velocity vs. Time");

		double[] array = dragVwithFallingA(pos, vel, acel, pts, dragCo, objR, objM);

		for (int i = 0; i < array.length; i++) {
			trial.add(array[i + 1], array[i]);
			i += 1;
		}

		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(trial);
		return dataset;
	}

	static double[] arrayXYGraph(double t, double iAy, double iPy, double velX,
			double velY, int pts, double dC, double radius, double mass) {

		double deltaT = t / pts;
		double air = airViscosity();
		double[] axis = new double[(pts * 2) + 2];

		System.out.println("System has calculated: " + air
				+ " kinematic viscosity.");
		System.out.println("System has chosen: " + t + " seconds");
		System.out.println("DeltaT: " + deltaT + "(s)");

		double accely = iAy;
		double accelx = 0;
		double drag = 0;

		for (int i = 0; i < (pts * 2) + 2; i++) {

			drag = calculateDrag(dC, radius, velX);// drag
			accelx = round((((mass * accelx) - drag) / mass), 2);// calculate acceleration
			axis[i] = round(calcxyV(deltaT, velX, accelx), 3); // by dividing i

			velX = axis[i];

			System.out.println();

			drag = calculateDrag(dC, radius, velY);// drag
			accely = round((((mass * accely) - drag) / mass), 2);// calculate acceleration

			axis[i + 1] = round(calcxyV(deltaT, velY, accely), 3); 

			velY = axis[i + 1];
			System.out.printf("\nydrag: %f", drag);
			System.out.printf("\nyprevious velocity: %f", velY);
			System.out.printf("\nyAcceleration: %f", accely);
			System.out.println();
			i += 1;
		}
		return axis;
	}

	static double calcxyV(double deltaT, double vel, double accel) {
		return deltaT * accel + vel;
	}

	// xyVersion t, acel, pos, velX, velY, pts, dC, objR, objM
	static XYDataset velocityXYT(double t, double iAy, double iPy, double velX,
			double velY, int pts, double dC, double radius, double mass) {
		final XYSeries trial1 = new XYSeries("Velocity (Y)");

		double[] array1 = dragVwithFallingA(t, iPy, velX, 0, pts, dC, radius,
				mass);

		double[] array2 = dragVwithFallingA(t, iPy, velY, iAy, pts, dC, radius,
				mass);
		for (int i = 0; i < array2.length; i++) {
			trial1.add(array1[i], array2[i]);
			i += 1;
		}

		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(trial1);
		// dataset.addSeries( trial2 );
		return dataset;
	}

	// xyVersion t, acel, pos, velX, velY, pts, dC, objR, objM
	static XYDataset velocityXYDataSet(double t, double iAy, double iPy,
			double velX, double velY, int pts, double dC, double radius,
			double mass) {
		final XYSeries trial1 = new XYSeries("Velocity (X)");
		final XYSeries trial2 = new XYSeries("Velocity (Y)");

		double[] array1 = dragVwithFallingA(t, iPy, velX, 0, pts, dC, radius,
				mass);

		for (int i = 0; i < array1.length; i++) {
			trial1.add(array1[i + 1], array1[i]);
			i += 1;
		}

		double[] array2 = dragVwithFallingA(t, iPy, velY, iAy, pts, dC, radius,
				mass);
		for (int i = 0; i < array2.length; i++) {
			trial2.add(array2[i + 1], array2[i]);
			i += 1;
		}

		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(trial1);
		dataset.addSeries(trial2);
		return dataset;
	}

	static double[] dragVwithFallingA(double iP, double iV, double iA, int pts,
			double dC, double radius, double mass) {
		double t = timeAtGround(iP, iV, iA);
		double deltaT = t / pts;
		double dragCo = ((double) dC) / 10;
		double air = airViscosity();
		double[] axis = new double[(pts * 2) + 2];
		double preV = iV;

		System.out.println("System has calculated: " + air
				+ " kinematic viscosity.");
		System.out.println("System has chosen: " + t + " seconds");
		System.out.println("DeltaT: " + deltaT + "(s)");

		for (int i = 0; i < (pts * 2) + 2; i++) {

			double drag = calculateDrag(dragCo, radius, preV);// drag

			System.out.printf("\nDrag: %f", drag);// display drag value

			double accel = ((mass * iA) - drag) / mass;// calculate acceleration

			axis[i] = calcVel(deltaT, (i / 2), preV, accel); // by dividing i by

			preV = axis[i];

			System.out.printf("\nprevious velocity: %f", preV);

			System.out.printf("\nAcceleration: %f", accel);

			axis[i + 1] = deltaT * ((i) / 2);

			i += 1;
		}

		return axis;
	}

	/**
	 * Units: m/s<br>
	 * 3.0v<br>
	 * Input: deltaT(s), pt(int), iV(m/s), (m/s^2)<br>
	 * 
	 * @param deltaT
	 * @param pt
	 * @param iV
	 * @param a
	 * @return
	 */
	static double calcVel(double deltaT, double pt, double iV, double a) {
		return (deltaT * pt * a) + iV;
	}

	/**
	 * Units: m^2/s<br>
	 * 3.0v<br>
	 * Input: Volume(m^3), Mass (kg)<br>
	 * Sutherland's Equation: calculates the dynamic viscosity of gases (in this
	 * case average air at 25 degrees C, 298 Kelvin<br>
	 * Since (for now) we will always be calculating the viscosity of air <br>
	 * The constants b and s will be hard coded for the calculation with air;<br>
	 * b = 1.458 x 10^-6 s = S = 110.4K
	 * 
	 * Using the dynamic viscosity we can determine the absolute viscosity by
	 * dividing by 'p' , rho, (mass / volume).
	 * 
	 * @return
	 */
	static double airViscosity() {
		double dynamicVis = ((0.000001458) * (Math.sqrt(Math.pow(298, 3))))
				/ (298 + 110.4);
		double kinematicVis = dynamicVis / (1.18);// density/p/rho of air at 25C
		// sea level
		return kinematicVis;
	}

	/**
	 * Unit: <br>
	 * 3.0v<br>
	 * Input: dragCoeff(n/a), radius(m), velocity(m/s)<br>
	 * 
	 * Calculate the drag of the air on an object given radius, velocity, and
	 * drag coefficient (density of air is hard coded at 25C sea level).
	 * 
	 * @param dragCoeff
	 * @param radius
	 * @param velocity
	 * @return
	 */
	static double calculateDrag(double dC, double radius, double velocity) {
		double crossArea = Math.pow(radius, 2) * Math.PI;
		return dC * ((crossArea * velocity * Math.abs(velocity)) / 2) * 1.18;// 1.18
	}

	/**
	 * Unit: s<br>
	 * 3.0v<br>
	 * Input: Position(m), Velocity(m/s), Acceleration(m/s^2)<br>
	 * 
	 * @param iP
	 * @param iV
	 * @param a
	 * @return
	 */
	static double timeAtGround(double iP, double iV, double a) {
		double plusTime = 0;
		double minusTime = 0;
		if (a == 0) {
			System.out.println("Acceleration was zero.");
			return (-(iP) / iV);
		}

		plusTime = (-(iV) + Math.sqrt(Math.pow(iV, 2) - 4 * (0.5 * a) * iP))
				/ (a);
		minusTime = (-(iV) - Math.sqrt(Math.pow(iV, 2) - 4 * (0.5 * a) * iP))
				/ (2 * (0.5 * a));
		System.out.printf("\nThe positive root of Position = 0 : %f s",
				plusTime);
		System.out.printf("\nThe negative root of Position = 0 : %f s",
				minusTime);

		if (minusTime == Double.NaN || minusTime < 0) {
			return plusTime;
		}
		if (plusTime == Double.NaN || plusTime < 0) {
			return minusTime;
		}
		return Math.max(plusTime, minusTime);
	}

	public static XYDataset velocityTimeDataSet(double t, double acel,
			double pos, double vel, int pts, double dC, double objR, double objM) {

		final XYSeries trial = new XYSeries("Velocity vs. Time");

		double[] array = dragVwithFallingA(t, pos, vel, acel, pts, dC, objR,
				objM);

		for (int i = 0; i < array.length; i++) {
			trial.add(array[i + 1], array[i]);
			i += 1;
		}

		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(trial);
		return dataset;
	}

	private static double[] dragVwithFallingA(double t, double pos, double vel,
			double acel, int pts, double dC, double objR, double objM) {
		double tim = t;
		double deltaT = t / pts;
		double air = airViscosity();
		double[] axis = new double[(pts * 2) + 2];

		System.out.println("System has calculated: " + air
				+ " kinematic viscosity.");
		System.out.println("System has chosen: " + t + " seconds");
		System.out.println("DeltaT: " + deltaT + "(s)");

		for (int i = 0; i < (pts * 2) + 2; i++) {

			double drag = calculateDrag(dC, objR, vel);// drag

			System.out.printf("\nDrag: %f", drag);// display drag value

			double accel = round((((objM * acel) - drag) / objM), 2);

			axis[i] = round(calcxyV(deltaT, vel, accel), 3); 

			vel = axis[i];

			System.out.printf("\nprevious velocity: %f", vel);

			System.out.printf("\nAcceleration: %f", accel);

			axis[i + 1] = deltaT * ((i) / 2);

			t = t - deltaT;
			System.out.printf("\nTime Used: %f", (tim - t));

			i += 1;
		}
		return axis;
	}

	public static double round(double num, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Double.toString(num));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}
}
