/**
 * behavior for ring walls
 */
package game;

import java.util.ArrayList;

public class Wall_Ring extends Wall
{
	private double xdif;
	private double ydif;
	protected int oCX;
	protected int oCY;
	private int oCRIn;
	protected int oCROut;
	private double oCRSIn;
	private double oCRSOut;
	private double oCRSAve;
	private double rads;
	/**
	 * sets variables and stores some in control object array
	 * @param creator control object
	 * @param OCX x value
	 * @param OCY y value
	 * @param OCRIn inner radius
	 * @param OCROut outer radius
	 */
	public Wall_Ring(View creator, int OCX, int OCY, int OCRIn, int OCROut, boolean tall)
	{
		oCX = OCX;
		oCY = OCY;
		oCRIn = OCRIn;
		oCROut = OCROut;
		control = creator;
		oCRIn -= humanWidth;
		oCROut += humanWidth;
		oCRSIn = Math.pow(oCRIn, 2);
		oCRSOut = Math.pow(oCROut, 2);
		oCRSAve = Math.pow((oCRIn+oCROut)/2, 2);
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
				double curX = enemies.get(i).getX();
				double curY = enemies.get(i).getY();
				xdif = oCX - curX;
				ydif = oCY - curY;
				rads = Math.atan2(ydif, xdif);
				double dist = Math.pow(xdif, 2) + Math.pow(ydif, 2);
				if(dist < oCRSOut&&dist>oCRSIn)
				{
					if(!control.wallController.checkHitBackPass(curX, curY, true))
					{
						if(dist<oCRSAve)
						{
							enemies.get(i).getPushed(oCX - (Math.cos(rads) * oCRIn), oCY - (Math.sin(rads) * oCRIn));
						} else
						{
							enemies.get(i).getPushed(oCX - (Math.cos(rads) * oCROut), oCY - (Math.sin(rads) * oCROut));
						}
					}
				}
			}
		}
	}
}