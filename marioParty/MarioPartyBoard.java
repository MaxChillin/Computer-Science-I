package marioParty;

// this is the basic class that holds the game board info
public class MarioPartyBoard {
	
	public static final int MAX_ROUNDS = 25;
	public static final int SQUARE_WIDTH = 13;
	public static int STARINDEX;
	Square[][] board = new Square[11][11];
	public int temp;
	public int rounds = 1;
	public boolean restore, started;	

	// this is the constructor
	public MarioPartyBoard(){

		for (int x = 0; x < board.length; x++)
			for (int y = 0; y < board[x].length; y++)
				board[x][y] = new Square();


		// first row
		board[0][0] = new SoloGame("  Solo Game", 20, true);
		board[0][1] = new Minus3("   Minus 3", 21, true);
		board[0][2] = new OptionBattle("Option Battle", 22, true);
		board[0][3] = new Plus4("   Plus 4", 23, true);
		board[0][4] = new Plus5("   Plus 5", 24, true);
		board[0][5] = new Plus6("   Plus 6", 25, true);
		board[0][6] = new OptionBattle("Option Battle", 26, true);
		board[0][7] = new Plus1("   Plus 1", 27, true);
		board[0][8] = new Plus2("   Plus 2", 28, true);
		board[0][9] = new Minus3("   Minus 3", 29, true);
		board[0][10] = new SoloGame("  Solo Game", 30, true);
		// row 2
		board[1][0] = new Plus2("   Plus 2", 19, true);
		board[1][7] = new OptionBattle("Option Battle", 54, true);
		board[1][10] = new OptionBattle("Option Battle", 31, true);
		// row 3
		board[2][0] = new Plus1("   Plus 1", 18, true);
		board[2][7] = new Minus3("   Minus 3", 53, true);
		board[2][10] = new Plus4("   Plus 4", 32, true);
		// row 4
		board[3][0] = new OptionBattle("Option Battle", 17, true);
		board[3][5] = new OptionBattle("Option Battle", 50, true);
		board[3][6] = new Plus1("   Plus 1", 51, true);
		board[3][7] = new Switch("   Switch", 52, true);
		board[3][8] = new Minus3("   Minus 3", 55, true);
		board[3][9] = new OptionBattle("Option Battle", 56, true);
		board[3][10] = new Plus5("   Plus 5", 33, true);
		// row 5
		board[4][0] = new Plus6("   Plus 6", 16, true);
		board[4][5] = new Minus3("   Minus 3", 49, true);
		board[4][10] = new Plus6("   Plus 6", 34, true);
		// row 6
		board[5][0] = new Plus5("   Plus 5", 15, true);
		board[5][5] = new Plus2("   Plus 2", 48, true);
		board[5][10] = new OptionBattle("Option Battle", 35, true);
		// row 7
		board[6][0] = new Plus4("   Plus 4", 14, true);
		board[6][5] = new Plus1("   Plus 1", 47, true);
		board[6][10] = new Plus1("   Plus 1", 36, true);
		// row 8
		board[7][0] = new OptionBattle("Option Battle", 13, true);
		board[7][1] = new Plus1("   Plus 1", 44, true);
		board[7][2] = new Minus3("   Minus 3", 43, true);
		board[7][3] = new Switch("   Switch", 42, true);
		board[7][4] = new Minus3("   Minus 3", 45, true);
		board[7][5] = new OptionBattle("Option Battle", 46, true);
		board[7][10] = new Plus2("   Plus 2", 37, true);
		// row 9
		board[8][0] = new Minus3("   Minus 3", 12, true);
		board[8][3] = new Plus1("   Plus 1", 41, true);
		board[8][10] = new Minus3("  Minus 3", 38, true);
		// row 10
		board[9][0] = new Plus2("   Plus 2", 11, true);
		board[9][3] = new OptionBattle("Option Battle", 40, true);
		board[9][10] = new OptionBattle("Option Battle", 39, true);
		// row 11
		board[10][10] = new Square("    Start", 0, true);
		board[10][9] = new Plus1("   Plus 1", 1, true);
		board[10][8] = new Plus2("   Plus 2", 2, true);
		board[10][7] = new Minus3("   Minus 3", 3, true);
		board[10][6] = new OptionBattle("Option Battle", 4, true);
		board[10][5] = new Plus4("   Plus 4", 5, true);
		board[10][4] = new Plus5("   Plus 5", 6, true);
		board[10][3] = new Switch("   Switch", 7, true);
		board[10][2] = new OptionBattle("Option Battle", 8, true);
		board[10][1] = new Plus1("   Plus 1", 9, true);
		board[10][0] = new SoloGame("  Solo Game", 10, true);		

		// Player Windows
		board[5][7] = new Info("  Player 1", 0, 0, 0, true);
		board[5][8] = new Info("  Player 2", 0, 0, 0, true);
		board[7][7] = new Info("  Player 3", 0, 0, 0, true);
		//board[7][8] = new Info("  Player 4", 0, 0, 0, true);
		board[3][3] = new StarInfo("  Star Info", 0, 0, 0, true);
		board[3][2] = new Round("   ROUND", rounds, 0, 0, true);
	}

