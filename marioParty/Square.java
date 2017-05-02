package marioParty;

import java.util.ArrayList;

// this is the basic square class
public class Square{
	
	public static int STARCOST = 20;
	public String display;
	public ArrayList<Player> players = new ArrayList<Player>();
	public String blank = "               ";
	public String floor = "_____________ ";	

	public boolean space;
	public int pathindex;

	// constructor
	public Square(){
		display = "               ";
	}

	// overloaded constructor
	public Square(String s, int i, boolean b){

		this.pathindex = i;
		this.space = b;
		display = s + "              ";
	}

	// this is the basic method for the actions that take place when a player lands on a square
	public void land(Player p, MarioPartyBoard b, int roll) throws InterruptedException	{		
		this.players.add(p);
		b.draw();
		
		Thread.sleep(500);		

		if (roll == 0)	return;
		else b.movePlayer(p, roll);
		
	}// end of the land method

	public String toString(){
		if (space)
			return "|" + display.substring(0, MarioPartyBoard.SQUARE_WIDTH)+ "|";
		else
			return " " + display.substring(0, MarioPartyBoard.SQUARE_WIDTH)	+ " ";
	}// end of toString method

	public String DrawFirstLine(){
		return this.toString();
	}

	public String DrawSecondLine(){
		String playerOutput = players.toString()
				+ "                                ";
		if (space)
			return "|" + playerOutput.substring(0, MarioPartyBoard.SQUARE_WIDTH) + "|";
		else
			return " " + display.substring(0, MarioPartyBoard.SQUARE_WIDTH)	+ " ";
	}// end of DrawSecondLine method

	public String DrawThirdLine(){
		if (space)
			return "|" + blank.substring(0, MarioPartyBoard.SQUARE_WIDTH) + "|";
		else
			return " " + display.substring(0, MarioPartyBoard.SQUARE_WIDTH)	+ " ";
	}// end of DrawThirdLine method

	public String DrawForthLine(){
		if (space)
			return "|" + blank.substring(0, MarioPartyBoard.SQUARE_WIDTH) + "|";
		else
			return " " + display.substring(0, MarioPartyBoard.SQUARE_WIDTH) + " ";
	}// end of DrawFourthLine method

	public String DrawFifthLine(){
		if (space)
			return "|" + floor.substring(0, MarioPartyBoard.SQUARE_WIDTH) + "|";
		else
			return " " + display.substring(0, MarioPartyBoard.SQUARE_WIDTH)	+ " ";
	}// end of DrawFifthLine method
	
}// end of Square class