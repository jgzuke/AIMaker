package game;


public abstract class Cleric extends Enemy {

	public Cleric(Packet p){super(p);}
	
	@Override
	protected void frameCall()
	{
		chooseAction();
	}
	protected boolean canHeal(Enemy s)
	{
		return checkDistance(getX(), getY(), s.getX(), s.getY())<100;
	}
}
