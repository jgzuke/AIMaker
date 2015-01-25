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
	protected void chooseAction() {
		for(int i = 0; i < numEnemiesInSight; i++)
		{
			if(checkDistance(enemyX.get(i), enemyY.get(i), getX(), getY())<400)
			{
				turnToward(enemyX.get(i), enemyY.get(i));
				shoot();
				break;
			} else
			{
				turnToward(enemyX.get(i), enemyY.get(i));
				run(4);
				break;
			}
		}
	}
	
	@Override
	protected void shooting() {
		
	}

	@Override
	protected void endShot() {
		
	}

	@Override
	protected void justShot() {
		
	}

	@Override
	protected void endRun() {
		
	}

	@Override
	protected void running() {
		
	}
}
