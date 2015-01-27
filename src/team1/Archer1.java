package team1;

import game.Archer;
import game.Enemy;
import game.Packet;

public final class Archer1 extends Archer {

	public Archer1(Packet p){super(p);}
	/*
	 * AVALIABLE ACTIONS
	 * run(i);	runs forward for i frames
	 * roll(); rolls forward
	 * turnToward(x, y); turns towards those coordinates
	 * shoot(); shoots a bullet
	 * shootAgain(); if called in justShot(), shoots again
	 * 
	 * AVALIABLE VARIABLES
	 * 
	 * 
	 * 
	 * UTILITY FUNCTIONS
	 * checkHitBack(x, y, grounded)
	 * checkObstructions(x, y, x2, y2, grounded, expand)
	 * checkDistance(x, y, x2, y2)
	 */
	@Override
	protected void chooseAction()
	{
		int closestEnemy = findClosest();
		if(closestEnemy>-1)
		{
			if(checkDistance(enemyX(closestEnemy), enemyY(closestEnemy), getX(), getY())<400)
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
	protected void endShot() {
		
	}

	@Override
	protected void justShot() {
		int closestEnemy = findClosest();
		if(closestEnemy>-1)
		{
			if(checkDistance(enemyX(closestEnemy), enemyY(closestEnemy), getX(), getY())<400)
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
