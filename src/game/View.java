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
		timer = new Timer(50, this);
		timer.start();
	}
	public void playEffect(String effect)
	{
		//TODO
	}
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		for(int i = 0; i<8; i++)
		{
			for(int j = 0; j<8; j++)
			{
				g.drawImage(imageLibrary.backTile, i*100, j*100, null);
			}
		}
		g.drawImage(imageLibrary.backBot, 0, 0, null);
		for(int i = 0; i < spriteController.enemies.size(); i++)
		{
			drawRotated(spriteController.enemies.get(i), g);
		}
		for(int i = 0; i < spriteController.aoes.size(); i++)
		{
			drawRotated(spriteController.aoes.get(i), g);
		}
		for(int i = 0; i < spriteController.shots.size(); i++)
		{
			drawRotated(spriteController.shots.get(i), g);
		}
		g.drawImage(imageLibrary.backTop, 0, 0, null);
	}
	private void drawRotated(Sprite s, Graphics g)
	{
		double rot = Math.toRadians(s.rotation);
		double x = s.image.getWidth() / 2;
		double y = s.image.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rot, x, y);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		g.drawImage(op.filter(s.image, null), (int)s.x, (int)s.y, null);
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