package marioParty;

//this square gives the player the option to attack another player in hopes of taking some of the other players coins
public class OptionBattle extends Square{

	// This is the constructor
	public OptionBattle(String s, int i, boolean b){
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
			if(p.location.players.size() > 1)
				if(p.location.players.size() > 2)
					b.playerBattle(p.location.players.get(0), p.location.players.get(1), p.location.players.get(2));
				else b.playerBattle(p.location.players.get(0), p.location.players.get(1));
			else b.movePlayer(p, roll);
		
	}// end of land method
	
}// end of OptionBattle class