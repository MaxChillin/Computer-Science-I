package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Ball implements Runnable
{
	public int sin, cos;
	public int ballYPos;
	int ballXPos, hits;
	int radius = 20;
	float speed = 5.0f;
	float StartSpeed = 5.0f;
	
	public String bop = "C:/Windows/Media/Windows Pop-up Blocked.wav";
	
	private boolean up = false;
	private boolean down = false;
	private boolean right = false;
	private boolean left = false;
	boolean paddleServe = false;
	boolean AIPaddleServe = true;
	boolean phit = false;
	boolean aihit = false;
	boolean ticked = false;
	
	public Ball(int x, int y){
		this.ballXPos = x;
		this.ballYPos = y;		
	}
	
	public void draw(Graphics g){
		g.setColor(Color.RED);
		g.fillOval(this.ballXPos, this.ballYPos, radius * 2, radius * 2);
	}
	
	
	public void tick2(Paddle paddle, Paddle AIPaddle){
		sin = (int) (Math.sin(System.nanoTime() )* 10);
		cos = (int) (Math.cos(System.nanoTime() )* 10);
		
		ballXPos += sin;
		ballYPos -= cos;
	}
	
	// this is the method for controlling the movement of the ball.
	public void tick(Paddle paddle, Paddle AIPaddle){
		if(Pong.LVL1) StartSpeed = 5.0f;
		
		if(Pong.LVL2) StartSpeed = 8.0f;
		
		if(Pong.LVL3) StartSpeed = 15.0f;
		
		Pong.leftImpact = false;
		Pong.rightImpact = false;
		
		if(Pong.Pause){
			
		}else if(!paddleServe){							// this checks to see if it is the players turn to serve the ball.
			speed = StartSpeed;							// the speed is reset to 5 each new serve
			ballYPos = paddle.y + 25;					// the position of the ball is placed in the middle of the paddle
			ballXPos = paddle.x + 28;
		}else if(!AIPaddleServe){						// this checks to see if it is the computers turn to serve the ball
			speed = StartSpeed;							// the speed is reset to 5 each new serve			
			ballYPos = AIPaddle.y + 25;					// the position of the ball is placed in the middle of the paddle
			ballXPos = AIPaddle.x - 14;			
				
			right = true;								// there is a slight pause and then the ball leaves the computers paddle
			AIPaddleServe = true;						// the boolean for the computers serve is set to true
		}else {
			
		if(ballXPos <= 0){
			if(AIPaddle.points != 4)					
				Pong.playSound("src/pong/goal.WAV");
			Pong.scored = true;
			AIPaddle.points++;
			AIPaddleServe = false;
			up = false;
			down = false;
			hits = 0;
			speed = StartSpeed;
		}
		
		else if(ballXPos + radius >= Pong.WIDTH){
			Pong.playSound("src/pong/goal.WAV");
			Pong.scored = true;
			paddle.points++;
			paddleServe = false;
			up = false;
			down = false;
			hits = 0;
			speed = StartSpeed;
		}
		
		// this keeps the ball from going to high off the screen
		if(ballYPos <= 0){
			Pong.playSound(bop);
			up = false; 
			down = true;
		}
		
		// this keeps the ball from going to low off the screen
		else if(ballYPos + radius >= Pong.HEIGHT){
			Pong.playSound(bop);
			down = false; 
			up = true;
		}
			
		if((paddle.points >= 2 && ballXPos > 480) || (AIPaddle.points >= 2 && ballXPos < 500)){
			Pong.firstBumper = true;
		}else Pong.firstBumper = false;
		
		if((paddle.points >= 3 && ballXPos > 480) || (AIPaddle.points >= 3 && ballXPos < 500)){
			Pong.secondBumper = true;
		}else Pong.secondBumper = false;
		
		if((paddle.points >= 4 && ballXPos > 480) || (AIPaddle.points >= 4 && ballXPos < 500)){
			Pong.thirdBumper = true;
		}else Pong.thirdBumper = false;		
			
		// this is center collision and does not change the up or down movement of the ball
		if((ballXPos >= paddle.x && ballXPos <= paddle.x + 20) && (ballYPos <= paddle.y + 110 && ballYPos >= paddle.y - 30)){
			Pong.playSound(bop);			
			right = true;
			left = false;
			if(!phit){
				hits++;
				phit = true;
				aihit = false;
				ticked = false;
			}
		}
		
		// this is the collision check for the top of the players paddle and sends the ball back at an upward angle
		if((ballXPos >= paddle.x && ballXPos <= paddle.x + 20) && (ballYPos <= paddle.y + 10 && ballYPos >= paddle.y - 30)){
			right = true;
			up = true;
			left = false;
			down = false;
		}
		
		// this is the collision check for the bottom of the players paddle and sends the ball back at an downward angle
		if((ballXPos >= paddle.x && ballXPos <= paddle.x + 20) && (ballYPos <= paddle.y + 110 && ballYPos >= paddle.y + 50)){
			right = true;
			up = false;
			left = false;
			down = true;
		}
		
		if((ballXPos >= AIPaddle.x - 35 && ballXPos <= AIPaddle.x + 20) && (ballYPos >= AIPaddle.y - 30 && ballYPos <= AIPaddle.y + 110)){
			Pong.playSound(bop);
			if((speed > StartSpeed + 2.0f) && Pong.LVL3)
				up = true;
			
			right = false;
			left = true;
			if(!aihit){
				hits++;
				aihit = true;
				phit = false;
				ticked = false;
			}
			
		}
		
		if((ballXPos >= AIPaddle.x - 35 && ballXPos <= AIPaddle.x + 20) && (ballYPos >= AIPaddle.y - 30 && ballYPos <= AIPaddle.y + 10)){
			left = true;
			right = false;
			up = true;			
			down = false;
		}		
		
		if((ballXPos >= AIPaddle.x - 35 && ballXPos <= AIPaddle.x + 20) && (ballYPos >= AIPaddle.y + 50 && ballYPos <= AIPaddle.y + 110)){
			left = true;
			right = false;
			up = false;			
			down = true;			
		}
		
		if(Pong.firstBumper && (( ballXPos >= 460 && ballXPos <= 480) || ( ballXPos >= 480 && ballXPos <= 500)) && (ballYPos + radius >= 60 && ballYPos <= 170)){
			if(left && paddle.points >= 2){
				Pong.playSound(bop);
				left = false;
				right = true;
				down = true;
				aihit = false;
				Pong.leftImpact = true;
			}else if(right && AIPaddle.points >= 2){
				Pong.playSound(bop);
				left = true;
				right = false;
				down = true;
				phit = false;
				Pong.rightImpact = true;
				}
		}
		
		if(Pong.secondBumper && (( ballXPos >= 460 && ballXPos <= 480) || ( ballXPos >= 480 && ballXPos <= 500)) && (ballYPos >= 450 && ballYPos <= 570)){
			if(left && paddle.points >= 3){
				Pong.playSound(bop);
				left = false;
				right = true;
				up = true;
				aihit = false;
				Pong.leftImpact = true;
			}else if(right && AIPaddle.points >= 3){
				Pong.playSound(bop);
				left = true;
				right = false;
				up = true;
				phit = false;
				Pong.rightImpact = true;
				}
		}
		
		if(Pong.thirdBumper && (( ballXPos >= 460 && ballXPos <= 480) || ( ballXPos >= 480 && ballXPos <= 500)) && (ballYPos >= 250 && ballYPos <= 370)){
			if(left && paddle.points >= 4){
				Pong.playSound(bop);
				int random = (int) (Math.random()*2);
				if(random == 1){
					up = true;
				}else
					down = true;
				
				left = false;
				right = true;
				aihit = false;
				Pong.leftImpact = true;
			}else if(right && AIPaddle.points >= 4){
				Pong.playSound(bop);
				int random = (int) (Math.random()*2);
				if(random == 1){
					up = true;
				}else
					down = true;
				
				left = true;
				right = false;
				phit = false;
				Pong.rightImpact = true;
				}
		}		
		
		if(up){ 
			ballYPos-=speed;
		}else if(down){ 
			ballYPos+=speed;
		}
		
		if(right){
			ballXPos+=speed;
		}else if(left){
			ballXPos-=speed;
		}else ballXPos+=speed;
		
		if((hits % 5 == 0) && !ticked){
			speed += 1.0f;
			ticked = true;
		}
		}	
	}	
	
	public void keyPressed(KeyEvent e) {		
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			paddleServe = true;
			Pong.matchInProgress = true;
		}
	}	

	@Override
	public void run() {}	
}