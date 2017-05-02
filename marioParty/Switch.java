package marioParty;

public class Switch extends Square{

	public Switch(String s, int i, boolean b){
		this.pathindex = i;
		this.space = b;
		display = s + "              ";
	}

	// this method determines what happens when a player lands on the square
	public void land(Player p, MarioPartyBoard b, int roll) throws InterruptedException	{
		this.players.add(p);
		b.draw();
		
		Thread.sleep(500);		

		if(p instanceof SmartPlayer){// this is a check to see if the AI is smart or dumb
				
			if(p.Coins >= STARCOST - 5){// this checks to see if the player has within 5 coins of the cost of the STAR
				
				switch (p.location.pathindex){
				case 7:					
					if(MarioPartyBoard.STARINDEX == 8 || 
						MarioPartyBoard.STARINDEX == 13 || 
						MarioPartyBoard.STARINDEX == 17 || 
						MarioPartyBoard.STARINDEX == 22 || 
						MarioPartyBoard.STARINDEX == 26)					
						p.location.pathindex = 7;
					else											
						p.location.pathindex = 57;
					break;
				case 42:
					if (MarioPartyBoard.STARINDEX == 13 || 
						MarioPartyBoard.STARINDEX == 17 || 
						MarioPartyBoard.STARINDEX == 22 || 
						MarioPartyBoard.STARINDEX == 26)
						p.location.pathindex = 42;
					else											
						p.location.pathindex = 58;
					break;
				case 52:
					if (MarioPartyBoard.STARINDEX == 56 || 
						MarioPartyBoard.STARINDEX == 35 || 
						MarioPartyBoard.STARINDEX == 39 || 
						MarioPartyBoard.STARINDEX == 4 || 
						MarioPartyBoard.STARINDEX == 40 || 
						MarioPartyBoard.STARINDEX == 46 || 
						MarioPartyBoard.STARINDEX == 50)							
						p.location.pathindex = 59;
					else					
						p.location.pathindex = 52;										
					break;
				}// end of switch
				
			}// end of cost check if statement
			
		}else{// this is the dumb AI it just rolls a random 1 or 0 and makes a decision based on that
		
			int random = (int)(Math.random()* 2);

			switch (p.location.pathindex){
			case 7:			
				if(random == 1)	p.location.pathindex = 57;
				break;			
			case 42:			
				if(random == 1)p.location.pathindex = 58;							
				break;			
			case 52:			
				if(random == 1)	p.location.pathindex = 59;
				break;
			
			}// end of switch
			
		}// end of else section of the if-else statement


		if (roll == 0)
			b.movePlayer(p, 1);
		else
			b.movePlayer(p, roll);
	}// end of the land method
	
}// end of Switch class