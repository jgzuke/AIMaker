package game;

import java.awt.image.BufferedImage;

public abstract class Sprite
{
	protected double x;
	protected double y;
	protected double rotation;
	protected int frame = 0;
	protected double r2d = 180 / Math.PI;
	protected double rads;
	protected boolean deleted = false;
	protected BufferedImage image;
	protected BufferedImage [] images;
	public Sprite(double X, double Y, double Rotation, BufferedImage [] Images)
	{
		x=X;
		y=Y;
		rotation=Rotation;
		images = Images;
		image = Images[0];
	}
}
