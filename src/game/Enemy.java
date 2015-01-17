package game;

public class Enemy
{
	public double x;
	public double y;
	public double rotation;
	public int width;
	public int height;
	public boolean isVideo = false;
	public int frame = 0;
	public boolean playing = false;
	//TODO public Bitmap image = null;
	public boolean visible = true;
	public boolean deleted = false;
	protected int hp;
	protected int hpMax;
	protected double r2d = 180 / Math.PI;
	protected double rads;
	protected double speedCur;
	protected boolean hitBack;
	protected boolean thisPlayer = false;
	protected View control;
	
	protected void frameCall()
	{
		if(hp > hpMax)
		{
			hp = hpMax;
		}
	}
	protected void getHit(double damage)
	{
		hp -= damage*2;
		if(hp < 1)
		{
			hp = 0;
			deleted = true;
		}
	}
}
