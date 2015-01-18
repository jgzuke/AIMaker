package game;

import java.util.ArrayList;

public final class SpriteController
{
	/**
	 * Initializes all undecided variables, loads level, creates player and enemy objects, and starts frameCaller
	 */
	ArrayList<Enemy> enemies;
	ArrayList<AOE> AOEs;
	ArrayList<Shot> shots;
	View control;
	public SpriteController(View Control)
	{
		control = Control;
	}
	/**
	 * creates a player power ball
	 * @param rotation rotation of bolt
	 * @param xVel horizontal velocity of bolt
	 * @param yVel vertical velocity of bolt
	 * @param power power of bolt
	 * @param x x position
	 * @param y y position
	 */
	protected void createShot(double rotation, double Vel, int power, double x, double y, byte Team)
	{
		shots.add(new Shot(control, x, y, rotation, Team));
	}
	/**
	 * creates an emeny AOE explosion
	 * @param x x position
	 * @param y y position
	 * @param power power of explosion
	 * @param damaging whether it damages player
	 */
	protected void createAOE(double x, double y, byte Team)
	{
		AOEs.add(new AOE(control, x, y, Team));
	}
	protected void createSafeAOE(double x, double y, byte Team)
	{
		AOEs.add(new AOE(control, x, y, Team));
	}
}