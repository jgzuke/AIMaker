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
public final class View extends JPanel implements ActionListener
{
	Timer timer;
	WallController wallController;
	SpriteController spriteController;
	SoundController soundController;
	ControlAccess controlAccess;
	ImageLibrary imageLibrary;
	private int levelWidth = 600;
	private int levelHeight = 600;
	private int[] wins = {0, 0};
	private double sliderValue;
	protected JLabel label1;
	protected JLabel label2;
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
		timer = new Timer(40, this);
		timer.start();
		
		
		label1 = new JLabel("0");
		label2 = new JLabel("0");
		final JSlider slider = new JSlider(JSlider.HORIZONTAL, 5, 200, 20);
		slider.setMinorTickSpacing(100);
	    slider.setMajorTickSpacing(200);
	    slider.setPaintTicks(true);
	    slider.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e)
	        {
	        	sliderValue = slider.getValue();
	        	timer.setDelay(1000/slider.getValue());
	        }
	      });
	    add(label1, BorderLayout.SOUTH);
	    add(slider, BorderLayout.SOUTH);
	    add(label2, BorderLayout.SOUTH);
	}
	protected int getLevelWidth()
	{
		return levelWidth;
	}
	protected int getLevelHeight()
	{
		return levelHeight;
	}
	protected void restartGame(int teamsAlive)
	{
		wins[teamsAlive]++;
		label1.setText(Integer.toString(wins[0]));
		label2.setText(Integer.toString(wins[1]));
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
		drawRotated(s.getX(), s.getY(), (int)s.getRotation(), image, g);
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
	double timeAccurate = 0;
	@Override
	public void actionPerformed(ActionEvent e)
	{
		timeAccurate += Math.pow(2, (sliderValue/10)-9);
		spriteController.frameCall();
		wallController.frameCall();
		while(timeAccurate>1)
		{
			spriteController.frameCall();
			wallController.frameCall();
			timeAccurate--;
		}
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