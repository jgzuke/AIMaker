package game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public final class Shot
{
	private double x;
	private double y;
	private final double r2d = 180 / Math.PI;
	private double rads;
	private double rotation;
	private boolean deleted = false;
	private byte team;
	private ArrayList<Enemy> enemies;
	private Enemy target;
	private double rotChange = 3;
	public Shot(double X, double Y, double Rotation, byte Team, ArrayList<Enemy> Enemies)
	{
		x=X;
		y=Y;
		rotation = Rotation;
		rads=rads(Rotation);
		team = Team;
		enemies = Enemies;
	}
	private double rads(double rotation)
	{
		return rotation/r2d;
	}
	protected int getX() { return (int)x; }
	protected int getY() { return (int)y; }
	protected int getRotation() { return (int)rotation; }
	protected byte getTeam() { return team; }
	protected boolean getDeleted() { return deleted; }
	
	
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
			for(int j = 0; j < enemies.size(); j++)
			{
				if(enemies.get(j) != null)
				{
					xDif = x - enemies.get(i).getX();
					yDif = y - enemies.get(i).getY();
					if(Math.sqrt(Math.pow(xDif, 2) + Math.pow(yDif, 2)) < 600)
					{
						spriteController.createAOE(x, y, team);
						deleted = true;
					}
				}
			}
			if(wallController.checkHitBack(x, y, false) && !deleted)
			{
				spriteController.createSafeAOE(x, y, team);
				deleted = true;
			}
		}
		if(target != null)
		{
			xDif = target.getX()-x;
			yDif = target.getY()-y;
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
			if(needToTurn>20||target.getDeleted()) target = null;
		} else
		{
			for(int i = 0; i < enemies.size(); i++)
			{
				if(enemies.get(i) != null && !deleted)
				{
					if(goodTarget(enemies.get(i), 200)) target = enemies.get(i);
				}
			}
		}
	}
	protected boolean goodTarget(Enemy enemy, int d)
	{
		double xDif = enemy.getX()-x;
		double yDif = enemy.getY()-y;
		double distance = Math.sqrt(Math.pow(xDif, 2)+Math.pow(yDif, 2));
		double newRotation = Math.atan2(yDif, xDif) * r2d;
		double needToTurn = Math.abs(rotation-newRotation);
		if(needToTurn>180) needToTurn = 360-needToTurn;
		return needToTurn<20&&distance<d;
	}
	private double compareRot(double newRotation)
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
