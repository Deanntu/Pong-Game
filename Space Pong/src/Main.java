
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;


public class Main {

	public static void main(String[] args) {
		
		JFrame obj = new JFrame();

		GamePlay play = new GamePlay();
		BotSide bot = new BotSide(play);
		TopSide top = new TopSide(play);
		bot.setFocusable(false);
		play.setBot(bot);
		top.setFocusable(false);
		play.setTop(top);
		
		obj.setBounds(0, 0, 1024, 768);
		obj.setResizable(false);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(top,BorderLayout.NORTH);
		obj.add(play,BorderLayout.CENTER);
		obj.add(bot,BorderLayout.SOUTH);
		obj.setVisible(true);
	}

}
