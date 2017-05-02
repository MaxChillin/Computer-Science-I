package marioParty;

public class Info extends Square{	
	public String Coins, Stars, Wins;

	public Info(String s, int C, int S, int W, boolean b){
		Coins = "Coins:  " + C + "       ";
		Stars = "Stars:  " + S + "       ";
		Wins = "Wins:  " + W + "       ";
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
			return "|"+ Coins.substring(0, MarioPartyBoard.SQUARE_WIDTH)+ "|";
		else
			return " " + display.substring(0, MarioPartyBoard.SQUARE_WIDTH)+ " ";
	}

	public String DrawThirdLine(){
		if (space)
			return "|" + Stars.substring(0, MarioPartyBoard.SQUARE_WIDTH)
			+ "|";
		else
			return " " + display.substring(0, MarioPartyBoard.SQUARE_WIDTH)
			+ " ";
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
			return "|" + Stars.substring(0, MarioPartyBoard.SQUARE_WIDTH)
			+ "|";
		else
			return " " + display.substring(0, MarioPartyBoard.SQUARE_WIDTH)
			+ " ";
	}
}// end of Info class