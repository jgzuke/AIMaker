package game;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public final class SpriteHolder
{
	/**
	 * Initializes all undecided variables, loads level, creates player and enemy objects, and starts frameCaller
	 */
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	ArrayList<AOE> aoes = new ArrayList<AOE>();
	ArrayList<Shot> shots = new ArrayList<Shot>();
	public SpriteHolder(View Control)
	{
	}
	
}