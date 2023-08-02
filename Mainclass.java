package demogame;

import javax.swing.JFrame;

public class Mainclass {

	public static void main(String[] args) {
		
		JFrame f=new JFrame();
		f.setTitle("Brick Beaker");
		f.setSize(700,630);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.setResizable(false);
		
		GamePlay gameplay=new GamePlay();
		f.add(gameplay);
	}

}