package game;

public final class Archer1 extends Archer {

	public Archer1(Packet p){super(p);}
	/*
	 * AVALIABLE ACTIONS
	 * run(i);	runs forward for i frames
	 * roll(); rolls forward
	 * 
	 * 
	 * 
	 * AVALIABLE VARIABLES
	 * 
	 * 
	 * UTILITY FUNCTIONS
	 * checkHitBack(x, y, grounded)
	 * checkObstructionsPoint(x, y, x2, y2, grounded, expand)
	 * checkDistance(x, y, x2, y2)
	 */
	
	@Override
	protected void chooseAction() {
		run(4);
	}
	
	@Override
	protected void shooting() {
		
	}

	@Override
	protected void endShot() {
		
	}

	@Override
	protected void justShot() {
		
	}

	@Override
	protected void endRun() {
		
	}

	@Override
	protected void running() {
		
	}
}
