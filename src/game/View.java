package game;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public final class View extends JPanel implements ActionListener
{
	Timer timer;
	WallController wallController;
	SpriteController spriteController;
	double levelWidth = 500;
	double levelHeight = 500;
	public View()
	{
		setFocusable(true);
		requestFocusInWindow();
		setBackground(Color.gray);
		setSize(new Dimension(500, 500));
		setMinimumSize(new Dimension(500, 500));
		setPreferredSize(new Dimension(500, 500));
		timer = new Timer(50, this);
		timer.start();
	}
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
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