package marioParty;

//this is the basic player class
public class Player{
	
	final String name;
	int xPos;
	int yPos;
	Square location;
	int Coins = 0;
	int Stars = 0;
	int Wins = 0;

	// Constructor
	public Player(Square start, String passedInName){
		location = start;
		name = passedInName;
	}// end of constructor

	public int roll(){
		return (int) (Math.random() * 8) + 1;
	}// end of roll method

	public String toString(){
		return name;
	}// end of toString method
	
}// end of Player class