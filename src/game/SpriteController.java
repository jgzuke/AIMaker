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
	private View control;
	public SpriteController(View Control)
	{
		makeEnemy(0, 100, 300, 0, 0);
		makeEnemy(0, 500, 300, 180, 1);
		control = Control;
	}
	protected void makeEnemy(int type, int x, int y, int r, int t)
	{
		Packet p = new Packet(x, y, r, (byte)t, (byte)type, control.controlAccess);
		switch(t)
		{
		case 0:
			switch(type)
			{
			case 0:
				enemies.add(new Archer1(p)); //x, y, hp, sick, type is ImageIndex
				break;
			case 1:
				enemies.add(new Cleric1(p));
				break;
			case 2:
				enemies.add(new Mage1(p));
				break;
			case 3:
				enemies.add(new Swordsman1(p));
				break;
			}
			break;
		case 1:
			switch(type)
			{
			case 0:
				enemies.add(new Archer2(p)); //x, y, hp, sick, type is ImageIndex
				break;
			case 1:
				enemies.add(new Cleric2(p));
				break;
			case 2:
				enemies.add(new Mage2(p));
				break;
			case 3:
				enemies.add(new Swordsman2(p));
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
		shots.add(new Shot(x, y, rotation, Team, control));
	}
	protected void frameCall()
	{
		for(int i = 0; i < enemies.size(); i++)
		{
			if(enemies.get(i).getDeleted()) enemies.remove(i);
			else enemies.get(i).enemyFrame();
		}
		for(int i = 0; i < aoes.size(); i++)
		{
			if(aoes.get(i).getDeleted()) aoes.remove(i);
			else aoes.get(i).frameCall();
		}
		for(int i = 0; i < shots.size(); i++)
		{
			if(shots.get(i).getDeleted()) shots.remove(i);
			else shots.get(i).frameCall();
		}
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
		aoes.add(new AOE(x, y, Team, enemies));
	}
	protected void createSafeAOE(double x, double y, byte Team)
	{
		aoes.add(new AOE(x, y, Team));
	}
}