	// this is a 2 player battle when 2 players land on the same square
	public void playerBattle(Player p1, Player p2)
	{
		int p1Roll = (int) ((Math.random() * 10) + 1);
		// System.out.println("P1 rolled " + p1Roll);
		int p2Roll = (int) ((Math.random() * 10) + 1);
		// System.out.println("P2 rolled " + p2Roll);

		if (p1Roll >= p2Roll)
		{
			if (p1Roll == p2Roll)
			{
				return;
			}
			{
				p1.Coins += 10;
				p1.Wins++;
				p2.Coins -= 10;
			}
		}else
		{
			p2.Coins += 10;
			p2.Wins++;
			p1.Coins -= 10;
		}
	}

	// this is a 3 player battle when 3 players land on the same square
	public void playerBattle(Player p1, Player p2, Player p3)
	{
		int p1Roll = (int) ((Math.random() * 10) + 1);
		// System.out.println("P1 rolled " + p1Roll);
		int p2Roll = (int) ((Math.random() * 10) + 1);
		// System.out.println("P2 rolled " + p2Roll);
		int p3Roll = (int) ((Math.random() * 10) + 1);
		// System.out.println("P3 rolled " + p3Roll);

		if (p1Roll >= p2Roll)
		{
			if (p1Roll >= p3Roll)
			{
				if (p1Roll == p2Roll)
				{
					if (p1Roll == p3Roll)
					{
						p1.Coins += 5;
						p2.Coins += 5;
						p3.Coins += 5;
					} else
					{
						p1.Coins += 5;
						p2.Coins += 5;
						p3.Coins -= 10;
					}
				} else if (p1Roll == p3Roll)
				{
					p1.Coins += 5;
					p3.Coins += 5;
					p2.Coins -= 10;
				} else
				{
					p1.Coins += 10;
					p2.Coins -= 5;
					p3.Coins -= 5;
				}
			} else
			{
				p3.Coins += 10;
				p2.Coins -= 5;
				p1.Coins -= 5;
			}
		} else if (p2Roll >= p3Roll)
		{
			if (p2Roll == p3Roll)
			{
				p2.Coins += 5;
				p3.Coins += 5;
				p1.Coins -=10;
			} else
			{
				p2.Coins += 10;
				p3.Coins -= 5;
				p1.Coins -= 5;
			}
		} else
		{
			p3.Coins += 10;
			p2.Coins -= 5;
			p1.Coins -= 5;
		}

	}

