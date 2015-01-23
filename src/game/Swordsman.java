package game;
public abstract class Swordsman extends Enemy {
	public Swordsman(Packet p){super(p);}
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
			double distanceFound = checkDistance(getX() + Math.cos(getRads()) * ahead, getY() + Math.sin(getRads()) * ahead, enemy.getX(), enemy.getY());
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
