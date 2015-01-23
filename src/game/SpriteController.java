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
		makeEnemy(0, 100, 300, 0, 0);
		makeEnemy(0, 500, 300, 180, 1);
	}
	protected void makeEnemy(int type, int x, int y, int r, int t)
	{
		byte team = (byte)t;
		switch(team)
		{
		case 0:
			switch(type)
			{
			case 0:
				enemies.add(new Archer1(control, x, y, r, 3000, control.imageLibrary.archer, team)); //x, y, hp, sick, type is ImageIndex
				break;
			case 1:
				enemies.add(new Cleric1(control, x, y, r, 1700, control.imageLibrary.cleric, team));
				break;
			case 2:
				enemies.add(new Mage1(control, x, y, r, 700, control.imageLibrary.mage, team));
				break;
			case 3:
				enemies.add(new Swordsman1(control, x, y, r, 1700, control.imageLibrary.swordsman, team));
				break;
			}
			break;
		case 1:
			switch(type)
			{
			case 0:
				enemies.add(new Archer2(control, x, y, r, 3000, control.imageLibrary.archer, team)); //x, y, hp, sick, type is ImageIndex
				break;
			case 1:
				enemies.add(new Cleric2(control, x, y, r, 1700, control.imageLibrary.cleric, team));
				break;
			case 2:
				enemies.add(new Mage2(control, x, y, r, 700, control.imageLibrary.mage, team));
				break;
			case 3:
				enemies.add(new Swordsman2(control, x, y, r, 1700, control.imageLibrary.swordsman, team));
				break;
			}
			break;
		}
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
	protected void createShot(double rotation, double x, double y, byte Team)
	{
		shots.add(new Shot(control, x, y, rotation, Team));
	}
	protected void frameCall()
	{
		for(int i = 0; i < enemies.size(); i++) enemies.get(i).enemyFrame();
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