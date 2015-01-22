package game;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
public final class View extends JPanel implements ActionListener
{
	Timer timer;
	WallController wallController;
	SpriteController spriteController;
	ImageLibrary imageLibrary;
	double levelWidth = 800;
	double levelHeight = 800;
	private BufferedImage backBot;
	private BufferedImage backTop;
	private ImageObserver imageObserver;
	public View()
	{
		setFocusable(true);
		requestFocusInWindow();
		setBackground(Color.blue);
		setSize(new Dimension(800, 800));
		setMinimumSize(new Dimension(800, 800));
		setPreferredSize(new Dimension(800, 800));
		wallController = new WallController(this);
		spriteController = new SpriteController(this);
		imageLibrary = new ImageLibrary();
		backBot = imageLibrary.loadImage("level1");
		backTop = imageLibrary.loadImage("leveltop1");
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
		/*g.drawImage(backBot, 0, 0, imageObserver);
		spriteController.drawSprites(g, imageObserver);
		g.drawImage(backTop, 0, 0, imageObserver);*/
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