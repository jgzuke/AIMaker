package game;

import java.util.ArrayList;

public final class AOE
{
	private int x;
	private int y;
	private boolean deleted = false;
	private byte team;
	private int radius = 10;
	private float alpha = 0.8f;
	private int timer = 0;
	private ArrayList<Enemy> enemies;
	public AOE(double X, double Y, byte Team, ArrayList<Enemy> Enemies)
	{
		x=(int)X;
		y=(int)Y;
		team = Team;
		enemies = Enemies;
	}
	public AOE(double X, double Y, byte Team)
	{
		x=(int)X;
		y=(int)Y;
		team = Team;
		enemies = new ArrayList<Enemy>();
	}
	protected void frameCall()
	{
		radius += 10;
		alpha -= 0.087;
		double xDif;
		double yDif;
		if(timer>7)
		{
			deleted = true;
		} else
		{
			for(int i = 0; i < enemies.size(); i++)
			{
				if(enemies.get(i) != null && enemies.get(i).getTeam() != team)
				{
					xDif = getX() - enemies.get(i).getX();
					yDif = getY() - enemies.get(i).getY();
					if(Math.sqrt(Math.pow(xDif, 2) + Math.pow(yDif, 2)) < radius)
					{
						enemies.get(i).getHit(7);
					}
				}
			}
		}
		timer++;
	}
	protected byte getTeam(){return team;}
	protected int getX() { return x; }
	protected int getY() { return y; }
	protected int getRadius() { return radius; }
	protected float getAlpha() { return alpha; }
	protected boolean getDeleted() { return deleted; }
}
