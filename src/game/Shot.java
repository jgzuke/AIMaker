package game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public final class Shot
{
	private double x;
	private double y;
	private double xForward;
	private double yForward;
	private boolean deleted = false;
	private byte team;
	private View control;
	private int rotation;
	private ArrayList<Enemy> enemies;
	public Shot(double X, double Y, double Rotation, byte Team, View c, ArrayList<Enemy> Enemies)
	{
		x=X;
		y=Y;
		rotation = (int)Rotation;
		xForward = Math.cos(Rotation/57.2957795)*10;
		yForward = Math.sin(Rotation/57.2957795)*10;
		x += xForward*1.5;
		y += yForward*1.5;
		team = Team;
		control = c;
		enemies = Enemies;
	}
	protected int getX() { return (int)x; }
	protected int getY() { return (int)y; }
	protected double getXVelocity() { return xForward; }
	protected double getYVelocity() { return yForward; }
	protected byte getTeam() { return team; }
	protected int getRotation() { return rotation; }
	protected boolean getDeleted() { return deleted; }
	
	
	protected void frameCall()
	{
		double xDif;
		double yDif;
		Enemy e;
		for(int i = 0; i < 3; i++)
		{
			x += xForward/3;
			y += yForward/3;
			for(int j = 0; j < enemies.size(); j++)
			{
				e=enemies.get(j);
				if(e != null && e.getTeam() != team)
				{
					xDif = x - e.getX();
					yDif = y - e.getY();
					if(Math.pow(xDif, 2) + Math.pow(yDif, 2) < 600)
					{
						e.getHit(20);
						control.spriteController.createAOE(x, y, team);
						deleted = true;
					}
				}
			}
			if(control.wallController.checkHitBack(x, y, false) && !deleted)
			{
				control.spriteController.createSafeAOE(x, y, team);
				deleted = true;
			}
		}
	}
}
