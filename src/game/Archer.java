package game;

import java.awt.image.BufferedImage;

public abstract class Archer extends EnemyActions {

	public Archer(View creator, double X, double Y, double R, int HP,
			BufferedImage[] Images, byte Team) {
		super(creator, X, Y, R, HP, Images, Team);
		// TODO Auto-generated constructor stub
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
		int v = 10; //projectile velocity
		if(frame==36) // shoots
		{
			control.spriteController.createProj_TrackerEnemy(rotation, Math.cos(rads) * v, Math.sin(rads) * v, 130, x, y);
			control.playEffect("arrowrelease");
			justShot();
		}
	}
}
