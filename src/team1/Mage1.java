package team1;

import game.Mage;
import game.Packet;

public final class Mage1 extends Mage {
	public Mage1(Packet p){super(p);}
	protected int dangerX = 0;
	protected int dangerY = 0;
	protected int lastX = 0;
	protected int lastY = 0;
	protected boolean checkedLastX = true;
	@Override
	protected void chooseAction() {
		if(inDanger())
		{
			turnToward(dangerX, dangerY);
			if(Math.random()>0.5)
			{
				turn(85);
			} else
			{
				turn(-85);
			}
			if(canRoll()) roll();
			else run(6);
		} else
		{
			int closestEnemy = findClosest();
			if(closestEnemy>-1)
			{
				int dist = distanceToEnemy(closestEnemy);
				if(dist<400) shoot(enemyX(closestEnemy), enemyY(closestEnemy));
				
				turnToward(enemyX(closestEnemy), enemyY(closestEnemy));
				if(dist<150)
				{
					turn(180);
				} else if(dist<230)
				{
					if(Math.random()>0.5)
					{
						turn(85);
					} else
					{
						turn(-85);
					}
				}
				run(9);
				lastX = enemyX(closestEnemy);
				lastY = enemyY(closestEnemy);
				checkedLastX = false;
			} else
			{
				if(!checkedLastX)
				{
					turnToward(lastX, lastY);
					if(distanceTo(lastX, lastY) < 40)
					{
						checkedLastX = true;
					}
					run(2);
				} else
				{
					runRandom();
				}
			}
		}
	}
	private void runRandom()
	{
		int x = (int)Math.random()*control.getLevelWidth();
		int y = (int)Math.random()*control.getLevelHeight();
		while(distanceTo(x, y) <150 || control.checkObstructions(getX(), getY(), x, y, true, 10))
		{
			x = (int)Math.random()*control.getLevelWidth();
			y = (int)Math.random()*control.getLevelHeight();
		}
		turnToward(x, y);
		run(9);
	}
	private int findClosest()
	{
		int closestDist = 1000;
		int enemy = -1;
		for(int i = 0; i < numEnemiesInSight; i++)
		{
			int dist = (int) distance(enemyX(i), enemyY(i), getX(), getY());
			if(dist<closestDist)
			{
				closestDist = dist;
				enemy = i;
			}
		}
		return enemy;
	}
	protected boolean inDanger()
	{
		int dangerCount = 0;
		int posCount = 0;
		dangerX = 0;
		dangerY = 0;
		for(int i = 0; i < numExplosionsInSight; i++)
		{
			if(distanceToExplosion(i)<70 && explosionSize(i)<70)
			{
				dangerCount++;
			}
		}
		for(int i = 0; i < numShotsInSight; i++)
		{
			if(distanceToShot(i)<200 && shotHeadedForMe(i))
			{
				dangerX += shotX(i);
				dangerY += shotY(i);
				posCount++;
				if(distanceToShot(i)<130)
				{
					dangerCount++;
				}
			}
		}
		if(posCount>0)
		{
			dangerX /= posCount;
			dangerY /= posCount;
		}
		if(dangerCount > 0) return true;
		return false;
	}
	protected boolean shotHeadedForMe(int i)
	{
		for(int j = 0; j < 10; j++)
		{
			if(distanceTo(shotX(i)+j*shotXVelocity(i), shotY(i)+j*shotYVelocity(i)) < 30) return true;
		}
		return false;
	}
	@Override
	protected void endRun() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void running() {
		if(inDanger())
		{
			turnToward(dangerX, dangerY);
			if(Math.random()>0.5)
			{
				turn(85);
			} else
			{
				turn(-85);
			}
			if(canRoll()) roll();
			else run(6);
		}
	}
}
