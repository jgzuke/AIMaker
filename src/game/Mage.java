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
		return energy>9 && shootTimer>3;
	}
	protected void shoot(int x, int y)
	{
		if(canShoot())
		{
			control.createShot(Math.atan2((y - getY()), (x-getX()))*r2d, getX(), getY(), getTeam());
			energy -= 10;
			shootTimer = 0;
		}
	}
}
