package game;
import java.awt.GridLayout;
import javax.swing.*;
class Main
{
	public static void main(String [] args){
		JFrame jf=new JFrame("Graphics Panel");
		jf.setLayout(new GridLayout(2, 1));
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Controller control = new Controller();
		jf.setContentPane(control.graphics);
		jf.pack();
		jf.setVisible(true);
	}
}