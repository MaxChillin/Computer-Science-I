package marioParty;

//this square decreases the players coins by 3
public class Minus3 extends Square{

	public Minus3(String s, int i, boolean b){
		this.pathindex = i;
		this.space = b;
		display = s + "              ";
	}

	// this method determines what happens when a player lands on the square
	public void land(Player p, MarioPartyBoard b, int roll) throws InterruptedException	{
		this.players.add(p);
		b.draw();
		
		Thread.sleep(500);
		
		if (roll == 0){
			p.Coins -= 3;
			if(p.location.players.size() > 1)
				if(p.location.players.size() > 2)
					b.playerBattle(p.location.players.get(0), p.location.players.get(1), p.location.players.get(2));
				else b.playerBattle(p.location.players.get(0), p.location.players.get(1));
						
		}else b.movePlayer(p, roll);
		
	}// end of the land method
	
}// end of Minus3 class