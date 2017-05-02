package pong;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Pong extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener
{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 600;
	
	static Clip track1, track2;	
	
	private Thread thread;
	static boolean isRunning = false;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	private Paddle paddle;
	private Paddle AIPaddle;
	private Ball ball, ball2;
	private Bumper topBumper, midBumper, bottomBumper;	
	
	static boolean firstBumper;
	static boolean secondBumper;
	static boolean thirdBumper;
	static boolean rightImpact, leftImpact;
	
	static boolean scored = false;
	static boolean PWins = false;
	static boolean AIWins = false;
	static boolean Pause = false;
	static boolean LVL1 = true;
	static boolean LVL2 = false;
	static boolean LVL3 = false;
	static boolean matchInProgress = false;
	static boolean musicStarted = false;
	
	public static void main(String[] args){
		JFrame frame = new JFrame("Pong");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(new Pong(), BorderLayout.CENTER);
		frame.pack();		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);		
	}
	
	public Pong(){
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		setFocusable(true);
		addKeyListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
		
		paddle = new Paddle(50, HEIGHT / 2 - 50, 40, 100);
		AIPaddle = new AIPaddle(WIDTH - 70, HEIGHT / 2 - 50, 40, 100);
		ball = new Ball(paddle.x + 19, paddle.y);
		//ball2 = new Ball(500, 300);
		topBumper = new Bumper(480,100,30,50);
		midBumper = new Bumper(480,300,30,50);
		bottomBumper = new Bumper(480,500,30,50);		
		
		start();		
	}	
	
	public void start(){
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void run(){
		long start, elapsed, wait;        
		
		while(isRunning){
			// this checks to see if music is playing and what the current level
//			if(!musicStarted && LVL1){
//				playMusic("C:/Users/Micah/Desktop/Do I See Color.WAV");
//				musicStarted = true;
//			}
//			// this checks to see if music is playing and what the current level
//			if(!musicStarted && LVL2){
//				playMusic("C:/Users/Micah/Desktop/Rise and Fall.WAV");
//				musicStarted = true;
//			}
//			// this checks to see if music is playing and what the current level
//			if(!musicStarted && LVL3){
//				playMusic("C:/Users/Micah/Desktop/Thunderclap.WAV");
//				musicStarted = true;
//			}				
				
			tick();
			repaint();
			// this checks to see if either the player or AI has scored
			if(scored){
				if(paddle.points == 5){						// this checks to see if the player has won the round
					if(LVL2){								// this checks to see if the current level is 2 if so it goes on to level 3
						//track1.stop();						// this stops the current music
						LVL3 = true;						// this is set to true so the player can advance to level 3
						LVL1 = false;						
						LVL2 = false;
						Pause = true;						// this provides a short pause between the last level and the next pressing Enter ends the pause
						PWins = true;						// this indicates that the Player has won the round
						matchInProgress = false;			// when this is false it displays text at the start of each round. After the first serve it turns true and the text is removed
						musicStarted = false;				// this is set to false so the music can start back up for the new level
						firstBumper = false;				// this resets the top bumper
						secondBumper = false;				// this resets the bottom bumper
						thirdBumper = false;				// this resets the middle bumper
						paddle.points = 0;					// this resets the player's points
						AIPaddle.points = 0;				// this resets the AI's points
						ball.StartSpeed = 15.0f;			// this sets the speed of the ball for Level 3
					}else{
						//track1.stop();						// this stops the current music
						LVL2 = true;						// this makes level 2 the current level
						LVL1 = false;
						LVL3 = false;
						Pause = true;						// this provides a short pause between the last level and the next pressing Enter ends the pause
						PWins = true;						// this indicates that the Player has won the round
						matchInProgress = false;			// when this is false it displays text at the start of each round. After the first serve it turns true and the text is removed
						musicStarted = false;				// this is set to false so the music can start back up for the new level
						firstBumper = false;				// this resets the top bumper
						secondBumper = false;				// this resets the bottom bumper
						thirdBumper = false;				// this resets the middle bumper
						paddle.points = 0;					// this resets the player's points
						AIPaddle.points = 0;				// this resets the AI's points
						ball.StartSpeed = 8.0f;				// this sets the speed of the ball for Level 2
					}
				}
				
				if(AIPaddle.points == 5){					// this checks to see if the AI has won the round
					LVL1 = true;							// When AI wins the player goes back to level 1
					LVL2 = false;
					LVL3 = false;
					Pause = true;							// this provides a short pause between the last level and the next pressing Enter ends the pause
					AIWins = true;							// this indicates that the AI has won the round
					matchInProgress = false;				// when this is false it displays text at the start of each round. After the first serve it turns true and the text is removed
					musicStarted = false;					// this is set to false so the music can start back up for the new level
					firstBumper = false;					// this resets the top bumper
					secondBumper = false;					// this resets the bottom bumper
					thirdBumper = false;					// this resets the middle bumper
					paddle.points = 0;						// this resets the player's points
					AIPaddle.points = 0;					// this resets the AI's points
					//track1.stop();							// this stops the current music
					playSound("src/pong/loser.wav");			// this is the sound file for when you lose
				}
				serveBall();								// this places the ball on either the Player's paddle or the AI's paddle
				try{
					Thread.sleep(2000);
				}catch (Exception e){}
				
				scored = false;								// this sets the score boolean back to false
			}
			start = System.nanoTime();			
							
			elapsed = System.nanoTime()	- start;
			wait = targetTime - elapsed / 1000000;
			if(wait <= 0) wait = 5;
			
			try{
				Thread.sleep(wait);
			}catch (Exception e){
				e.printStackTrace();
			}			
		}		
	}
	// this controls the animation for each item
	public void tick(){
		paddle.tick();
		AIPaddle.tick(ball);
		ball.tick(paddle, AIPaddle);
		//ball2.tick2(paddle, AIPaddle);
		topBumper.tick(ball);
		midBumper.tick(ball);
		bottomBumper.tick(ball);		
	}
	// this places the ball on either the Player's paddle or the AI's paddle
	public void serveBall(){
		
		if(!ball.paddleServe){										// this checks to see if it is the Player's serve
			ball.ballYPos = paddle.y + 30;							// if so then the x and y of the ball is set to the player's paddle
			ball.ballXPos = paddle.x + 28;			
		}
		
		if(!ball.AIPaddleServe){									// this checks to see if it is the AI's serve
			ball.ballYPos = AIPaddle.y + 25;						// if so then the x and y of the ball is set to the AI's paddle
			ball.ballXPos = AIPaddle.x - 39;			
		}		
	}	
	// this is the method to play the music for each level
	public static void playMusic(String song) {
	    try {
	        AudioInputStream music = AudioSystem.getAudioInputStream(new File(song));
	        Pong.track1 = AudioSystem.getClip();
	        Pong.track1.open(music);
	        Pong.track1.start();	       
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}
	// this method plays different sound fx like the ball bounce, goal, and loser
	public static void playSound(String song) {
	    try {
	        AudioInputStream sound = AudioSystem.getAudioInputStream(new File(song));
	        Pong.track2 = AudioSystem.getClip();
	        Pong.track2.open(sound);
	        Pong.track2.start();	       
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}
	// this method draws everything to the screen
	public void paintComponent(Graphics g){
		if(LVL1){												// this checks to see what the current level is and then sets the background color for that level
			g.setColor(Color.yellow);
		}else if(LVL2){											// this checks to see what the current level is and then sets the background color for that level
			g.setColor(Color.green);
		}else if(LVL3){											// this checks to see what the current level is and then sets the background color for that level
			g.setColor(Color.cyan);
		}
			g.fillRect(0, 0, WIDTH + 10, HEIGHT + 10);				
			
			// this sets the text for level 1
		if(LVL1 && !matchInProgress){
			Font lvl1 = new Font("Comic Sans MS", Font.BOLD, 100);
			g.setFont(lvl1);
			g.setColor(Color.RED);
			g.drawString("LEVEL 1!!!", 260, 180);
			// this sets the text for the instructions
			Font instructions = new Font("Comic Sans MS", Font.BOLD, 18);
			g.setFont(instructions);
			g.setColor(Color.RED);
			g.drawString("Use the UP and DOWN arrows or the W and S keys to move the paddle up or down.", 120, 500);
			g.drawString("Press the SPACEBAR to serve the ball. Hit the ball with the top of the paddle to send the ball up.", 70, 525);
			g.drawString("Hit the ball with the bottom of the paddle to send the ball down. The first one to score 5 points wins.", 50, 550);
		}
		// this sets the text for level 2
		if(LVL2 && !matchInProgress){
			Font lvl2 = new Font("Comic Sans MS", Font.BOLD, 100);
			g.setFont(lvl2);
			g.setColor(Color.RED);
			g.drawString("LEVEL 2!!!", 240, 180);
				
		}
		// this sets the text for level 3
		if(LVL3 && !matchInProgress){
			Font lvl3 = new Font("Comic Sans MS", Font.BOLD, 100);
			g.setFont(lvl3);
			g.setColor(Color.RED);
			g.drawString("LEVEL 3!!!", 240, 180);
			
			Font text = new Font("Comic Sans MS", Font.BOLD, 50);
			g.setFont(text);
			g.setColor(Color.RED);
			g.drawString("Get Ready to be Owned Punk!!!", 130, 250);
				
		}
		
		paddle.draw(g);
		AIPaddle.draw(g);
		ball.draw(g);
		//ball2.draw(g);
		
		if(firstBumper)					// this checks to see if the boolean for the top bumper is true
			topBumper.draw(g);			// if so it draws the top bumper to the screen
		if(thirdBumper)					// this checks to see if the boolean for the bottom bumper is true
			midBumper.draw(g);			// if so it draws the bottom bumper to the screen
		if(secondBumper)				// this checks to see if the boolean for the middle bumper is true
			bottomBumper.draw(g);		// if so it draws the middle bumper to the screen
		// this is the text for the top of the screen and holds the points the hits and the speed of the ball
		Font font = new Font("Comic Sans MS", Font.BOLD, 25);
		g.setFont(font);
		g.setColor(Color.RED);
		g.drawString("Points: " + paddle.points, 20, 30);
		g.drawString("Points: " + AIPaddle.points, 860, 30);
		g.drawString("Ball Speed: " + ball.speed, 450, 30);
		g.drawString("Hits: " + ball.hits, 250, 30);
		// this checks to see if either the Player or the AI as scored a point
		if(scored){
			if(PWins){		// this checks to see if the Player has won the round if so the appropriate text is display on screen
				Font pwins = new Font("Comic Sans MS", Font.BOLD, 100);
				g.setFont(pwins);
				g.setColor(Color.RED);
				g.drawString("Player 1 Wins!!!", 130, 350);
			}
			
			if(AIWins){		// this checks to see if the AI has won the round if so the appropriate text is display on screen
				Font aiwins = new Font("Comic Sans MS", Font.BOLD, 150);
				g.setFont(aiwins);
				g.setColor(Color.RED);
				g.drawString("LOSER!!!", 190, 350);
			}
			// if neither the Player nor the AI has won then the goal text is displayed
			if(!PWins && !AIWins){
			Font Goal = new Font("Comic Sans MS", Font.BOLD, 150);
			g.setFont(Goal);
			g.setColor(Color.RED);
			g.drawString("GOAL!!!", 250, 350);			
			}
		}
		// when either the Player or AI wins the game is paused and this text is displayed until the enter key is pressed
		if(Pause){
			Font Goal = new Font("Comic Sans MS", Font.BOLD, 50);
			g.setFont(Goal);
			g.setColor(Color.RED);
			g.drawString("Press Enter to Continue", 200, 450);
		}
	}	

	@Override
	public void keyPressed(KeyEvent e) {
		paddle.keyPressed(e);
		ball.keyPressed(e);		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		paddle.keyReleased(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		paddle.mousePressed(e, paddle);		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		paddle.mouseReleased(e, paddle);
	}
}// end of class