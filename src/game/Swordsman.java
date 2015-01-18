package game;

import java.awt.image.BufferedImage;

public class Swordsman extends EnemyActions {
	public Swordsman(View creator, double X, double Y, double R, int HP,
			BufferedImage[] Images) {
		super(creator, X, Y, R, HP, Images);
		// TODO Auto-generated constructor stub
	}

	/**
	 * when enemy swings at player, check whether it hits
	 */
	protected void meleeAttack(int damage, int range, int ahead)
	{
		double distanceFound = checkDistance(x + Math.cos(rads) * ahead, y + Math.sin(rads) * ahead, control.player.x, control.player.y);
		if(distanceFound < range)
		{
			control.player.getHit((int)(damage));
			control.soundController.playEffect("sword2");
			if(control.getRandomInt(3) == 0)
			{
				control.player.rads = Math.atan2(control.player.y-y, control.player.x-x);
				control.player.stun();
			}
		} else
		{
			control.soundController.playEffect("swordmiss");
		}
	}
}
