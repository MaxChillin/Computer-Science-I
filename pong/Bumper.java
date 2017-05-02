package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bumper extends Rectangle{
	
	private static final long serialVersionUID = 1L;
	
	public Bumper(int x, int y, int width, int height){
		setBounds(x, y, width, height);		
	}
	
	public void tick(Ball ball){}
	
	public void draw(Graphics g){
		g.setColor(Color.BLUE);
		if(Pong.rightImpact)
			g.fillRoundRect(x + 35, y, width, height, 35, 35);
		else if(Pong.leftImpact)
			g.fillRoundRect(x - 35, y, width, height, 35, 35);
		else
			g.fillRoundRect(x, y, width, height, 35, 35);
	}// end of draw method
}// end of class