	// this is a battle that all players play at the end of each round
	public void groupBattle(Player p1, Player p2, Player p3)
	{
		int p1Roll = (int) ((Math.random() * 10) + 1);
		// System.out.println("P1 rolled " + p1Roll);
		int p2Roll = (int) ((Math.random() * 10) + 1);
		// System.out.println("P2 rolled " + p2Roll);
		int p3Roll = (int) ((Math.random() * 10) + 1);
		// System.out.println("P3 rolled " + p3Roll);

		if (p1Roll >= p2Roll)
		{
			if (p1Roll >= p3Roll)
			{
				if (p1Roll == p2Roll)
				{
					if (p1Roll == p3Roll)
					{
						p1.Coins += 5;
						p1.Wins++;
						p2.Coins += 5;
						p2.Wins++;
						p3.Coins += 5;
						p3.Wins++;
					} else
					{
						p1.Coins += 5;
						p1.Wins++;
						p2.Coins += 5;
						p2.Wins++;
					}
				} else if (p1Roll == p3Roll)
				{
					p1.Coins += 5;
					p1.Wins++;
					p3.Coins += 5;
					p3.Wins++;
				} else
				{
					p1.Coins += 10;
					p1.Wins++;
				}
			} else
			{
				p3.Coins += 10;
				p3.Wins++;
			}
		} else if (p2Roll >= p3Roll)
		{
			if (p2Roll == p3Roll)
			{
				p2.Coins += 5;
				p2.Wins++;
				p3.Coins += 5;
				p3.Wins++;
			} else
			{
				p2.Coins += 10;
				p2.Wins++;
			}
		} else
		{
			p3.Coins += 10;
			p3.Wins++;
		}

	}

	// this is the method that Prof Laughlin said was horrible. 
	// It returns to coordinates to move the players correctly around the board.
	public Square getLocation(Player p, int index)
	{
		switch (index)
		{
		case 0:
			p.xPos = 10;
			p.yPos = 10;
			break;
		case 1:
			p.xPos = 9;
			p.yPos = 10;
			break;
		case 2:
			p.xPos = 8;
			p.yPos = 10;
			break;
		case 3:
			p.xPos = 7;
			p.yPos = 10;
			break;
		case 4:
			p.xPos = 6;
			p.yPos = 10;
			break;
		case 5:
			p.xPos = 5;
			p.yPos = 10;
			break;
		case 6:
			p.xPos = 4;
			p.yPos = 10;
			break;
		case 7:
			p.xPos = 3;
			p.yPos = 10;
			break;
		case 8:
			p.xPos = 2;
			p.yPos = 10;
			break;
		case 9:
			p.xPos = 1;
			p.yPos = 10;
			break;
		case 10:
			p.xPos = 0;
			p.yPos = 10;
			break;
		case 11:
			p.xPos = 0;
			p.yPos = 9;
			break;
		case 12:
			p.xPos = 0;
			p.yPos = 8;
			break;
		case 13:
			p.xPos = 0;
			p.yPos = 7;
			break;
		case 14:
			p.xPos = 0;
			p.yPos = 6;
			break;
		case 15:
			p.xPos = 0;
			p.yPos = 5;
			break;
		case 16:
			p.xPos = 0;
			p.yPos = 4;
			break;
		case 17:
			p.xPos = 0;
			p.yPos = 3;
			break;
		case 18:
			p.xPos = 0;
			p.yPos = 2;
			break;
		case 19:
			p.xPos = 0;
			p.yPos = 1;
			break;
		case 20:
			p.xPos = 0;
			p.yPos = 0;
			break;
		case 21:
			p.xPos = 1;
			p.yPos = 0;
			break;
		case 22:
			p.xPos = 2;
			p.yPos = 0;
			break;
		case 23:
			p.xPos = 3;
			p.yPos = 0;
			break;
		case 24:
			p.xPos = 4;
			p.yPos = 0;
			break;
		case 25:
			p.xPos = 5;
			p.yPos = 0;
			break;
		case 26:
			p.xPos = 6;
			p.yPos = 0;
			break;
		case 27:
			p.xPos = 7;
			p.yPos = 0;
			break;
		case 28:
			p.xPos = 8;
			p.yPos = 0;
			break;
		case 29:
			p.xPos = 9;
			p.yPos = 0;
			break;
		case 30:
			p.xPos = 10;
			p.yPos = 0;
			break;
		case 31:
			p.xPos = 10;
			p.yPos = 1;
			break;
		case 32:
			p.xPos = 10;
			p.yPos = 2;
			break;
		case 33:
			p.xPos = 10;
			p.yPos = 3;
			break;
		case 34:
			p.xPos = 10;
			p.yPos = 4;
			break;
		case 35:
			p.xPos = 10;
			p.yPos = 5;
			break;
		case 36:
			p.xPos = 10;
			p.yPos = 6;
			break;
		case 37:
			p.xPos = 10;
			p.yPos = 7;
			break;
		case 38:
			p.xPos = 10;
			p.yPos = 8;
			break;
		case 39:
			p.xPos = 10;
			p.yPos = 9;
			break;
		case 40:
			p.xPos = 3;
			p.yPos = 9;
			break;
		case 41:
			p.xPos = 3;
			p.yPos = 8;
			break;
		case 42:
			p.xPos = 3;
			p.yPos = 7;
			break;
		case 43:
			p.xPos = 2;
			p.yPos = 7;
			break;
		case 44:
			p.xPos = 1;
			p.yPos = 7;
			break;
		case 45:
			p.xPos = 4;
			p.yPos = 7;
			break;
		case 46:
			p.xPos = 5;
			p.yPos = 7;
			break;
		case 47:
			p.xPos = 5;
			p.yPos = 6;
			break;
		case 48:
			p.xPos = 5;
			p.yPos = 5;
			break;
		case 49:
			p.xPos = 5;
			p.yPos = 4;
			break;
		case 50:
			p.xPos = 5;
			p.yPos = 3;
			break;
		case 51:
			p.xPos = 6;
			p.yPos = 3;
			break;
		case 52:
			p.xPos = 7;
			p.yPos = 3;
			break;
		case 53:
			p.xPos = 7;
			p.yPos = 2;
			break;
		case 54:
			p.xPos = 7;
			p.yPos = 1;
			break;
		case 55:
			p.xPos = 8;
			p.yPos = 3;
			break;
		case 56:
			p.xPos = 9;
			p.yPos = 3;
			break;

		}
		return null;
	}

