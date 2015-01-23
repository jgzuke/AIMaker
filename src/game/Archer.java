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
	abstract protected void shooting();
	abstract protected void endShot();
	abstract protected void justShot();
	
	protected void baseShooting()
	{
		frame++;
		if(frame==49)
		{
			action = "Nothing";	//attack over
			frame = 0;
			endShot();
		}
		if(frame==36) // shoots
		{
			control.createShot(getRotation(), getX(), getY(), getTeam());
			control.playEffect("arrowrelease");
			justShot();
		}
	}
}
