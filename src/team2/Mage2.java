package team2;

import game.Mage;
import game.Packet;
public final class Mage2 extends Mage {
	public Mage2(Packet p){super(p);}
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
			} else
			{
				turnToward(lastX, lastY);
				run(4);
			}
		}
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
		for(int i = 0; i < numExplosionsInSight; i++)
		{
			if(distanceToExplosion(i)<60 && explosionSize(i)<60)
			{
				dangerX = explosionX(i);
				dangerY = explosionY(i);
				return true;
			}
		}
		for(int i = 0; i < numShotsInSight; i++)
		{
			if(distanceToShot(i)<100 && shotHeadedForMe(i))
			{
				dangerX = shotX(i);
				dangerY = shotY(i);
				return true;
			}
		}
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