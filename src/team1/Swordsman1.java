package team1;

import game.Packet;
import game.Swordsman;

public final class Swordsman1 extends Swordsman {
	public Swordsman1(Packet p){super(p);}
	@Override
	protected void endAttack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void endBlock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void attacking() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void blocking() {
		// TODO Auto-generated method stub
		
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
	protected void chooseAction() {
		int closestEnemy = findClosest();
		if(closestEnemy>-1)
		{
			if(checkDistance(enemyX.get(closestEnemy), enemyY.get(closestEnemy), getX(), getY())<30)
			{
				turnToward(enemyX.get(closestEnemy), enemyY.get(closestEnemy));
				attack();
			} else
			{
				turnToward(enemyX.get(closestEnemy), enemyY.get(closestEnemy));
				run(4);
			}
		}
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
