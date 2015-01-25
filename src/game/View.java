package game;
import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
public final class View extends JPanel implements ActionListener
{
	Timer timer;
	WallController wallController;
	SpriteController spriteController;
	SoundController soundController;
	ControlAccess controlAccess;
	ImageLibrary imageLibrary;
	int levelWidth = 600;
	int levelHeight = 600;
	public View()
	{
		setFocusable(true);
		requestFocusInWindow();
		setBackground(Color.blue);
		setSize(new Dimension(levelWidth, levelHeight));
		setMinimumSize(new Dimension(levelWidth, levelHeight));
		setPreferredSize(new Dimension(levelWidth, levelHeight));
		imageLibrary = new ImageLibrary();
		
		wallController = new WallController(this);
		spriteController = new SpriteController(this);
		soundController = new SoundController(this);
		controlAccess = new ControlAccess(this);
		spriteController.startRound();
		wallController.loadLevel(0);
		timer = new Timer(50, this);
		timer.start();
	}
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		for(int i = 0; i<=levelWidth/100; i++)
		{
			for(int j = 0; j<=levelWidth/100; j++)
			{
				g.drawImage(imageLibrary.backTile, i*100, j*100, null);
			}
		}
		g.drawImage(imageLibrary.backBot, 0, 0, null);
		Graphics2D g2d = (Graphics2D) g;
		for(int i = 0; i < spriteController.enemies.size(); i++)
		{
			drawEnemy(spriteController.enemies.get(i), g2d);
		}
		for(int i = 0; i < spriteController.shots.size(); i++)
		{
			drawShot(spriteController.shots.get(i), g2d);
		}
		for(int i = 0; i < spriteController.aoes.size(); i++)
		{
			drawAOE(spriteController.aoes.get(i), g2d);
		}
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		
		int minX;
		int minY;
		Enemy e;
		for(int i = 0; i < spriteController.enemies.size(); i++)
		{
			e=spriteController.enemies.get(i);
			if(e != null)
			{
					minX = (int) e.getX() - 20;
					minY = (int) e.getY() - 30;
					g.setColor(Color.RED);
					g.fillRect(minX, minY, (int)(40*e.getHealthFraction()), 10);
					g.setColor(Color.BLACK);
					g.drawRect(minX, minY, 40, 10);
			}
		}
		
		g.drawImage(imageLibrary.backTop, 0, 0, null);
	}
	
	private void drawEnemy(Enemy s, Graphics2D g)
	{
		BufferedImage image = null;
		switch(s.getType())
		{
		case 0:
			image = imageLibrary.archer[s.frame];
			break;
		case 1:
			image = imageLibrary.mage[s.frame];
			break;
		case 2:
			image = imageLibrary.swordsman[s.frame];
			break;
		}
		drawRotated(s.getX(), s.getY(), s.getRotation(), image, g);
	}
	private void drawShot(Shot s, Graphics2D g)
	{
		BufferedImage image = imageLibrary.shot[s.getTeam()][(int)(Math.random()*5)];
		drawRotated(s.getX(), s.getY(), s.getRotation(), image, g);
	}
	private void drawRotated(int x, int y, int r, BufferedImage image, Graphics2D g)
	{
		AffineTransform at = new AffineTransform();
        at.translate(x, y);
        at.rotate(Math.toRadians(r));
        at.translate(-image.getWidth()/2, -image.getHeight()/2);
        g.drawImage(image, at, null);
	}
	private void drawAOE(AOE s, Graphics2D g)
	{
		int r = s.getRadius();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, s.getAlpha()));
		g.drawImage(imageLibrary.aoe[s.getTeam()], s.getX()-(r/2), s.getY()-(r/2), r, r, null);
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		spriteController.frameCall();
		wallController.frameCall();
		repaint();
	}
	protected double getRandomDouble()
	{
		return 0.5; //TODO
	}
	protected int getRandomInt(int i)
	{
		return i; //TODO
	}
}