	// this method gets the start location for the players
	public Square getStart(){		
		//return path.get(0);
		return board [10][10];
	}

	// this method moves the player along the path one square at a time,
	// by subtracting 1 from the roll each move until the roll is zero.
	public void movePlayer(Player p, int roll) throws InterruptedException{		

		int temp;

		switch (p.location.pathindex)
		{
		case 39:
			temp = 0;
			break;		
		case 44:
			temp = 13;
			break;		
		case 54:
			temp = 27;
			break;		
		case 56:
			temp = 33;
			break;
		case 57:
			temp = 40;
			break;
		case 58:
			temp = 45;
			break;
		case 59:
			temp = 55;
			break;
		default:
			temp = p.location.pathindex+1;
			break;
		}

		getLocation(p, temp);
		p.location.players.remove(p);
		p.location = board[p.yPos][p.xPos];
		p.location.land(p, this, --roll);

	}

	// this method randomly places the star on one of the 14 Option Battle squares
	public void moveStar(){
		int random = ((int) (Math.random() * 14) + 1);

		if (restore)
		{
			switch (temp)
			{
			case 1:
				board[10][6] = new OptionBattle("Option Battle", 4, true);
				break;
			case 2:
				board[10][2] = new OptionBattle("Option Battle", 8, true);
				break;
			case 3:
				board[7][0] = new OptionBattle("Option Battle", 13, true);
				break;
			case 4:
				board[3][0] = new OptionBattle("Option Battle", 17, true);
				break;
			case 5:
				board[0][2] = new OptionBattle("Option Battle", 22, true);
				break;
			case 6:
				board[0][6] = new OptionBattle("Option Battle", 26, true);
				break;
			case 7:
				board[1][10] = new OptionBattle("Option Battle", 31, true);
				break;
			case 8:
				board[5][10] = new OptionBattle("Option Battle", 35, true);
				break;
			case 9:
				board[9][10] = new OptionBattle("Option Battle", 39, true);
				break;
			case 10:
				board[9][3] = new OptionBattle("Option Battle", 40, true);
				break;
			case 11:
				board[7][5] = new OptionBattle("Option Battle", 46, true);
				break;
			case 12:
				board[3][5] = new OptionBattle("Option Battle", 50, true);
				break;
			case 13:
				board[1][7] = new OptionBattle("Option Battle", 54, true);
				break;
			case 14:
				board[3][9] = new OptionBattle("Option Battle", 56, true);
				break;
			}
		}

		switch (random){
		case 1:
			board[10][6] = new Star("*****STAR*****", 4, true);
			restore = true;
			temp = 1;
			MarioPartyBoard.STARINDEX = 4;
			break;
		case 2:
			board[10][2] = new Star("*****STAR*****", 8, true);
			restore = true;
			temp = 2;
			MarioPartyBoard.STARINDEX = 8;
			break;
		case 3:
			board[7][0] = new Star("*****STAR*****", 13, true);
			restore = true;
			temp = 3;
			MarioPartyBoard.STARINDEX = 13;
			break;
		case 4:
			board[3][0] = new Star("*****STAR*****", 17, true);
			restore = true;
			temp = 4;
			MarioPartyBoard.STARINDEX = 17;
			break;
		case 5:
			board[0][2] = new Star("*****STAR*****", 22, true);
			restore = true;
			temp = 5;
			MarioPartyBoard.STARINDEX = 22;
			break;
		case 6:
			board[0][6] = new Star("*****STAR*****", 26, true);
			restore = true;
			temp = 6;
			MarioPartyBoard.STARINDEX = 26;
			break;
		case 7:
			board[1][10] = new Star("*****STAR*****", 31, true);
			restore = true;
			temp = 7;
			MarioPartyBoard.STARINDEX = 31;
			break;
		case 8:
			board[5][10] = new Star("*****STAR*****", 35, true);
			restore = true;
			temp = 8;
			MarioPartyBoard.STARINDEX = 35;
			break;
		case 9:
			board[9][10] = new Star("*****STAR****", 39, true);
			restore = true;
			temp = 9;
			MarioPartyBoard.STARINDEX = 39;
			break;
		case 10:
			board[9][3] = new Star("*****STAR*****", 40, true);
			restore = true;
			temp = 10;
			MarioPartyBoard.STARINDEX = 40;
			break;
		case 11:
			board[7][5] = new Star("*****STAR*****", 46, true);
			restore = true;
			temp = 11;
			MarioPartyBoard.STARINDEX = 46;
			break;
		case 12:
			board[3][5] = new Star("*****STAR*****", 50, true);
			restore = true;
			temp = 12;
			MarioPartyBoard.STARINDEX = 50;
			break;
		case 13:
			board[1][7] = new Star("*****STAR*****", 54, true);
			restore = true;
			temp = 13;
			MarioPartyBoard.STARINDEX = 54;
			break;
		case 14:
			board[3][9] = new Star("*****STAR*****", 56, true);
			restore = true;
			temp = 14;
			MarioPartyBoard.STARINDEX = 56;
			break;
		}

	}

