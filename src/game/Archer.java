package game;

import java.awt.image.BufferedImage;

public abstract class Archer extends Enemy {

	public Archer(Packet p){
		super(p);
	}
	
	@Override
	protected void frameCall()
	{
		if(action.equals("Shoot"))
		{
			baseShooting();
			shooting();
		} else
		{
			chooseAction();
		}
	}
	@Override
	protected void everyFrame()
	{
	}
	abstract protected void shooting();
	abstract protected void endShot();
	abstract protected void justShot();
	abstract protected void aboutToShoot();
	protected void shootAgain()
	{
		if(frame==40 && action.equals("Shoot"))
		{
			frame = 28;
		}
	}
	protected void shoot()
	{
		if(action.equals("Nothing"))
		{
			action = "Shoot";
			frame = 20;
		}
	}
	protected void baseShooting()
	{
		frame++;
		if(frame==49)
		{
			action = "Nothing";	//attack over
			frame = 0;
			endShot();
		}
		if(frame==40) // shoots
		{
			aboutToShoot();
			control.createShot(getRotation(), getX(), getY(), getTeam());
			control.playEffect("arrowrelease");
			justShot();
		}
	}
}
