package marioParty;

//this square increases the players coins by 5
public class Plus5 extends Square{

	public Plus5(String s, int i, boolean b){
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
			p.Coins += 5;
			if(p.location.players.size() > 1)
				if(p.location.players.size() > 2)
					b.playerBattle(p.location.players.get(0), p.location.players.get(1), p.location.players.get(2));
				else b.playerBattle(p.location.players.get(0), p.location.players.get(1));
						
		}else b.movePlayer(p, roll);
		
	}// end of land method
	
}// end of Plus5 class