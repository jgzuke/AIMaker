package game;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public final class View extends JPanel implements ActionListener
{
	Timer timer;
	public View()
	{
		setFocusable(true);
		requestFocusInWindow();
		setBackground(Color.blue);
		setSize(new Dimension(500, 500)); // Set window size
		setMinimumSize(new Dimension(500, 500));
		setPreferredSize(new Dimension(500, 500));
		timer = new Timer(50, this); // Action timer
		timer.start();
	}
	// draws everything on the screen, called every action
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		repaint(); // calls paintComponent
	}
}