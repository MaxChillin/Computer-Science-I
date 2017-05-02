package pong;

public class AIPaddle extends Paddle{
	private static final long serialVersionUID = 1L;
	
	private int speed = 5;

	public AIPaddle(int x, int y, int width, int height) {
		super(x, y, width, height);		
	}
	
	public void tick(){}
	
	// this is the tick method for the AI paddle so it can track the location of the ball.
	public void tick(Ball ball){
		if(Pong.Pause){
			
		}else{			
			if(Pong.LVL1)
				speed = 5;
			else if(Pong.LVL2)
				speed = 8;
			else if(Pong.LVL3)
				speed = 15;
		
		
			if(ball.ballYPos < y + (height / 2)){
				up = true;
				down = false;
			} 
			
			if(ball.ballYPos + 25 > y + (height / 2)){
				down = true;
				up = false;
			}					
			
			if(y <= 0) up = false;
			if(y + height >= Pong.HEIGHT) down = false;
			
			if(up) y-=speed;
			if(down) y+=speed;
		}
	}
}