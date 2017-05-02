package marioParty;

//This class displays a square on the middle of the board to indicate the cost and location of the star 
public class StarInfo extends Square{
	public String starLocation, starCost;

	public StarInfo(String s, int C, int S, int W, boolean b){
		starLocation = "Location:  " + C + "       ";
		starCost = "Cost:  " + S + "       ";
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
			return "|"+ starLocation.substring(0, MarioPartyBoard.SQUARE_WIDTH)+ "|";
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
			return "|" + starCost.substring(0, MarioPartyBoard.SQUARE_WIDTH)
			+ "|";
		else
			return " " + display.substring(0, MarioPartyBoard.SQUARE_WIDTH)
			+ " ";
	}	
}// end of StarInfo class