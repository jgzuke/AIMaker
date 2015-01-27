package team2;

import game.Packet;
import game.Archer;



public final class Archer2 extends Archer {

	public Archer2(Packet p){super(p);}

	@Override
	protected void chooseAction()
	{
		int closestEnemy = findClosest();
		if(closestEnemy>-1)
		{
			if(checkDistance(enemyX.get(closestEnemy), enemyY.get(closestEnemy), getX(), getY())<400)
			{
				turnToward(enemyX.get(closestEnemy), enemyY.get(closestEnemy));
				shoot();
			} else
			{
				turnToward(enemyX.get(closestEnemy), enemyY.get(closestEnemy));
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
	protected void endShot() {
		
	}

	@Override
	protected void justShot() {
		int closestEnemy = findClosest();
		if(closestEnemy>-1)
		{
			if(checkDistance(enemyX.get(closestEnemy), enemyY.get(closestEnemy), getX(), getY())<400)
			{
				turnToward(enemyX.get(closestEnemy), enemyY.get(closestEnemy));
				shootAgain();
			}
		}
	}

	@Override
	protected void endRun() {
		
	}

	@Override
	protected void running() {
		
	}
	@Override
	protected void shooting() {
		
	}
}
