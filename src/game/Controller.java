package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/*
 * Control runs frames, controls graphics redraw, etc
 * 
 */
public final class Controller implements ActionListener
{
	Timer timer;
	protected Graphics graphics;
	protected Player player;
	protected Objects objects;
	/** 
	 * Initializes all undecided variables, loads level, creates player and enemy objects, and starts frameCaller
	 */
	public Controller(double[] dimensions)
	{
		player = new Player(this);
		objects = new Objects(this);
		graphics = new Graphics(this, dimensions);
		timer = new Timer(40, this);
		timer.start();
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		repaint();
	}
}