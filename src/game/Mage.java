package game;

public abstract class Mage extends Enemy {
	public Mage(Packet p){super(p);}
	protected int shootTimer = 5;
	protected int energy = 90;
	@Override
	protected void frameCall()
	{
		energy ++;
		if(energy>90) energy=90;
		shootTimer++;
		chooseAction();
	}
	protected boolean canShoot()
	{
		return energy>25 && shootTimer>3;
	}
	protected void shoot()
	{
		if(canShoot())
		{
			control.createShot(getRotation(), getX(), getY(), getTeam());
			energy -= 25;
			shootTimer = 0;
		}
	}
}
