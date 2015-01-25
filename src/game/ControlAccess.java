package game;

import java.util.ArrayList;

public final class ControlAccess
{
	private View control;
	private SoundController soundController;
	private SpriteController spriteController;
	private WallController wallController;
	protected ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	protected ArrayList<AOE> aoes = new ArrayList<AOE>();
	protected ArrayList<Shot> shots = new ArrayList<Shot>();
	public ControlAccess(View Control)
	{
		control = Control;
		soundController = control.soundController;
		wallController = control.wallController;
		spriteController = control.spriteController;
		enemies = spriteController.enemies;
		aoes = spriteController.aoes;
		shots = spriteController.shots;
	}
	
	public int getLevelWidth()
	{
		return control.levelWidth;
	}
	public int getLevelHeight()
	{
		return control.levelHeight;
	}
	protected void playEffect(String effect)
	{
		soundController.playEffect(effect);
	}
	public boolean checkHitBack(double X, double Y, boolean objectOnGround)
	{
		return wallController.checkHitBack(X, Y, objectOnGround);
	}
	public boolean checkObstructionsPoint(float x1, float y1, float x2, float y2, boolean objectOnGround, int expand)
	{
		return wallController.checkObstructionsPoint(x1, y1, x2, y2, objectOnGround, expand);
	}
	public boolean checkObstructions(double x1, double y1, double rads, int distance, boolean objectOnGround, int offset)
	{
		return wallController.checkObstructions(x1, y1, rads, distance, objectOnGround, offset);
	}
	protected void createShot(double rotation, double x, double y, byte Team)
	{
		//spriteController.createShot(rotation, x, y, Team);
	}
}