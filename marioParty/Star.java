package marioParty;

//this square lets the player buy a star if they have enough coins
//if the star is bought the location of the star changes and the cost of the star is increased by 10 coins
public class Star extends Square{
	
	public String starFloor;
	
	public Star(String s, int i, boolean b)	{
		starFloor = "*****STAR*******";
		this.pathindex = i;
		this.space = b;
		display = s + "              ";
	}

	// this method determines what happens when a player lands on the square
	public void land(Player p, MarioPartyBoard b, int roll) throws InterruptedException	{
		this.players.add(p);
		b.draw();
		
		Thread.sleep(500);
		
		if (p.Coins >= STARCOST){
			p.Stars++;
			p.Coins -= STARCOST;
			STARCOST += 10;
			b.moveStar();
		}

		if (roll == 0) return;
		else b.movePlayer(p, roll);
		
	}// end of the land method

	public String DrawFifthLine(){
		if (space)
			return "|" + starFloor.substring(0, MarioPartyBoard.SQUARE_WIDTH)
			+ "|";
		else
			return " " + display.substring(0, MarioPartyBoard.SQUARE_WIDTH)
			+ " ";
	}// end of DrawFifthLine method
	
}// end of Star class