package game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Enemy extends Sprite
{
	protected int hp;
	protected int hpMax;
	protected double speed;
	protected View control;
	protected int inDanger = 0;
	protected double[] closestDanger = new double[2];
	protected int radius = 20;
	protected double xMove = 0;
	protected double yMove = 0;
	protected double speedCur = 0;
	protected int runTimer;
	protected String action = "Nothing"; //"Nothing", "Move", "Alert", "Shoot", "Melee", "Roll", "Hide", "Sheild", "Stun"

	public Enemy(View creator, double X, double Y, double R, int HP, BufferedImage [] Images, byte Team)
	{
		super(X, Y, R, Images, Team);
		control = creator;
	}
	protected void enemyFrame()
	{
		image = images[frame];
		pushOtherPeople();
		if(action.equals("Run"))
		{
			baseRunning();
			running();
		} else
		{
			frameCall();
		}
		if(hp > hpMax) hp = hpMax;
	}
	/**
	 * checks who else this guy is getting in the way of and pushes em
	 */
	private void pushOtherPeople()
	{
		double movementX;
		double movementY;
		double moveRads;
		double xdif;
		double ydif;
		ArrayList<Enemy> enemies = control.spriteController.enemies;
		for(int i = 0; i < enemies.size(); i++)
		{
			if(enemies.get(i) != null&& enemies.get(i).x != x)
			{
				xdif = x - enemies.get(i).x;
				ydif = y - enemies.get(i).y;
				if(Math.pow(xdif, 2) + Math.pow(ydif, 2) < Math.pow(radius, 2))
				{
					moveRads = Math.atan2(ydif, xdif);
					movementX = (x - (Math.cos(moveRads) * radius) - enemies.get(i).x)/2;
					movementY = (y - (Math.sin(moveRads) * radius) - enemies.get(i).y)/2;
					enemies.get(i).x += movementX;
					enemies.get(i).y += movementY;
					x -= movementX;
					y -= movementY;
				}
			}
		}
	}
	/**
	 * Takes a sent amount of damage, modifies based on shields etc.
	 * if health below 0 kills enemy
	 * @param damage amount of damage to take
	 */
	protected void getHit(double damage)
	{
		if(!deleted)
		{
			if(action.equals("Sheild")) damage /= 9;
			if(action.equals("Hide")) action = "Nothing";
			hp -= damage;
			if(hp < 1) deleted = true;
		}
	}
	/**
	 * Checks whether object can 'see' player
	 */
	protected boolean checkLOS(Sprite s)
	{
		int px = (int)s.x;
		int py = (int)s.y;
		return !control.wallController.checkObstructionsPoint((float)x, (float)y, (float)px, (float)py, false, 5);
	}
	/**
	 * Checks whether any Proj_Trackers are headed for object
	 */
	protected void checkDanger()
	{   
		inDanger = 0;
		closestDanger[0] = 0;
		closestDanger[1] = 0;
		for(int i = 0; i < control.spriteController.aoes.size(); i++)
		{
			AOE aoe = control.spriteController.aoes.get(i);
			if(Math.pow(x-aoe.x, 2)+Math.pow(y-aoe.y, 2)<Math.pow(aoe.radius+20, 2))
			{
				closestDanger[0]+=aoe.x;
				closestDanger[1]+=aoe.y;
				inDanger++;
			}
		}
		for(int i = 0; i < control.spriteController.shots.size(); i++)
		{
			Shot shot = control.spriteController.shots.get(i);
			if(shot.goodTarget(this, 110))
			{
				closestDanger[0]+=shot.x*2;
				closestDanger[1]+=shot.y*2;
				inDanger+=2;
			}
		}
		closestDanger[0]/=inDanger;
		closestDanger[1]/=inDanger;
	}
	/**
	 * Checks distance between two points
	 * @return Returns distance
	 */
	protected double distanceTo(double toX, double toY)
	{
		return checkDistance(x, y, toX, toY);
	}
	/**
	 * Checks distance between two points
	 * @return Returns distance
	 */
	protected double checkDistance(double fromX, double fromY, double toX, double toY)
	{
		return Math.sqrt((Math.pow(fromX - toX, 2)) + (Math.pow(fromY - toY, 2)));
	}
	protected void baseRunning()
	{
		frame++;
		if(frame == 19) frame = 0; // restart walking motion
		x += xMove;
		y += yMove;
		runTimer--;
		if(runTimer<1)
		{
			action = "Nothing"; // stroll done
			endRun();
		}
	}
	abstract protected void frameCall();
	abstract protected void chooseAction();
	abstract protected void endRun();
	abstract protected void running();
}
