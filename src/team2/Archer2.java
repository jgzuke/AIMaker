package team2;

import game.Packet;
import game.Archer;



public final class Archer2 extends Archer {

	public Archer2(Packet p){super(p);}
	protected int lastX = 0;
	protected int lastY = 0;
	protected boolean checkedLastX = true;
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
	@Override
	protected void aboutToShoot() {
		int closestEnemy = findClosest();
		if(closestEnemy>-1)
		{
			double x = enemyX(closestEnemy);
			double y = enemyY(closestEnemy);
			double distance = distanceToEnemy(closestEnemy);
			//x += distance/10 * enemyXVelocity(closestEnemy);
			//y += distance/10 * enemyYVelocity(closestEnemy);
			turnToward(x, y);
		}
	}
}

