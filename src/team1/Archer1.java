package team1;

import game.Archer;
import game.Enemy;
import game.Packet;

public final class Archer1 extends Archer {

	public Archer1(Packet p){super(p);}
	@Override
	protected void chooseAction()
	{
		int closestEnemy = findClosest();
		if(closestEnemy>-1)
		{
			if(distanceToEnemy(closestEnemy)<400)
			{
				turnToward(enemyX(closestEnemy), enemyY(closestEnemy));
				shoot();
			} else
			{
				turnToward(enemyX(closestEnemy), enemyY(closestEnemy));
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
			int dist = distanceToEnemy(i);
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
			if(distanceToEnemy(closestEnemy)<400)
			{
				turnToward(enemyX(closestEnemy), enemyY(closestEnemy));
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
