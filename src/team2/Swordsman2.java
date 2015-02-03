package team2;

import game.Packet;
import game.Swordsman;
public final class Swordsman2 extends Swordsman {
	public Swordsman2(Packet p){super(p);}
	protected double dangerX = 0;
	protected double dangerY = 0;
	protected int lastX = 0;
	protected int lastY = 0;
	@Override
	protected void endAttack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void endBlock() {
		// TODO Auto-generated method stub
		
	}
	protected boolean inDanger()
	{
		int dangerCount = 0;
		int posCount = 0;
		dangerX = 0;
		dangerY = 0;
		for(int i = 0; i < numShotsInSight; i++)
		{
			if(distanceToShot(i)<250 && shotHeadedForMe(i))
			{
				dangerX += shotX(i);
				dangerY += shotY(i);
				posCount++;
				if(distanceToShot(i)<180)
				{
					dangerCount++;
				}
			}
		}
		if(dangerCount < 1) return false;
		dangerX /= posCount;
		dangerY /= posCount;
		return true;
	}
	protected boolean shotHeadedForMe(int i)
	{
		for(int j = 0; j < 10; j++)
		{
			if(distanceTo(shotX(i)+j*shotXVelocity(i)/2, shotY(i)+j*shotYVelocity(i)/2) < 10) return true;
		}
		return false;
	}
	@Override
	protected void attacking() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void blocking() {
		if(inDanger())
		{
			turnToward(dangerX, dangerY);
			holdBlock();
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
	protected void chooseAction() {
		int closestEnemy = findClosest();
		if(closestEnemy>-1)
		{
			if(distanceToEnemy(closestEnemy)<30)
			{
				turnToward(enemyX(closestEnemy), enemyY(closestEnemy));
				attack();
			} else
			{
				if(inDanger())
				{
					turnToward(dangerX, dangerY);
					block();
				} else
				{
					turnToward(enemyX(closestEnemy), enemyY(closestEnemy));
					run(4);
				}
			}
			lastX = enemyX(closestEnemy);
			lastY = enemyY(closestEnemy);
		} else
		{
			turnToward(lastX, lastY);
			run(4);
		}
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
			block();
		}
	}
}