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
	public void playEffect(String effect)
	{
		//TODO
	}
}