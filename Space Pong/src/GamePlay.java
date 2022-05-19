import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;
public class GamePlay extends JPanel implements KeyListener, ActionListener{
	Random random = new Random();
	// components of player
	private int playerX = 452;
	private int playerY = 654;
	private int health = 3;
	private boolean play = false;
	private int level = 1;
	private int score = 0;
	// components about time / repetition
	private int time = 60;
	private int count; // helper for int time
	private Timer timer;
	private int delay = 20;
	// components of ball
	private int ballX = 10;
	private int ballY = 10;
	private double ballXSpeed = 4.0*Math.pow(1.5, level-1); // effects ball start speed
	private double ballYSpeed = 1.0*Math.pow(1.5, level-1);
	private int ballRadius = 20;
	
	// components of world & stellars;
	private double gravity = 9.8;
	// components of ufo;
	private Image ufo;
	private int ufoX;
	private int ufoY;
	private int ufoRadiusX;
	private int ufoRadiusY;
	private Boolean ufoLives = false;
	// components of additional health
	private Image hp;
	private int hpX;
	private int hpY;
	private int hpRadiusX;
	private int hpRadiusY;
	private boolean hpLives = false;
	//components of star
	private Image star;
	private int starX;
	private int starY;
	private int starRadiusX;
	private int starRadiusY;
	private Boolean starLives = false;
	//components of meteor
	private Image meteor;
	private int meteorX;
	private int meteorY;
	private int meteorRadiusX;
	private int meteorRadiusY;
	private Boolean meteorLives = false;
	// Settings
	private Color background = Color.white;
	private Color paddle = Color.black;
	private String concept = "UFO";
	private String theme = "Bright";
	private String gameMode = "Normal";
	//Other parts of game
	private TopSide top;
	private BotSide bot;
	// random


