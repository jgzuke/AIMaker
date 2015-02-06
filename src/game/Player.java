package game;

/*
 * Controlls movements nd rotation of view
 */
public final class Player
{
	protected int height = 190; //cm
	protected int x = -200;
	protected int y = 0;
	protected int z = 190; //height of eyes
	protected double hRotation = 0; //looking directly along x axis , -ve is left
	protected double zRotation = 0; //looking directly forward, -ve is down
	protected double tiltRotation = 0;
	protected int moving = 0;	/* -2: left, -1: back, 0: none, 1: forward, 2: right*/
	private Controller control;
	public Player(Controller controlSet)
	{
		control = controlSet;
	}
	/**
	 * returns players x, y, z
	 * @return x, y, z coordinates
	 */
	protected double[] getLocation()
	{
		double[] location = {x, y, z};
		return location;
	}
	/**
	 *  returns players horizontal and vertical rotation
	 * @return horizontal and vertical rotation
	 */
	protected double[] getRotation()
	{
		double[] rotation = {hRotation, zRotation};
		return rotation;
	}
	double zVel = 0; // for jumping
	double k = 0.1;
	/**
	 * what actions player takes every frame
	 */
	protected void frameCall()
	{
		if(zRotation > Math.PI/3) zRotation = Math.PI/3;
		if(zRotation > Math.PI/3) zRotation = Math.PI/3;
		z += zVel * 10;
		zVel -= 0.1;
		if(z < 190)
		{
			z = 190;
			zVel = 0;
		}
		switch(moving)
		{
		case -2:
			x += Math.sin(hRotation)*5;
			y -= Math.cos(hRotation)*5;
			break;
		case -1:
			x -= Math.cos(hRotation)*5;
			y -= Math.sin(hRotation)*5;
			break;
		case 1:
			x += Math.cos(hRotation)*5;
			y += Math.sin(hRotation)*5;
			break;
		case 2:
			x -= Math.sin(hRotation)*5;
			y += Math.cos(hRotation)*5;
			break;
		}
	}
	public boolean clickedMove(float x, float y)
	{
		return (x < 100 && y > 0);
	}
}