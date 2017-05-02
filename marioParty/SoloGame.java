package marioParty;

//this square lets the player play a solo game to gain extra coins
//solo game squares are only found at the corners of the board
public class SoloGame extends Square{

	public SoloGame(String s, int i, boolean b){
		this.pathindex = i;
		this.space = b;
		display = s + "              ";
	}

	// this method determines what happens when a player lands on the square
	public void land(Player p, MarioPartyBoard b, int roll) throws InterruptedException	{
		this.players.add(p);
		b.draw();
		
		Thread.sleep(500);
		
		if (roll == 0)
			p.Coins += (int) ((Math.random() * 10) + 1);
		else
			b.movePlayer(p, roll);
	}// end of the land method
	
}// end of the SoloGame class