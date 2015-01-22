package game;

import java.awt.image.BufferedImage;

public abstract class Sprite
{
	protected double x;
	protected double y;
	protected int frame = 0;
	protected double r2d = 180 / Math.PI;
	protected double rads;
	protected double rotation;
	protected boolean deleted = false;
	protected BufferedImage image;
	protected BufferedImage [] images;
	protected byte team;
	public Sprite(double X, double Y, double Rotation, BufferedImage [] Images, byte Team)
	{
		x=X;
		y=Y;
		rads=rads(Rotation);
		images = Images;
		image = Images[0];
		team = Team;
	}
	public Sprite(double X, double Y, double Rotation, BufferedImage Image, byte Team)
	{
		x=X;
		y=Y;
		rads=rads(Rotation);
		image = Image;
		team = Team;
	}
	protected double rads(double rotation)
	{
		return rotation/r2d;
	}
	protected double rotation(double radians)
	{
		return radians*r2d;
	}
}
