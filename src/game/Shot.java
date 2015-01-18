package game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Shot extends Sprite
{
	protected View control;
	private Sprite target;
	private double rotChange = 3;
	public Shot(View Control, double X, double Y, double Rotation, BufferedImage[] Images, byte Team)
	{
		super(X, Y, Rotation, Images, Team);
		control = Control;
	}
	protected void frameCall()
	{
		double xForward = Math.cos(rads)*10;
		double yForward = Math.sin(rads)*10;
		double xDif;
		double yDif;
		for(int i = 0; i < 3; i++)
		{
			x += xForward/3;
			y += yForward/3;
			for(int j = 0; j < control.spriteController.enemies.size(); j++)
			{
				if(control.spriteController.enemies.get(j) != null)
				{
					xDif = x - control.spriteController.enemies.get(i).x;
					yDif = y - control.spriteController.enemies.get(i).y;
					if(Math.sqrt(Math.pow(xDif, 2) + Math.pow(yDif, 2)) < 600)
					{
						control.spriteController.createAOE((int)x, (int)y, 60);
						deleted = true;
					}
				}
			}
			if(control.wallController.checkHitBack(x, y, false) && !deleted)
			{
				control.spriteController.createSafeAOE((int)x, (int)y);
				deleted = true;
			}
		}
		if(target != null)
		{
			xDif = target.x-x;
			yDif = target.y-y;
			double newRotation = Math.atan2(yDif, xDif) * r2d;
			double fix = compareRot(newRotation/r2d);
			if(fix>rotChange/2)
			{
				rotation += rotChange;
			} else if(fix<-rotChange/2)
			{
				rotation -= rotChange;
			} else
			{
				rotation += fix;
			}
			xForward = Math.cos(rotation/r2d) * 10;
			yForward = Math.sin(rotation/r2d) * 10;
			double needToTurn = Math.abs(rotation-newRotation);
			if(needToTurn>180) needToTurn = Math.abs(needToTurn-360);
			if(needToTurn>20||target.deleted) target = null;
		} else
		{
			for(int i = 0; i < control.spriteController.enemies.size(); i++)
			{
				if(control.spriteController.enemies.get(i) != null && !deleted)
				{
					if(goodTarget(control.spriteController.enemies.get(i), 200)) target = control.spriteController.enemies.get(i);
				}
			}
		}
	}
	protected boolean goodTarget(Sprite s, int d)
	{
		double xDif = s.x-x;
		double yDif = s.y-y;
		double distance = Math.sqrt(Math.pow(xDif, 2)+Math.pow(yDif, 2));
		double newRotation = Math.atan2(yDif, xDif) * r2d;
		double needToTurn = Math.abs(rotation-newRotation);
		if(needToTurn>180) needToTurn = 360-needToTurn;
		return needToTurn<20&&distance<d;
	}
	public double compareRot(double newRotation)
	{
		newRotation*=r2d;
		double fix = 400;
		while(newRotation<0) newRotation+=360;
		while(rotation<0) rotation+=360;
		if(newRotation>290 && rotation<70) newRotation-=360;
		if(rotation>290 && newRotation<70) rotation-=360;
		fix = newRotation-rotation;
		return fix;
	}
}
