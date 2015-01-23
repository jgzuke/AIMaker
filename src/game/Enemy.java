package game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Enemy
{
	//SPRITE STUFF
	private double x;
	private double y;
	protected int frame = 0;
	protected final double r2d = 180 / Math.PI;
	private double rads;
	private double rotation;
	private boolean deleted = false;
	private byte team;
	protected byte type;
	protected ControlAccess control;
	
	
	//ENEMY STUFF
	
	private int hp;
	private int hpMax;
	protected int inDanger = 0;
	protected double[] closestDanger = new double[2];
	private int radius = 20;
	private double xMove = 0;
	private double yMove = 0;
	private double speedCur = 3;
	private int runTimer;
	protected String action = "Nothing"; //"Nothing", "Move", "Alert", "Shoot", "Melee", "Roll", "Hide", "Sheild", "Stun"

	public Enemy(Packet p)
	{
		x=p.x;
		y=p.y;
		rotation = p.r;
		rads=rads(p.r);
		team = p.t;
		type = p.ty;
		control = p.c;
	}
	//SPRITE ACTIONS
	
	private double rads(double rotation)
	{
		return rotation/r2d;
	}
	protected double getRads() { return rads; }
	protected int getX() { return (int)x; }
	protected int getY() { return (int)y; }
	protected int getRotation() { return (int)rotation; }
	protected byte getTeam() { return team; }
	protected byte getType() { return type; }
	protected boolean getDeleted() { return deleted; }
	
	
	
	
	
	//ENEMY STUFF
	protected void enemyFrame()
	{
		pushOtherPeople();
		if(action.equals("Run"))
		{
			baseRunning();
			running();
		} else if(action.equals("Roll"))
		{
			baseRolling();
		} else 
		{
			frameCall();
		}
		if(hp > hpMax) hp = hpMax;
		if(x<10) x=10;
		if(y<10) y=10;
		if(x>790) x=790;
		if(y>790) y=790;
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
		for(int i = 0; i < enemies.size(); i++)
		{
			if(enemies.get(i) != null&& enemies.get(i).getX() != x)
			{
				xdif = x - enemies.get(i).getX();
				ydif = y - enemies.get(i).getY();
				if(Math.pow(xdif, 2) + Math.pow(ydif, 2) < Math.pow(radius, 2))
				{
					moveRads = Math.atan2(ydif, xdif);
					movementX = (x - (Math.cos(moveRads) * radius) - enemies.get(i).getX())/2;
					movementY = (y - (Math.sin(moveRads) * radius) - enemies.get(i).getY())/2;
					enemies.get(i).getPushed(enemies.get(i).getX() + movementX, enemies.get(i).getY() + movementY);
					x -= movementX;
					y -= movementY;
				}
			}
		}
	}
	protected void getPushed(double newX, double newY)
	{
		x = newX;
		y = newY;
	}
	protected void getPushedX(double newX)
	{
		x = newX;
	}
	protected void getPushedY(double newY)
	{
		y = newY;
	}
	protected void getPushedRot(double rot)
	{
		rotation += rot;
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
	 * Checks whether any Proj_Trackers are headed for object
	 */
	private void checkDanger()
	{   
		inDanger = 0;
		closestDanger[0] = 0;
		closestDanger[1] = 0;
		for(int i = 0; i < aoes.size(); i++)
		{
			AOE aoe = aoes.get(i);
			if(Math.pow(x-aoe.getX(), 2)+Math.pow(y-aoe.getY(), 2)<Math.pow(aoe.getRadius()+20, 2))
			{
				closestDanger[0]+=aoe.getX();
				closestDanger[1]+=aoe.getY();
				inDanger++;
			}
		}
		for(int i = 0; i < shots.size(); i++)
		{
			Shot shot = shots.get(i);
			if(shot.goodTarget(this, 110))
			{
				closestDanger[0]+=shot.getX()*2;
				closestDanger[1]+=shot.getY()*2;
				inDanger+=2;
			}
		}
		closestDanger[0]/=inDanger;
		closestDanger[1]/=inDanger;
	}
	private void baseRunning()
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
	private void baseRolling()
	{
		x += xMove;
		y += yMove;
		frame++;
		if(frame==31)
		{
			action = "Nothing";	//roll done
			frame = 0;
			endRun();
		}
	}
	abstract protected void frameCall();
	abstract protected void chooseAction();
	abstract protected void endRun();
	abstract protected void running();
	
	//ENEMY AVALIABLE ACTIONS
	
	/**
	 * Runs in direction object is rotated for 10 frames
	 */
	protected void run(int i)
	{
		runTimer = i;
		action = "Run";
		xMove = Math.cos(rads) * speedCur;
		yMove = Math.sin(rads) * speedCur;
        if(frame>17) frame = 0;
	}
	/**
	 * rolls forward for 11 frames
	 */
	protected void roll()
	{
		frame = 20;
		action = "Roll";
		rotation = rads * r2d;
		xMove = Math.cos(rads) * 8;
		yMove = Math.sin(rads) * 8;
	}
	protected void turnToward(double X, double Y)
	{
		rads = Math.atan2((Y - y), (X - x));
		rotation = rads * r2d;
	}
	protected void turn(int degrees)
	{
		rotation += degrees;
		rads = rotation/r2d;
	}
	protected boolean checkHitBack(double X, double Y, boolean objectOnGround)
	{
		return wallController.checkHitBack(X, Y, objectOnGround);
	}
	protected boolean checkObstructionsPoint(float x1, float y1, float x2, float y2, boolean objectOnGround, int expand)
	{
		return wallController.checkObstructionsPoint(x1, y1, x2, y2, objectOnGround, expand);
	}
	protected boolean checkObstructions(double x1, double y1, double rads, int distance, boolean objectOnGround, int offset)
	{
		return wallController.checkObstructions(x1, y1, rads, distance, objectOnGround, offset);
	}
	/**
	 * Checks distance between two points
	 * @return Returns distance
	 */
	protected double checkDistance(double fromX, double fromY, double toX, double toY)
	{
		return Math.sqrt((Math.pow(fromX - toX, 2)) + (Math.pow(fromY - toY, 2)));
	}
}