	// self explanatory this draws the board
	public void draw(){
		System.out.println();
		for (int x = 0; x < board.length; x++)
		{
			for (int y = 0; y < board[x].length; y++)			
				System.out.print(board[x][y].DrawFirstLine());
			
			System.out.println();
			
			for (int y = 0; y < board[x].length; y++)			
				System.out.print(board[x][y].DrawSecondLine());
			
			System.out.println();
			
			for (int y = 0; y < board[x].length; y++)			
				System.out.print(board[x][y].DrawFifthLine());
			
			System.out.println();
		}
		System.out.println();
		System.out.println();


	}// end of draw method

	// this method updates the player info panels to show their Stars and Coins
	public void updateInfoPanels(Player p){

		if(p.name == "P1")
			board[5][7] = new Info("  Player 1", p.Coins, p.Stars, p.Wins, true);

		if(p.name == "P2")
			board[5][8] = new Info("  Player 2", p.Coins, p.Stars, p.Wins, true);

		if(p.name == "P3")
			board[7][7] = new Info("  Player 3", p.Coins, p.Stars, p.Wins, true);

		if(p.name == "P4")
			board[7][8] = new Info("  Player 4", p.Coins, p.Stars, p.Wins, true);
	}

	// this method update the star and round info panels to show the current location
	// of the star, how much the star costs, and the current round
	public void updateInfoPanels(){
		board[3][3] = new StarInfo("  Star Info", MarioPartyBoard.STARINDEX, Square.STARCOST, 0, true);
		board[3][2] = new Round("   ROUND", rounds, 0, 0, true);
	}

	// this method changes the Start square into a solo game square after the first round is over
	public void changeStartSquare(){
		if(!started){
			board[10][10] = new SoloGame("  Solo Game", 0, true);
			started = true;
		}		
	}// end of changeStartSquare method
	
}// end of MarioPartyBoard class