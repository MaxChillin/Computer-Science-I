package marioParty;

//This class displays a square in the middle of the board to show the current round of the game
public class Round extends Square{
	
	public String rounds;

	public Round(String s, int C, int S, int W, boolean b){
		rounds = "     " + C + "       ";		
		this.space = b;
		display = s + "              ";
	}

	public String toString(){
		if (space)
			return "|" + display.substring(0, MarioPartyBoard.SQUARE_WIDTH)
			+ "|";
		else
			return " " + display.substring(0, MarioPartyBoard.SQUARE_WIDTH)
			+ " ";
	}

	public String DrawFirstLine(){
		return this.toString();
	}

	public String DrawSecondLine(){

		if (space)
			return "|"+ rounds.substring(0, MarioPartyBoard.SQUARE_WIDTH)+ "|";
		else
			return " " + display.substring(0, MarioPartyBoard.SQUARE_WIDTH)+ " ";
	}

	public String DrawForthLine(){
		if (space)
			return "|" + blank.substring(0, MarioPartyBoard.SQUARE_WIDTH)
			+ "|";
		else
			return " " + display.substring(0, MarioPartyBoard.SQUARE_WIDTH)
			+ " ";
	}

	public String DrawFifthLine(){
		if (space)
			return "|" + blank.substring(0, MarioPartyBoard.SQUARE_WIDTH)
			+ "|";
		else
			return " " + display.substring(0, MarioPartyBoard.SQUARE_WIDTH)
			+ " ";
	}
	
}// end of Round class