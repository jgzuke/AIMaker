package game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public final class AOE extends Sprite
{
	protected int frame = 0;
	protected int radius = 10;
	protected byte alpha = 120;
	protected View control;
	public AOE(View Control, double X, double Y, byte Team)
	{
		super(X, Y, Control.getRandomInt(360), Control.imageLibrary.aoe, Team);
		control = Control;
	}
	protected void frameCall()
	{
		radius += 10;
		alpha = (byte)(255 - (frame*20));
		double xDif;
		double yDif;
		if(frame==11) deleted = true;
		for(int i = 0; i < control.spriteController.enemies.size(); i++)
		{
			if(control.spriteController.enemies.get(i) != null)
			{
				xDif = x - control.spriteController.enemies.get(i).x;
				yDif = y - control.spriteController.enemies.get(i).y;
				if(Math.sqrt(Math.pow(xDif, 2) + Math.pow(yDif, 2)) < radius)
				{
					control.spriteController.enemies.get(i).getHit(60);
				}
			}
		}
	}
}
