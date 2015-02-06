package game;
import java.awt.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public final class Drawer extends JPanel
{
	private int levelWidth = 600;
	private int levelHeight = 600;
	protected int[] wins = {0, 0};
	private double sliderValue;
	private Controller control;
	protected JLabel score, level, label1;
	public Drawer(Controller ControlSet)
	{
		setFocusable(true);
		requestFocusInWindow();
		setBackground(Color.blue);
		setSize(new Dimension(levelWidth, levelHeight));
		setMinimumSize(new Dimension(levelWidth, levelHeight));
		setPreferredSize(new Dimension(levelWidth, levelHeight));
		control = ControlSet;
	}
	protected int getLevelWidth()
	{
		return levelWidth;
	}
	protected int getLevelHeight()
	{
		return levelHeight;
	}
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	public void frameCall()
	{
		repaint();
	}
}