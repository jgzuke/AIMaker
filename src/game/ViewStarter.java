package game;
import java.awt.GridLayout;

import javax.swing.*;
class Main {
	public static void main(String [] args){
		JFrame jf=new JFrame("Graphics Panel");
		jf.setLayout(new GridLayout(2, 1));
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setContentPane(new View());
		jf.pack();
		jf.setVisible(true);
	}
}