package game;

public abstract class Mage extends Enemy {
	public Mage(Packet p){super(p);}
	private int shootTimer = 5;
	private int energy = 90;
	private int tillRollTimer = 0;
	@Override
	protected void frameCall()
	{
		chooseAction();
	}
	@Override
	protected void everyFrame()
	{
		energy ++;
		if(energy>90) energy=90;
		shootTimer++;
		tillRollTimer++;
	}
	protected boolean canRoll()
	{
		return tillRollTimer>30;
	}
	protected boolean canShoot()
	{
		return energy>9 && shootTimer>3;
	}
	@Override
	protected void roll()
	{
		if(canRoll())
		{
			tillRollTimer = 0;
			super.roll();
		}
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
