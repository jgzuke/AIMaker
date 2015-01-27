package game;

import java.util.ArrayList;
import java.util.List;

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
	private byte type;
	protected final ControlAccess control;
	
	
	protected int numEnemiesInSight;
	protected int numAlliesInSight;
	protected int numShotsInSight;
	protected int numExplosionsInSight;
	protected List<Integer> enemyX = new ArrayList<>();
	protected List<Integer> enemyY = new ArrayList<>();
	protected List<Byte> enemyTeam = new ArrayList<>();
	protected List<Byte> enemyType = new ArrayList<>();
	protected List<Integer> allyX = new ArrayList<>();
	protected List<Integer> allyY = new ArrayList<>();
	protected List<Byte> allyType = new ArrayList<>();
	protected List<Integer> shotX = new ArrayList<>();
	protected List<Integer> shotY = new ArrayList<>();
	protected List<Double> shotXVelocity = new ArrayList<>();
	protected List<Double> shotYVelocity = new ArrayList<>();
	protected List<Integer> explosionX = new ArrayList<>();
	protected List<Integer> explosionY = new ArrayList<>();
	protected List<Integer> explosionSize = new ArrayList<>();
	
	
	//ENEMY STUFF
	
	private int hp;
	private int hpMax;
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
		hp = p.h;
		hpMax = p.h;
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
	public int getX() { return (int)x; }
	protected double getHealthFraction() { return (double)hp/hpMax; }
	public int getY() { return (int)y; }
	public int getRotation() { return (int)rotation; }
	public byte getTeam() { return team; }
	public byte getType() { return type; }
	public boolean getDeleted() { return deleted; }
	
	
	
	
	
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
			setEnemyInfo();
			frameCall();
		}
		if(hp > hpMax) hp = hpMax;
		if(x<10) x=10;
		if(y<10) y=10;
		if(x>control.getLevelWidth()-10) x=control.getLevelWidth()-10;
		if(y>control.getLevelHeight()-10) y=control.getLevelHeight()-10;
	}
	private void setEnemyInfo()
	{
		numEnemiesInSight = 0;
		numAlliesInSight = 0;
		Enemy e;
		enemyX.clear();
		enemyY.clear();
		enemyTeam.clear();
		enemyType.clear();
		allyX.clear();
		allyY.clear();
		allyType.clear();
		for(int i = 0; i < control.enemies.size(); i++)
		{
			e=control.enemies.get(i);
			if(!checkObstructions(e.getX(), e.getY(), (int)x, (int)y, false, 10))
			{
				if(e.getTeam() != team)
				{
					enemyX.add(e.getX());
					enemyY.add(e.getY());
					enemyTeam.add(e.getTeam());
					enemyType.add(e.getType());
					numEnemiesInSight++;
				} else
				{
					allyX.add(e.getX());
					allyY.add(e.getY());
					allyType.add(e.getType());
					numAlliesInSight++;
				}
			}
		}
		numShotsInSight = 0;
		Shot s;
		shotX.clear();
		shotY.clear();
		shotXVelocity.clear();
		shotYVelocity.clear();
		for(int i = 0; i < control.shots.size(); i++)
		{
			s=control.shots.get(i);
			if(s.getTeam() != team)
			{
				if(!checkObstructions(s.getX(), s.getY(), (int)x, (int)y, false, 10))
				{
					shotX.add(s.getX());
					shotY.add(s.getY());
					shotXVelocity.add(s.getXVelocity());
					shotYVelocity.add(s.getYVelocity());
					numShotsInSight++;
				}
			}
		}
		numExplosionsInSight = 0;
		AOE ex;
		explosionX.clear();
		explosionY.clear();
		explosionSize.clear();
		for(int i = 0; i < control.aoes.size(); i++)
		{
			ex=control.aoes.get(i);
			if(ex.getTeam() != team)
			{
				if(!checkObstructions(ex.getX(), ex.getY(), (int)x, (int)y, false, 10))
				{
					explosionX.add(ex.getX());
					explosionY.add(ex.getY());
					explosionSize.add(ex.getRadius());
					numExplosionsInSight++;
				}
			}
		}
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
		for(int i = 0; i < control.enemies.size(); i++)
		{
			if(control.enemies.get(i) != null&& control.enemies.get(i).getX() != x)
			{
				xdif = x - control.enemies.get(i).getX();
				ydif = y - control.enemies.get(i).getY();
				if(Math.pow(xdif, 2) + Math.pow(ydif, 2) < Math.pow(radius, 2))
				{
					moveRads = Math.atan2(ydif, xdif);
					movementX = (x - (Math.cos(moveRads) * radius) - control.enemies.get(i).getX())/2;
					movementY = (y - (Math.sin(moveRads) * radius) - control.enemies.get(i).getY())/2;
					control.enemies.get(i).getPushed(control.enemies.get(i).getX() + movementX, control.enemies.get(i).getY() + movementY);
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
		if(action.equals("Nothing"))
		{
			runTimer = i;
			action = "Run";
			xMove = Math.cos(rads) * speedCur;
			yMove = Math.sin(rads) * speedCur;
	        if(frame>17) frame = 0;
		}
	}
	/**
	 * rolls forward for 11 frames
	 */
	protected void roll()
	{
		if(type==2 && action.equals("Nothing"))
		{
			frame = 20;
			action = "Roll";
			rotation = rads * r2d;
			xMove = Math.cos(rads) * 8;
			yMove = Math.sin(rads) * 8;
		}
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
		return control.checkHitBack(X, Y, objectOnGround);
	}
	protected boolean checkObstructions(float x1, float y1, float x2, float y2, boolean objectOnGround, int expand)
	{
		return control.checkObstructions(x1, y1, x2, y2, objectOnGround, expand);
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
