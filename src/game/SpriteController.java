package game;

import java.util.ArrayList;

import team1.Archer1;
import team1.Mage1;
import team1.Swordsman1;
import team2.Archer2;
import team2.Mage2;
import team2.Swordsman2;

public final class SpriteController
{
	/**
	 * Initializes all undecided variables, loads level, creates player and enemy objects, and starts frameCaller
	 */
	protected ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	protected ArrayList<AOE> aoes = new ArrayList<AOE>();
	protected ArrayList<Shot> shots = new ArrayList<Shot>();
	private View control;
	public SpriteController(View Control)
	{
		control = Control;
	}
	protected void startRound()
	{
		makeEnemy(0, 50, 260, 0, 0);
		makeEnemy(0, control.getLevelWidth()-50, 260, 180, 1);
		makeEnemy(0, control.getLevelWidth()-50, 340, 180, 1);
		makeEnemy(0, 50, 340, 0, 0);
		
		makeEnemy(2, control.getLevelWidth()-100, 280, 180, 1);
		makeEnemy(2, 100, 280, 0, 0);
		makeEnemy(2, 100, 320, 0, 0);
		makeEnemy(2, control.getLevelWidth()-100, 320, 180, 1);
		
		makeEnemy(1, control.getLevelWidth()-40, 300, 180, 1);
		makeEnemy(1, 40, 300, 0, 0);
		
	}
	protected void makeEnemy(int type, int x, int y, int r, int t)
	{
		Packet p = new Packet(x, y, r, (byte)t, (byte)type, control.controlAccess);
		switch(t)
		{
		case 0:
			switch(type)
			{
			case 0:
				enemies.add(new Archer1(p)); //x, y, hp, sick, type is ImageIndex
				break;
			case 1:
				enemies.add(new Mage1(p));
				break;
			case 2:
				enemies.add(new Swordsman1(p));
				break;
			}
			break;
		case 1:
			switch(type)
			{
			case 0:
				enemies.add(new Archer2(p)); //x, y, hp, sick, type is ImageIndex
				break;
			case 1:
				enemies.add(new Mage2(p));
				break;
			case 2:
				enemies.add(new Swordsman2(p));
				break;
			}
			break;
		}
	}
	/**
	 * creates a player power ball
	 * @param rotation rotation of bolt
	 * @param xVel horizontal velocity of bolt
	 * @param yVel vertical velocity of bolt
	 * @param power power of bolt
	 * @param x x position
	 * @param y y position
	 */
	protected void createShot(double rotation, double x, double y, byte Team)
	{
		shots.add(new Shot(x, y, rotation, Team, control, enemies));
	}
	protected void frameCall()
	{
		for(int i = 0; i < aoes.size(); i++)
		{
			if(aoes.get(i).getDeleted()) aoes.remove(i);
			else aoes.get(i).frameCall();
		}
		for(int i = 0; i < shots.size(); i++)
		{
			if(shots.get(i).getDeleted()) shots.remove(i);
			else shots.get(i).frameCall();
		}
		int [] count = new int [4];
		for(int i = 0; i < enemies.size(); i++)
		{
			if(enemies.get(i).getDeleted())
			{
				enemies.remove(i);
				i--;
			} else
			{
				enemies.get(i).enemyFrame();
				count[enemies.get(i).getTeam()]++;
			}
		}
		int teamsAlive = -2;
		for(int i = 0; i < 4; i++)
		{
			if(count[i] != 0)
			{
				if(teamsAlive==-2) teamsAlive = i;
				else teamsAlive = -1;
			}
		}
		if(teamsAlive!=-1)
		{
			if(teamsAlive == -2) restartGame(-2);
			else restartGame(teamsAlive);
		}
	}
	protected void restartGame(int teamsAlive)
	{
		enemies.clear();
		shots.clear();
		aoes.clear();
		startRound();
		if(teamsAlive != -2) control.wins[teamsAlive]++;
		control.label1.setText(Integer.toString(control.wins[0]) + ":" + Integer.toString(control.wins[1]));
	}
	/**
	 * creates an emeny AOE explosion
	 * @param x x position
	 * @param y y position
	 * @param power power of explosion
	 * @param damaging whether it damages player
	 */
	protected void createAOE(double x, double y, byte Team)
	{
		aoes.add(new AOE(x, y, Team, enemies));
	}
	protected void createSafeAOE(double x, double y, byte Team)
	{
		aoes.add(new AOE(x, y, Team));
	}
}