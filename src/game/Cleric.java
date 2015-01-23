package game;


public abstract class Cleric extends Enemy {

	public Cleric(Packet p){super(p);}
	
	@Override
	protected void frameCall()
	{
		chooseAction();
	}
}
