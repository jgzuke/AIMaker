/**
 * behavior for rectangular walls
 */
package game;

import java.util.ArrayList;

public class Wall_Rectangle extends Wall
{
	private int x;
	private int y;
	protected int oRX1;
	protected int oRX2;
	protected int oRY1;
	protected int oRY2;
	/**
	 * sets variables and stores some in control object array
	 * @param creator control object
	 * @param ORX x position
	 * @param ORY y position
	 * @param wallWidth width of wall
	 * @param wallHeight height of wall
	 * @param Hit whether wall interacts with the player
	 * @param Tall whether or not the wall is tall enough to stop projectiles
	 */
	public Wall_Rectangle(View creator, int ORX, int ORY, int wallWidth, int wallHeight, boolean Tall)
	{
		tall = Tall;
		oRX1 = ORX;
		oRY1 = ORY;
		oRX2 = ORX+wallWidth;
		oRY2 = ORY+wallHeight;
		x = (oRX1 + oRX2) / 2;
		y = (oRY1 + oRY2) / 2;
		control = creator;
		oRX1 -=humanWidth;
		oRX2 +=humanWidth;
		oRY1 -=humanWidth;
		oRY2 +=humanWidth;
	}
	/**
	 * checks whether wall hits player or enemies
	 */
        @ Override
        protected void frameCall()
        {
        	ArrayList<Enemy> enemies = control.spriteController.enemies;
		for(int i = 0; i < enemies.size(); i++)
		{
			if(enemies.get(i) != null)
			{
				if(enemies.get(i).getX() > oRX1 && enemies.get(i).getX() < oRX2 && enemies.get(i).getY() > oRY1 && enemies.get(i).getY() < oRY2)
				{
						double holdX;
						double holdY;
						if(enemies.get(i).getX() > x)
						{
							holdX = Math.abs(enemies.get(i).getX() - oRX2);
						} else
						{
							holdX = Math.abs(enemies.get(i).getX() - oRX1);
						}
						if(enemies.get(i).getY() > y)
						{
							holdY = Math.abs(enemies.get(i).getY() - oRY2);
						} else
						{
							holdY = Math.abs(enemies.get(i).getY() - oRY1);
						}
						while(enemies.get(i).getRotation()<0) enemies.get(i).getPushedRot(360);
						if((holdX) < (holdY))
						{
							if(enemies.get(i).getX() > x)
							{
								enemies.get(i).getPushedX(oRX2);
								if(enemies.get(i).getRotation()>90&&enemies.get(i).getRotation()<180)
								{
									enemies.get(i).getPushedRot(-2);
								} else
								{
									enemies.get(i).getPushedRot(2);
								}
							}
							else
							{
								enemies.get(i).getPushedX(oRX1);
								if(enemies.get(i).getRotation()>0&&enemies.get(i).getRotation()<90)
								{
									enemies.get(i).getPushedRot(2);
								} else
								{
									enemies.get(i).getPushedRot(-2);
								}
							}
						} else
						{
							if(enemies.get(i).getY() > y)
							{
								enemies.get(i).getPushedY(oRY2);
								if(enemies.get(i).getRotation()>180&&enemies.get(i).getRotation()<270)
								{
									enemies.get(i).getPushedRot(-2);
								} else
								{
									enemies.get(i).getPushedRot(2);
								}
							}
							else
							{
								enemies.get(i).getPushedY(oRY1);
								if(enemies.get(i).getRotation()>0&&enemies.get(i).getRotation()<90)
								{
									enemies.get(i).getPushedRot(-2);
								} else
								{
									enemies.get(i).getPushedRot(2);
								}
							}
						}
				}
			}
		}
	}
}