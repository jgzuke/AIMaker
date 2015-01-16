package game;
import javax.swing.*;
class Main {
	public static void main(String [] args){
		JFrame jf=new JFrame("Graphics Panel");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setContentPane(new View());
		jf.pack();
		jf.setVisible(true);
	}
}