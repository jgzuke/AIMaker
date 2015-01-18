package game;

import java.awt.image.BufferedImage;

public abstract class Swordsman extends EnemyActions {
	public Swordsman(View creator, double X, double Y, double R, int HP,
			BufferedImage[] Images, byte Team) {
		super(creator, X, Y, R, HP, Images, Team);
		// TODO Auto-generated constructor stub
	}
	
	protected void attacking()
	{
		
	}
	protected void blocking()
	{
		
	}
	/**
	 * when enemy swings at player, check whether it hits
	 */
	protected void meleeAttack(int damage, int range, int ahead)
	{
		boolean hitEnemy = false;
		for(int i = 0; i < control.spriteController.enemies.size(); i++)
		{
			Enemy enemy = control.spriteController.enemies.get(i);
			double distanceFound = checkDistance(x + Math.cos(rads) * ahead, y + Math.sin(rads) * ahead, enemy.x, enemy.y);
			if(distanceFound < range)
			{
				enemy.getHit((int)(damage));
				hitEnemy = true;
			}
		}
		if(hitEnemy)
		{
			control.playEffect("sword2");
		} else
		{
			control.playEffect("swordmiss");
		}
	}
	

	protected void actions()
	{
		if(action.equals("Melee"))
		{
			frame++;
			attacking();
			if(frame==frames[3][1])
			{
				action = "Nothing";	//attack over
				frame = 0;
			}
		} else if(action.equals("Sheild"))
		{
			frame++;
			blocking();
			if(frame==frames[4][1])
			{
				action = "Nothing";	//block done
				frame = 0;
			}
		} else if(action.equals("Run"))
		{
			frame++;
			if(frame == frames[0][1]) frame = 0; // restart walking motion
			x += xMove*1.2;
			y += yMove*1.2;
			runTimer--;
			if(runTimer<1)
			{
				action = "Nothing"; // stroll done
			}
		}
	}
}
