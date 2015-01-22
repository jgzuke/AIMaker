package game;

import java.awt.image.BufferedImage;

public abstract class Cleric extends EnemyActions {

	public Cleric(View creator, double X, double Y, double R, int HP,
			BufferedImage[] Images, byte Team) {
		super(creator, X, Y, R, HP, Images, Team);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void frameCall()
	{
		if(action.equals("Roll"))
		{
			baseRolling();
		} else
		{
			chooseAction();
		}
	}
	protected void baseRolling()
	{
		x += xMove;
		y += yMove;
		frame++;
		if(frame==31)
		{
			action = "Nothing";	//roll done
			frame = 0;
			endRoll();
		}
	}
	abstract protected void endRoll();
}
