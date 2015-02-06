package game;

public final class Packet {
	protected double x, y, r;
	protected int h = 700;
	protected byte t;
	protected byte ty;
	protected ControlAccess c;
	public Packet(double X, double Y, double R, byte T, byte type, ControlAccess C)
	{
		x=X;
		y=Y;
		r=R;
		t=T;
		c=C;
		ty = type;
		switch(type)
		{
		case 0:
			h=1200;
			break;
		case 1:
			h=700;
			break;
		case 2:
			h=2500;
			break;
		}
	}
}
