package game;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public final class SpriteController
{
	/**
	 * Initializes all undecided variables, loads level, creates player and enemy objects, and starts frameCaller
	 */
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	ArrayList<AOE> aoes = new ArrayList<AOE>();
	ArrayList<Shot> shots = new ArrayList<Shot>();
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
	protected void drawSprites(Graphics g)
	{
		for(int i = 0; i < enemies.size(); i++)
		{
			//drawRotated(enemies.get(i), g, null);
		}
		for(int i = 0; i < aoes.size(); i++)
		{
			//drawRotated(AOEs.get(i), g, null);
		}
		for(int i = 0; i < shots.size(); i++)
		{
			//drawRotated(shots.get(i), g, null);
		}
	}
	private void drawRotated(Sprite s, Graphics g, ImageObserver i)
	{
		double rotationRequired = Math.toRadians(45);
		double locationX = s.image.getWidth() / 2;
		double locationY = s.image.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		g.drawImage(op.filter(s.image, null), (int)s.x, (int)s.y, i);
	}
	protected void frameCall()
	{
		for(int i = 0; i < enemies.size(); i++) enemies.get(i).frameCall();
		for(int i = 0; i < aoes.size(); i++) aoes.get(i).frameCall();
		for(int i = 0; i < shots.size(); i++) shots.get(i).frameCall();
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
		aoes.add(new AOE(control, x, y, Team));
	}
	protected void createSafeAOE(double x, double y, byte Team)
	{
		aoes.add(new AOE(control, x, y, Team));
	}
}