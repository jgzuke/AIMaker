package team1;

import game.Mage;
import game.Packet;

public final class Mage1 extends Mage {
	public Mage1(Packet p){super(p);}
	@Override
	protected void chooseAction() {
		int closestEnemy = findClosest();
		if(closestEnemy>-1)
		{
			int dist = (int) checkDistance(enemyX.get(closestEnemy), enemyY.get(closestEnemy), getX(), getY());
			if(dist<400) shoot(enemyX.get(closestEnemy), enemyY.get(closestEnemy));
			
			turnToward(enemyX.get(closestEnemy), enemyY.get(closestEnemy));
			if(dist<200)
			{
				turn(180);
			} else if(dist<300)
			{
				if(Math.random()>0.5)
				{
					turn(90);
				} else
				{
					turn(-90);
				}
			}
			run(9);
		}
	}
	private int findClosest()
	{
		int closestDist = 1000;
		int enemy = -1;
		for(int i = 0; i < numEnemiesInSight; i++)
		{
			int dist = (int) checkDistance(enemyX.get(i), enemyY.get(i), getX(), getY());
			if(dist<closestDist)
			{
				closestDist = dist;
				enemy = i;
			}
		}
		return enemy;
	}
	@Override
	protected void endRun() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void running() {
		// TODO Auto-generated method stub
		
	}
}
