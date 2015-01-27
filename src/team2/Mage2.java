package team2;

import game.Mage;
import game.Packet;
public final class Mage2 extends Mage {
	public Mage2(Packet p){super(p);}
	@Override
	protected void chooseAction() {
		int closestEnemy = findClosest();
		if(closestEnemy>-1)
		{
			int dist = (int) checkDistance(enemyX(closestEnemy), enemyY(closestEnemy), getX(), getY());
			if(dist<400) shoot(enemyX(closestEnemy), enemyY(closestEnemy));
			
			turnToward(enemyX(closestEnemy), enemyY(closestEnemy));
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
			int dist = (int) checkDistance(enemyX(i), enemyY(i), getX(), getY());
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
