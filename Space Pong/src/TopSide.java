import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class TopSide extends JPanel {
	private GamePlay play;
	private JTextField time;
	private JTextField level;
	private JTextField live;
	private JTextField score;
	public TopSide(GamePlay play) {
		this.play = play;
		live = new JTextField(String.format("Lives: %s", play.getHealth()),10);
		live.setEditable(false);
		live.setFocusable(false);
		score = new JTextField(String.format("Score: %s", play.getScore()),10);
		score.setEditable(false);
		score.setFocusable(false);
		time = new JTextField(String.format("Time: %ss", play.getTime()),10);
		time.setEditable(false);
		time.setFocusable(false);
		level = new JTextField(String.format("Time: %ss", play.getLevel()),10);
		level.setEditable(false);
		level.setFocusable(false);
		live.setBackground(Color.yellow);
		score.setBackground(Color.yellow);
		time.setBackground(Color.yellow);
		level.setBackground(Color.yellow);
		this.add(live);
		this.add(score);
		this.add(time);
		this.add(level);
	}
	public void update() {
		live.setText(String.format("Lives: %s", play.getHealth()));
		score.setText(String.format("Score: %s", play.getScore()));
		time.setText(String.format("Time: %ss", play.getTime()));
		level.setText(String.format("Level: %s", play.getLevel()));
	}
}
