import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BotSide extends JPanel implements ActionListener {
	private GamePlay play;
	private JButton playBut = new JButton("Play");
	private JButton reset = new JButton("Reset");
	private JButton theme = new JButton("Come to Dark Side");
	private JButton concept = new JButton("UFO");
	private JButton gameMode = new JButton("Normal");

	public BotSide(GamePlay play) {
		this.play = play;
		
		playBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(play.isPlay()) play.setPlay(false);
				else if (play.getHealth() <= 0){
					play.restart();
				}
				else {
					play.setPlay(true);
				}
			}
		});
		playBut.setBackground(Color.yellow);
		playBut.setFocusable(false);
		
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play.restart();
			}
		});
		reset.setBackground(Color.yellow);
		reset.setFocusable(false);
		
		
		theme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play.changeColorTheme();
				if(play.getTheme() == "Dark") {
					theme.setText("Come to Light Side");
				}
				else {
					theme.setText("Come to Dark Side");
				}
			}
		});
		theme.setBackground(Color.yellow);
		theme.setFocusable(false);
		
		
		concept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play.changeGameConcept();
				if(play.getConcept() == "tooth") {
					concept.setText("Tooth");
				}
				else if (play.getConcept() == "covid") {
					concept.setText("Corona");
				}
				else {
					concept.setText("UFO");
				}
			}
		});
		concept.setBackground(Color.yellow);
		concept.setFocusable(false);
		
		gameMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(play.getGameMode() == "Normal") {
					play.setGameMode("Hard");
					gameMode.setText("Hard");
				}
				else {
					play.setGameMode("Normal");
					gameMode.setText("Normal");
				}
				play.restart();
				
			}
		});
		gameMode.setBackground(Color.yellow);
		gameMode.setFocusable(false);
		this.add(concept);
		this.add(theme);
		this.add(playBut);
		this.add(gameMode);
		this.add(reset);
		
	}
	public void update() {
		if (play.isPlay()) {
			playBut.setText("Pause");
		}
		else {
			playBut.setText("Play");
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
