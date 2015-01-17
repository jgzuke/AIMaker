package game;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public final class View extends JPanel implements ActionListener
{
	Timer timer;
	WallController wallController;
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
}