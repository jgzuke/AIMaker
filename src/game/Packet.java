package game;

import java.util.ArrayList;

public final class Packet {
	protected double x, y, r;
	protected int h;
	protected byte t;
	protected byte ty;
	protected ArrayList<Enemy> e;
	protected ArrayList<AOE> a;
	protected ArrayList<Shot> s;
	public Packet(double X, double Y, double R, byte T, byte type, ArrayList<Enemy> E, ArrayList<AOE> A, ArrayList<Shot> S)
	{
		x=X;
		y=Y;
		r=R;
		t=T;
		e=E;
		a=A;
		s=S;
		ty = type;
		switch(type)
		{
		case 0:
			h=1700;
			break;
		case 1:
			h=700;
			break;
		case 2:
			h=700;
			break;
		case 3:
			h=3000;
			break;
		}
	}
}
