package game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Enemy extends Sprite
{
	protected int hp;
	protected int hpMax;
	protected int hadLOSLastTime;
	protected double speed;
	protected View control;
	protected int fromWall = 5;
	protected int runTimer = 0;
	protected int rollTimer = 0;
	protected double lastPlayerX;
	protected double lastPlayerY;
	protected boolean checkedPlayerLast = true;
	protected int inDanger = 0;
	protected double[] closestDanger = new double[2];
	protected boolean HasLocation = false;
	protected boolean LOS = false;
	protected int radius = 20;
	protected double pXVelocity=0;
	protected double pYVelocity=0;
	private double pXSpot=0;
	private double pYSpot=0;
	protected String action = "Nothing"; //"Nothing", "Move", "Alert", "Shoot", "Melee", "Roll", "Hide", "Sheild", "Stun"

	public Enemy(View creator, double X, double Y, double R, int HP, BufferedImage [] Images)
	{
		super(X, Y, R, Images);
		control = creator;
		lastPlayerX = x;
		lastPlayerY = y;
	}
	protected void frameCall()
	{
		otherActions();
		if(action.equals("Nothing"))
		{
			checkLOS();
			checkDanger();
			if(LOS)
			{
				frameLOS();
			} else
			{
				frameNoLOS();
			}
		}
		image = myImage[frame];
		rollTimer --;
		hadLOSLastTime--;
		if(sick)
		{
			hp -= 20;
			getHit(0);
		}
		pXVelocity = control.player.x-pXSpot;
		pYVelocity = control.player.y-pYSpot;
		pXSpot = control.player.x;
		pYSpot = control.player.y;
		hp += 4;
		super.frameCall();
		sizeImage();
		pushOtherPeople();
		if(hp > hpMax)
		{
			hp = hpMax;
		}
	}
	protected void otherActions()
	{
		/* int [][] frames[action][start/finish or 1/2/3]
		 * actions	move=0;
		 * 			roll=1;		0:start, 1:end
		 * 			stun=2;
		 * 			melee=3;
		 * 			sheild=4;
		 * 			hide=5;
		 * 			shoot=6;
		 */
		if(action.equals("Roll"))
		{
			x += xMove;
			y += yMove;
			frame++;
			if(frame==frames[1][1])
			{
				action = "Nothing";	//roll done
				frame = 0;
			}
		} else if(action.equals("Melee"))
		{
			frame++;
			attacking();
			if(frame==frames[3][1])
			{
				action = "Nothing";	//attack over
				frame = 0;
			}
		} else if(action.equals("Sheild"))
		{
			frame++;
			blocking();
			if(frame==frames[4][1])
			{
				action = "Nothing";	//block done
				frame = 0;
			}
		} else if(action.equals("Hide"))
		{
			hiding();
			if(frame < frames[5][1]-1)
			{
				frame++;
			}
		} else if(action.equals("Shoot"))
		{
			frame++;
			shooting();
			if(frame==frames[6][1])
			{
				action = "Nothing"; // attack done
				frame = 0;
			}
		} else if(action.equals("Run"))
		{
			frame++;
			if(frame == frames[0][1]) frame = 0; // restart walking motion
			x += xMove*1.2;
			y += yMove*1.2;
			runTimer--;
			if(runTimer<1)
			{
				action = "Nothing"; // stroll done
			}
		} else if(action.equals("Move")||action.equals("Wander"))
		{
			checkLOS();
			checkDanger();
			if(inDanger > 0 || LOS)
			{
				 action = "Nothing";
				 frame = 0;
			} else
			{
				frame++;
				if(frame == frames[0][1]) frame = 0; // restart walking motion
				x += xMove;
				y += yMove;
				runTimer--;
				if(runTimer<1) //stroll over
				{
					if(action.equals("Move"))
					{
						action = "Nothing";
						frameNoLOS();
					} else
					{
						action = "Nothing";
						finishWandering();
					}
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
	protected void checkLOS(Sprite s)
	{
		int px = (int)s.x;
		int py = (int)s.y;
		if(!control.wallController.checkObstructionsPoint((float)x, (float)y, (float)px, (float)py, false, fromWall))
		{
			LOS = true;
			hadLOSLastTime = 25;
			lastPlayerX = px;
			lastPlayerY = py;
			checkedPlayerLast = false;
		} else
		{
			LOS = false;
		}
		HasLocation = hadLOSLastTime>0;
	}
	/**
	 * Checks whether any Proj_Trackers are headed for object
	 */
	protected void checkDanger()
	{   
		inDanger = 0;
		closestDanger[0] = 0;
		closestDanger[1] = 0;
		for(int i = 0; i < control.spriteController.proj_TrackerP_AOEs.size(); i++)
		{
			Proj_Tracker_AOE_Player AOE = control.spriteController.proj_TrackerP_AOEs.get(i);
			if(AOE.timeToDeath>7 && Math.pow(x-AOE.x, 2)+Math.pow(y-AOE.y, 2)<Math.pow(AOE.widthDone+25, 2))
			{
				closestDanger[0]+=AOE.x;
				closestDanger[1]+=AOE.y;
				inDanger++;
			}
		}
		for(int i = 0; i < control.spriteController.proj_TrackerPs.size(); i++)
		{
			Proj_Tracker_Player shot = control.spriteController.proj_TrackerPs.get(i);
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
	protected void baseHp(int setHP)
	{
		hp = setHP;
		hpMax = hp;
	}
	abstract protected void attacking();
	abstract protected void hiding();
	abstract protected void shooting();
	abstract protected void blocking();
	abstract protected void finishWandering();
	abstract protected void frameLOS();
	abstract protected void frameNoLOS();
}
