package game;

import java.awt.image.BufferedImage;

public abstract class Sprite
{
	private double x;
	private double y;
	private int frame = 0;
	protected final double r2d = 180 / Math.PI;
	protected double rads;
	private double rotation;
	private boolean deleted = false;
	private BufferedImage image;
	private BufferedImage [] images;
	private byte team;
	public Sprite(double X, double Y, double Rotation, BufferedImage [] Images, byte Team)
	{
		x=X;
		y=Y;
		rotation = Rotation;
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
	protected int getX() { return (int)x; }
	protected int getY() { return (int)y; }
	protected int getFrame() { return (int)frame; }
	protected int getRotation() { return (int)rotation; }
	protected byte getTeam() { return team; }
	protected BufferedImage getImage() { return image; }
	protected boolean getDeleted() { return deleted; }
	protected void delete() { deleted=true; }
	
}
