package game;

public abstract class Mage extends Enemy {
	public Mage(Packet p){super(p);}
	@Override
	protected void frameCall()
	{
		chooseAction();
	}
}
