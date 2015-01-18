package game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class EnemyActions extends Enemy
{
	public EnemyActions(View creator, double X, double Y, double R, int HP, BufferedImage [] Images)
	{
		super(creator, X, Y, R, HP, Images);
	}
	/**
	 * Rotates to run away from player 
	 */
	protected void runAway(double px, double py)
	{
		rads = Math.atan2(-(py - y), -(px - x));
		rotation = rads * r2d;
		int distance = (int)checkDistance(x, y, px,  py);
		if(checkObstructions(x, y, rads, distance, true, 5))
		{
			int runPathChooseCounter = 0;
			double runPathChooseRotationStore = rotation;
			while(runPathChooseCounter < 180)
			{
				runPathChooseCounter += 10;
				rotation = runPathChooseRotationStore + runPathChooseCounter;
				rads = rotation / r2d;
				if(!checkObstructions(x, y, rads, 40, true, 5))
				{
					runPathChooseCounter = 300;
				}
				else
				{
					rotation = runPathChooseRotationStore - runPathChooseCounter;
					rads = rotation / r2d;
					if(!checkObstructions(x, y,  rads, 40, true, 5))
					{
						runPathChooseCounter = 300;
					}
				}
			}
		}
		run(4);
	}        
	/**
	 * Aims towards player
	 */
	protected void aimAheadOfPlayer(Sprite s)
	{
			double timeToHit = (checkDistance(x, y, s.x, s.y))/10;
			timeToHit *= (control.getRandomDouble()*0.7)+0.6;
			double newPX = s.x+(pXVelocity*timeToHit);
			double newPY = s.y+(pYVelocity*timeToHit);
			double xDif = newPX-x;
			double yDif = newPY-y;
			rads = Math.atan2(yDif, xDif); // ROTATES TOWARDS PLAYER
			rotation = rads * r2d;
	}
	/**
	 * Runs in direction object is rotated for 10 frames
	 */
	protected void run(int time)
	{
		runTimer = time;
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
		if(rollTimer<0)
		{
			rollTimer = 20;
			frame = frames[1][0];
			action = "Roll";
			rotation = rads * r2d;
			xMove = Math.cos(rads) * 8;
			yMove = Math.sin(rads) * 8;
		}
	}
	protected void turnToward(double px, double py)
	{
		rads = Math.atan2((py - y), (px - x));
		rotation = rads * r2d;
	}
	/**
	 * rolls sideways for 11 frames
	 */
	protected void runAround(int radius, int distance, Sprite s)
	{
		double oldRads = rads;
		rads = Math.atan2((s.y - y), (s.x - x));
		rotation = rads * r2d;
		boolean right = !checkObstructions(x, y, (rotation + 90) / r2d, 42, true, 5);
		boolean left = !checkObstructions(x, y, (rotation - 90) / r2d, 42, true, 5);
		if(left||right)
		{
			if(left && right) 		// if left and right pick one
			{
				double difLeft = Math.abs((oldRads*r2d)-(rotation - 90));
				double difRight = Math.abs((oldRads*r2d)-(rotation + 90));
				if(difLeft>300) difLeft -= 360;
				if(difRight>300) difLeft -= 360;
				if(difLeft<difRight && control.getRandomInt(20)!=0)
				{
					right = false;
				}
			}
			int maxTurn = 5;
			if(right)		// gotta be one or the other
			{
				rotation += 90;
				if(distance<radius-10) rotation += maxTurn; // get closer
				if(distance>radius+10) rotation -= maxTurn; // turn away
			} else
			{
				rotation -= 90;
				if(distance<radius-10) rotation -= maxTurn; // get closer
				if(distance>radius+10) rotation += maxTurn; // turn away
			}
			rads = rotation / r2d;
			run(2);
		} else
		{
			turnAround();
			run(5);
		}
	}
	/**
	 * rolls sideways for 11 frames
	 */
	protected void rollSideways(double oX, double oY)
	{
		turnToward(oX, oY);
		turnAround();
		boolean right = !checkObstructions(x, y, (rotation + 90) / r2d, 42, true, 5);
		boolean left = !checkObstructions(x, y, (rotation - 90) / r2d, 42, true, 5);
		if(left||right)
		{
			if(left) rotation -= 90;
			if(right) rotation += 90;
			if(left && right)
			{
				rotation -= 90;
				if(Math.random() > 0.5) rotation += 180;
			}
			rads = rotation / r2d;
		}
		roll();
	}
	/**
	 * rolls sideways for 11 frames
	 */
	protected void runSideways(double oX, double oY)
	{
		turnToward(oX, oY);
		turnAround();
		boolean right = !checkObstructions(x, y, (rotation + 90) / r2d, 30, true, 5);
		boolean left = !checkObstructions(x, y, (rotation - 90) / r2d, 30, true, 5);
		if(left||right)
		{
			if(left) rotation -= 90;
			if(right) rotation += 90;
			if(left && right)
			{
				rotation -= 90;
				if(Math.random() > 0.5) rotation += 180;
			}
			rads = rotation / r2d;
		}
		run(4);
	}
	protected void turnAround()
	{
		rotation += 180;
		rads = rotation / r2d;
	}
	/**
	 * Rolls away from player
	 */
	protected void rollAway(double oX, double oY)
	{
		turnToward(oX, oY);
		turnAround();
		rads = Math.atan2(-(oY - y), -(oX - x));
		rotation = rads * r2d;
		if(!checkObstructions(x, y, rads, 42, true, 5))
		{
			roll();
		} else
		{
			int rollPathChooseCounter = 0;
			double rollPathChooseRotationStore = rotation;
			while(rollPathChooseCounter < 180)
			{
				rollPathChooseCounter += 10;
				rotation = rollPathChooseRotationStore + rollPathChooseCounter;
				rads = rotation / r2d;
				if(!checkObstructions(x, y, rads, 42, true, 5))
				{
					roll();
					rollPathChooseCounter = 180;
				}
				else
				{
					rotation = rollPathChooseRotationStore - rollPathChooseCounter;
					rads = rotation / r2d;
					if(!checkObstructions(x, y, rads, 42, true, 5))
					{
						roll();
						rollPathChooseCounter = 180;
					}
				}
			}
		}
	}
	/**
	 * rolls towards player for 11 frames
	 */
	protected void rollTowards(double oX, double oY)
	{
		turnToward(oX, oY);
		roll();
	}
	/**
	 * Runs towards player, if you cant, run randomly
	 */
	protected void runTowards(double fx, double fy)
	{
		if(checkObstructionsPoint((int)fx, (int)fy, (int)x, (int)y, true, 5))
		{
			int foundPlayer = -1;			//try to find enemy
			int sX = (int)(fx/20);		//start at player
			int sY = (int)(fy/20);
			int eX = (int)x;
			int eY = (int)y;
			int[] p1 = {sX, sY, sX, sY};
			boolean[][] checked=new boolean[(int) (control.levelWidth/20)][(int) (control.levelHeight/20)];
			ArrayList<int[]> points = new ArrayList<int[]>();
			points.add(p1);
			checked[sX][sY]=true;
			int count = 0;
			while(foundPlayer==-1&&count<40)
			{
				foundPlayer=iterateSearch(points, checked, eX, eY);
				count++;
			}
			if(foundPlayer==-1)
			{
				runRandom();
			} else
			{
				int[] closest = points.get(foundPlayer);
				rads = Math.atan2(closest[3]*20 - y, closest[2]*20 - x);
			}
		} else
		{
			rads = Math.atan2(fy - y, fx - x);
		}
		rotation = rads * r2d;
		run(2);
	}
	private int iterateSearch(ArrayList<int[]> points, boolean[][] checked, int eX, int eY)
	{
		int numPoints = points.size();
		for(int i = 0; i < numPoints; i++) // for every endpoint we have, expand
		{
			int x = points.get(i)[0];
			int y = points.get(i)[1];
			if(!checkObstructionsPoint(x*20, y*20, eX, eY, true, 5)) return i;
			if(x>0)
			{	//if were not on the edge, we havent checked it, and its free
				if(!checked[x-1][y]&&!checkHitBack((x-1)*20, y*20, true))
				{
					int[] newPoint = {x-1, y, x, y}; // its a new endpoint
					points.add(newPoint);
					checked[x-1][y]=true;			//weve checked this square
				}
			}
			if(x<checked.length-1)
			{
				if(!checked[x+1][y]&&!checkHitBack((x+1)*20, y*20, true))
				{
					int[] newPoint = {x+1, y, x, y};
					points.add(newPoint);
					checked[x+1][y]=true;
				}
			}
			if(y>0)
			{
				if(!checked[x][y-1]&&!checkHitBack(x*20, (y-1)*20, true))
				{
					int[] newPoint = {x, y-1, x, y};
					points.add(newPoint);
					checked[x][y-1]=true;
				}
			}
			if(y<checked[0].length-1)
			{
				if(!checked[x][y+1]&&!checkHitBack(x*20, (y+1)*20, true))
				{
					int[] newPoint = {x, y+1, x, y};
					points.add(newPoint);
					checked[x][y+1]=true;
				}
			}
		}
		for(int i = 0; i < numPoints; i++) // remove all the old points
		{
			points.remove(0);
		}
		return -1;
	}
	/**
	 * Runs random direction for 25 or if not enough space 10 frames
	 */
	protected void runRandom()
	{
		boolean canMove = false;
		rotation += control.getRandomInt(10)-5;
		rads = rotation / r2d;
		if(checkObstructions(x, y,rads, (int)(speedCur*20), true, 5))
		{
			int runPathChooseCounter = 0;
			double runPathChooseRotationStore = rotation;
			while(runPathChooseCounter < 180)
			{
				runPathChooseCounter += 10;
				rotation = runPathChooseRotationStore + runPathChooseCounter;
				rads = rotation / r2d;
				if(!checkObstructions(x, y,rads, (int)(speedCur*20), true, 5))
				{
					runPathChooseCounter = 180;
					canMove = true;
				}
				else
				{
					rotation = runPathChooseRotationStore - runPathChooseCounter;
					rads = rotation / r2d;
					if(!checkObstructions(x, y,rads, (int)(speedCur*20), true, 5))
					{
						runPathChooseCounter = 180;
						canMove = true;
					}
				}
			}
		}
		if(checkObstructions(x, y,rads, (int)(speedCur*10), true, 5))
		{
			int runPathChooseCounter = 0;
			double runPathChooseRotationStore = rotation;
			while(runPathChooseCounter < 180)
			{
				runPathChooseCounter += 10;
				rotation = runPathChooseRotationStore + runPathChooseCounter;
				rads = rotation / r2d;
				if(!checkObstructions(x, y,rads, (int)(speedCur*10), true, 5))
				{
					runPathChooseCounter = 180;
				}
				else
				{
					rotation = runPathChooseRotationStore - runPathChooseCounter;
					rads = rotation / r2d;
					if(!checkObstructions(x, y,rads, (int)(speedCur*10), true, 5))
					{
						runPathChooseCounter = 180;
					}
				}
			}
		}
		if(canMove)
		{
			run(4);
		} else
		{
			run(2);
		}
		action = "Wander";
	}
	private boolean checkHitBack(double X, double Y, boolean objectOnGround)
	{
		return control.wallController.checkHitBack(X, Y, objectOnGround);
	}
	private boolean checkObstructionsPoint(float x1, float y1, float x2, float y2, boolean objectOnGround, int expand)
	{
		return control.wallController.checkObstructionsPoint(x1, y1, x2, y2, objectOnGround, expand);
	}
	protected boolean checkObstructions(double x1, double y1, double rads, int distance, boolean objectOnGround, int offset)
	{
		return control.wallController.checkObstructions(x1, y1, rads, distance, objectOnGround, offset);
	}
}