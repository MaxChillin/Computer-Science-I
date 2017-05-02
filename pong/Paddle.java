package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Paddle extends Rectangle{
	
	private static final long serialVersionUID = 1L;
	
	public static int width = 30;
	public static int height = 100;
	
	public boolean up = false;
	public boolean down = false;	
	
	public int points;
	
	public Paddle(int x, int y, int width, int height){
		setBounds(x, y, width, height);
	}
	
	public void tick(){
		if(Pong.Pause){						// if the game is paused movement is not allowed
			
		}else{								// this controls the movement of the Players paddle
		if(y <= 0) up = false;
		if(y + height >= Pong.HEIGHT) down = false;
		
		if(up) y-=5;
		if(down) y+=5;
		}
	}
	
	public void tick(Ball ball){}	
	
	public void keyPressed(KeyEvent e) {
		//e.getKeyCode() == KeyEvent.VK_ALT && e.getKeyCode() == KeyEvent.VK_SHIFT && 
		if(e.getKeyCode() == KeyEvent.VK_1){
			Pong.LVL1 = true;
			Pong.LVL2 = false;
			Pong.LVL3 = false;
			//Pong.track1.stop();
			Pong.musicStarted = false;
		}
		//e.getKeyCode() == KeyEvent.VK_ALT && e.getKeyCode() == KeyEvent.VK_SHIFT && 
		if(e.getKeyCode() == KeyEvent.VK_2){
			Pong.LVL2 = true;
			Pong.LVL1 = false;
			Pong.LVL3 = false;
			//Pong.track1.stop();
			Pong.musicStarted = false;
		}
		//e.getKeyCode() == KeyEvent.VK_ALT && e.getKeyCode() == KeyEvent.VK_SHIFT && 
		if(e.getKeyCode() == KeyEvent.VK_3){
			Pong.LVL3 = true;
			Pong.LVL2 = false;
			Pong.LVL1 = false;
			//Pong.track1.stop();
			Pong.musicStarted = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) up = true;		
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) down = true;
		if(e.getKeyCode() == KeyEvent.VK_ENTER && Pong.Pause){
			Pong.Pause = false;										// when Enter is pressed the game is unpaused the win booleans are reset
			Pong.PWins = false;										
			Pong.AIWins = false;									
			Pong.matchInProgress = true;							// this tells the game that the match is in progress
		}
	}

	public void keyReleased(int k) {
		if(k == KeyEvent.VK_UP || k == KeyEvent.VK_W) up = false;		
		if(k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S) down = false;
	}	
		
	public void mousePressed(MouseEvent e, Paddle paddle)
    {    	
    	if (e.getX() >= paddle.x - 20 &&  e.getX() <= paddle.x + 20 && e.getY() >= paddle.y)
    	{
    		down = true;
    		up = false;
    	}
    	
    	if (e.getX() >= paddle.x - 20 &&  e.getX() <= paddle.x + 20 && e.getY() <= paddle.y)
    	{
    		up = true;
    		down = false;
    	}
    }
	
	public void mouseReleased(MouseEvent e, Paddle paddle) {
		up = false;
		down = false;		
	}
	
	public void draw(Graphics g){
		g.setColor(Color.BLUE);		
		g.fillRoundRect(x, y, width, height, 35, 35);
	}
}
