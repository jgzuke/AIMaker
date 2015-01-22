package game;

import java.awt.image.BufferedImage;

public abstract class Swordsman extends EnemyActions {
	public Swordsman(View creator, double X, double Y, double R, int HP,
			BufferedImage[] Images, byte Team) {
		super(creator, X, Y, R, HP, Images, Team);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void frameCall()
	{
		if(action.equals("Melee"))
		{
			baseAttacking();
			attacking();
		} else if(action.equals("Sheild"))
		{
			baseBlocking();
			blocking();
		} else
		{
			chooseAction();
		}
	}
	protected void attack()
	{
		frame=20;
		action = "Melee";
	}
	protected void block()
	{
		frame=45;
		action = "Sheild";
	}
	@Override
	protected void getHit(double damage)
	{
		if(frame>47&&frame<53) damage /= 8;
		super.getHit(damage);
	}
	private void baseAttacking()
	{
		frame++;
		if(frame==27 || frame==37) meleeAttack(200, 20, 10);
		if(frame==45)
		{
			action = "Nothing";	//attack over
			frame = 0;
			endAttack();
		}
	}
	private void baseBlocking()
	{
		frame++;
		if(frame==55)
		{
			action = "Nothing";	//block done
			frame = 0;
			endBlock();
		}
	}
	abstract protected void endAttack();
	abstract protected void endBlock();
	abstract protected void attacking();
	abstract protected void blocking();
	
	
	
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
	
}