	public GamePlay() {
		createObjects(concept);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	
	private void createObjects(String s) {
		if (s.equals("tooth")) {
			hp = createImg("doctor.png");
			ufo = createImg("candy.png");
			star = createImg("tooth.png");
			meteor = createImg("toothbrush.png");
		}
		else if (s.equals("covid")) {
			hp = createImg("doctor.png");
			ufo = createImg("virus.png");
			star = createImg("mask.png");
			meteor = createImg("vaccine.png");

		}
		else {
			hp = createImg("hp_small.png");
			ufo = createImg("ufoship_small.png");
			star = createImg("star_small.png");
			meteor = createImg("meteorite_small.png");
		}
		hpRadiusX = hp.getWidth(this);
		hpRadiusY = hp.getHeight(this);
		ufoRadiusX = ufo.getWidth(this);
		ufoRadiusY = ufo.getHeight(this);
		starRadiusX = star.getWidth(this);
		starRadiusY = star.getHeight(this);
		meteorRadiusX = meteor.getWidth(this);
		meteorRadiusY = meteor.getHeight(this);
	}

	public void paint(Graphics g) {
		// background
		g.setColor(background);
		g.fillRect(0, 0, 1024, 768);
		// the paddle
		g.setColor(paddle );
		g.fillRect(playerX, playerY, 120, 10);
		// the ball
		g.setColor(Color.red);
		g.fillOval(ballX, ballY, ballRadius, ballRadius);
		// the ufo
		if (ufoLives) {
			g.drawImage(ufo,ufoX,ufoY,this);
		}
		// additional star
		if (starLives) {
			g.drawImage(star,starX,starY,this);
		}
		// meteor
		if (meteorLives) {
			g.drawImage(meteor,meteorX,meteorY,this);
		}
		// hp
		if (hpLives) {
			g.drawImage(hp,hpX,hpY,this);
		}
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(bot != null) {
			bot.update();
		}
		if(top != null) {
			top.update();
		}
		repaint();
		
		if (play && health > 0) {
			calculateTime();
			
			ballYSpeed += gravity*(((double)timer.getDelay())/3000.0);
			// bouncing
			if (new Rectangle (ballX,ballY,ballRadius,ballRadius)
					.intersects(new Rectangle(playerX,playerY, 120, 10)) && ballYSpeed >0) {
				ballYSpeed = -ballYSpeed;
				score += 15;
			}
			ballX += ballXSpeed;
			ballY += ballYSpeed;
			
			// ball movements caused by borders
			if (ballX < 0 && ballXSpeed < 0) ballXSpeed = -ballXSpeed;
			if (ballY < 0 && ballYSpeed < 0) ballYSpeed = -ballYSpeed;
			if (ballX > 1014-2*ballRadius && ballXSpeed >0) ballXSpeed = -ballXSpeed;
			// hp loses
			if (ballY > 730) {
				reducehp();
			}
			intersectsBall();
			//------CREATE PART----------
			// create ufo
			createUFO();
			// create hp
			createHP();
			// create star
			createStar();
			// create meteor
			createMeteor();
						
		}
	}
	
	private Image createImg(String name) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("src/TermProject/"+name));
			
		} catch (IOException e) {
		}
		return img;
	}
	private void calculateTime() {
		this.count++;
		if (count%(1000/timer.getDelay()) == 0) {
			this.time--;
		}
		if (time == 0) {
			levelUp(gameMode);
		}
	}

	private void levelUp(String s) {
		this.time = 60;
		level++;
		if(gameMode == "Normal") { //if stop for each level up
			clearStellars();
			ballReset();
			this.playerX = 452;
			play = false;
		}
		if(gameMode == "Hard") {
			this.ballXSpeed *= 1.5;
			this.ballYSpeed *= 1.5;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {
		if(health>0){
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if(playerX >= 865) playerX = 895;
				else {
					moveRight();
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				if(playerX <= 30) playerX = 0;
				else {
					moveLeft();
				}
			}
		}
	}
	// Movement Helpers
	private void moveLeft() {
		play = true;
		playerX -= 30;
	}

	private void moveRight() {
		play = true;
		playerX += 30;
		
	}

	private void reducehp() {
		if (health == 1) {
			health--;
			play = false;
		}
		if (health > 1) {
			health--;
			ballReset();
		}
	}
	
	// Change Theme to Dark / Bright
	protected void changeColorTheme() {
		if (theme  == "Bright")	{
			background = Color.black;
			paddle = Color.white;
			bot.setBackground(Color.black);
			top.setBackground(Color.black);
			theme = "Dark";
		}
		else {
			background = Color.white;
			paddle = Color.black;
			bot.setBackground(Color.white);
			top.setBackground(Color.white);
			theme  = "Bright";
		}
	}
	// Change Concept 
	protected void changeGameConcept() {
		if (concept == "UFO") {
			restart();
			concept = "tooth";
			createObjects(concept);
			
		}
		else if (concept == "tooth") {
			restart();
			concept = "covid";
			createObjects(concept);
			
		}
		else {
			restart();
			concept = "UFO";
			createObjects(concept);
			
		}
		
	}
	protected void restart() {
		play = false;
		clearStellars();
		levelAndTimeReset();
		ballReset();
		playerReset();

	}
	
	// Restart helpers
	private void clearStellars() {
		this.meteorLives = false;
		this.hpLives = false;
		this.starLives = false;
		this.ufoLives = false;
	}
	private void levelAndTimeReset() {
		this.level = 1;
		this.time = 60;
		this.count = 0;
	}
	private void ballReset() {
		this.ballX = 10;
		this.ballY = 10;
		this.ballXSpeed = 4*Math.pow(1.5, level-1); // effects ball start speed
		this.ballYSpeed = 1*Math.pow(1.5, level-1);
	}
	private void playerReset() {
		this.playerX = 452;
		this.health = 3;
		this.score = 0;
	}
	// Intersect Checker
	private void intersectsBall() {
		Rectangle ball = new Rectangle (ballX,ballY,ballRadius,ballRadius);
		Rectangle meteor = new Rectangle(meteorX,meteorY,meteorRadiusY,meteorRadiusX);
		Rectangle star = new Rectangle (starX,starY,starRadiusY,starRadiusX);
		Rectangle health = new Rectangle (hpX,hpY,hpRadiusY,hpRadiusX);
		Rectangle ufo = new Rectangle (ufoX, ufoY, ufoRadiusY, ufoRadiusX);
		//ufo-ball collapse
		if (ufoLives && ball.intersects(ufo)) {
			reducehp();
			ufoLives = false;
		}
		// hp gains
		if (hpLives && ball.intersects(health)) {
			this.health++;
			hpLives = false;
		}
		// ball hit to star (get point)
		if (starLives && ball.intersects(star)) {
			score += 100;
			starLives = false;
		}
		// ball hit to meteor
		if (meteorLives && ball.intersects(meteor)) {
			ballXSpeed *= -1.1;
			ballYSpeed *= -1.1;
			meteorLives = false;	
		}
	}
	//Creators
	private void createHP() {
		if (!hpLives && health < 3 && ballY > 450) {
			Rectangle temp = new Rectangle(hpX,hpY,hpRadiusY,hpRadiusX);
			do
			{
				hpX = random.nextInt(1008-hpRadiusX);
				hpY = random.nextInt(400);
				temp = new Rectangle(hpX,hpY,hpRadiusY,hpRadiusX);
			}
			while ((meteorLives &&  new Rectangle(meteorX,meteorY,meteorRadiusY,meteorRadiusX).intersects(temp)) || 
					(ufoLives && new Rectangle(ufoX, ufoY, ufoRadiusY, ufoRadiusX).intersects(temp) || 
					(starLives && new Rectangle(starX,starY,starRadiusY,starRadiusX).intersects(temp)))); 
			hpLives = true;
		}
	}
	private void createStar() {
		if (!starLives && ballY > 450) {
			Rectangle temp = new Rectangle(starX,starY,starRadiusY,starRadiusX);
			do
			{
				starX = random.nextInt(1008-starRadiusX);
				starY = random.nextInt(400);
				temp = new Rectangle(starX,starY,starRadiusY,starRadiusX);
			}
			while ((meteorLives &&  new Rectangle(meteorX,meteorY,meteorRadiusY,meteorRadiusX).intersects(temp)) || 
					(ufoLives && new Rectangle(ufoX, ufoY, ufoRadiusY, ufoRadiusX).intersects(temp) || 
					(hpLives && new Rectangle(hpX,hpY,hpRadiusY,hpRadiusX).intersects(temp))));
			starLives = true;
		}
	}
	private void createMeteor() {
		if (!meteorLives && ballY > 450) {
			Rectangle temp = new Rectangle(meteorX,meteorY,meteorRadiusY,meteorRadiusX);
			do
			{
				meteorX = random.nextInt(1008-meteorRadiusX);
				meteorY = random.nextInt(400);
				temp = new Rectangle(meteorX,meteorY,meteorRadiusY,meteorRadiusX);
			}
			while ((starLives &&  new Rectangle(starX,starY,starRadiusY,starRadiusX).intersects(temp)) || 
					(ufoLives && new Rectangle(ufoX, ufoY, ufoRadiusY, ufoRadiusX).intersects(temp) || 
							(hpLives && new Rectangle(hpX,hpY,hpRadiusY,hpRadiusX).intersects(temp))));
			meteorLives = true;
		}
	}
	private void createUFO() {
		if (!ufoLives && ballY > 450) {
			Rectangle temp = new Rectangle(ufoX, ufoY, ufoRadiusY, ufoRadiusX);
			do
			{
					ufoX = random.nextInt(1008-ufoRadiusX);
					ufoY = random.nextInt(400);
					temp = new Rectangle(ufoX, ufoY, ufoRadiusY, ufoRadiusX);
			}
			while ((meteorLives &&  new Rectangle(meteorX,meteorY,meteorRadiusY,meteorRadiusX).intersects(temp)) || 
					(starLives &&  new Rectangle(starX,starY,starRadiusY,starRadiusX).intersects(temp) || 
							(hpLives && new Rectangle(hpX,hpY,hpRadiusY,hpRadiusX).intersects(temp))));
			ufoLives = true;
		}
	}
	
	// SETTERS & GETTERS
	public TopSide getTop() {
		return top;
	}

	public void setTop(TopSide top) {
		this.top = top;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public BotSide getBot() {
		return bot;
	}

	public void setBot(BotSide bot) {
		this.bot = bot;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
	public boolean isPlay() {
		return play;
	}

	public void setPlay(boolean play) {
		this.play = play;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	public String getGameMode() {
		return gameMode;
	}
	public void setGameMode(String gameMode) {
		this.gameMode = gameMode;
	}
}
