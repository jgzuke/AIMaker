/**
 * behavior for circular walls
 */
package game;

import java.util.ArrayList;

public class Wall_Circle extends Wall
{
	private double xdif;
	private double ydif;
	protected int oCX;
	protected int oCY;
	protected int oCR;
	private double oCRS;
	private double rads;
	/**
	 * sets variables and stores some in control object array
	 * @param creator control object
	 * @param OCX x value
	 * @param OCY y value
	 * @param OCR radius
	 * @param OCRatio ratio between x and y
	 * @param Tall whether or not the wall is tall enough to stop projectiles
	 */
	public Wall_Circle(View creator, int OCX, int OCY, int OCR, boolean Tall)
	{
		tall = Tall;
		oCX = OCX;
		oCY = OCY;
		oCR = OCR;
		control = creator;
		oCR += humanWidth;
		oCRS = Math.pow(oCR, 2);
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
				xdif = oCX - enemies.get(i).getX();
				ydif = oCY - enemies.get(i).getY();
				rads = Math.atan2(ydif, xdif);
				if(Math.pow(xdif, 2) + Math.pow(ydif, 2) < oCRS)
				{
					enemies.get(i).getPushed(oCX - (Math.cos(rads) * oCR), oCY - (Math.sin(rads) * oCR));
					enemies.get(i).getPushed(enemies.get(i).getX()+3, enemies.get(i).getY()+3);
					if(Math.random()>0.5) enemies.get(i).getPushed(enemies.get(i).getX()-2, enemies.get(i).getY()-2);
				}
			}
		}
	}
}