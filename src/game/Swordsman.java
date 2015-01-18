package game;

import java.awt.image.BufferedImage;

public abstract class Swordsman extends EnemyActions {
	public Swordsman(View creator, double X, double Y, double R, int HP,
			BufferedImage[] Images, byte Team) {
		super(creator, X, Y, R, HP, Images, Team);
		// TODO Auto-generated constructor stub
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
	

	protected void frameCall()
	{
		if(action.equals("Melee"))
		{
			frame++;
			attacking();
			if(frame==27 || frame==37) meleeAttack(200, 20, 10);
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
			running();
			if(frame == frames[0][1]) frame = 0; // restart walking motion
			x += xMove;
			y += yMove;
			runTimer--;
			if(runTimer<1)
			{
				action = "Nothing"; // stroll done
			}
		} else
		{
			myActions();
		}
	}
	@Override
	protected void getHit(double damage)
	{
		if(frame>47&&frame<53) damage /= 8;
		super.getHit(damage);
	}
	abstract protected void attacking();
	abstract protected void blocking();
	abstract protected void running();
}
