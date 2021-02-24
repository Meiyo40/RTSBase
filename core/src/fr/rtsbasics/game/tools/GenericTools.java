package fr.rtsbasics.game.tools;

import java.security.Timestamp;

public class GenericTools {

	public static float RandomNb(int min, int max)
	{
		int range = max - min + 1;
		return (float)(Math.random() * range) + min; 
	}

	/**
	 * @return the distance between 2 points
	 */
	public static float getDistance(float x1, float y1, float x2, float y2)
	{
		float dx = x1 - x2;
		float dy = y1 - y2;
		return (float) Math.sqrt(dx*dx + dy*dy);
	}

	public static float[] CalculateLinePoints (float x1, float y1, float x2, float y2, float dist) {
		float x;
		float y;
		float T = dist /getDistance(x1,y1,x2,y2);

		// finding point C coordinate
		x = (1 - T) * x1 + T * x2;
		y = (1 - T) * y1 + T * y2;

		return new float[]{x, y};
	}
}
