package marioParty;

public class MarioParty{

	public static void main(String[] args) throws InterruptedException {		
		MarioPartyBoard GameBoard = new MarioPartyBoard();

		Player[] allPlayers = new Player[3];
		allPlayers[0] = new Player(GameBoard.getStart(), "P1");
		allPlayers[1] = new Player(GameBoard.getStart(), "P2");
		allPlayers[2] = new SmartPlayer(GameBoard.getStart(), "P3");		

		System.out.println("Instructions:");
		System.out.println("Players Take turns moving around the board. Each space the player lands on either adds or removes a certain amount of coins from that player."
				+ "\nThe length of the game is determined by a fixed number or rounds. After each round all players partisipate in a group battle for extra coins. "
				+ "\nIf players land on the same square they have a player battle where the winner takes coins from the loser. The over all goal of the game is to get Stars."
				+ "\nStars are bought with coins when a player lands on the Star Square. Once a player buys a star the star's location changes and the price increases."
				+ "\nAt the end of all the rounds the player with the most stars wins. If players have the same amount of stars then the player with the most coins wins.");		

		Thread.sleep(10000);		

		GameBoard.moveStar();
		GameBoard.updateInfoPanels();
		GameBoard.draw();		
		for (int i = 0; i < MarioPartyBoard.MAX_ROUNDS; i++)
		{
			for (int j = 0; j < allPlayers.length; j++)
			{
				GameBoard.movePlayer(allPlayers[j], allPlayers[j].roll());
				if(allPlayers[j].Coins < 0)
					allPlayers[j].Coins = 0;				
				GameBoard.updateInfoPanels(allPlayers[j]);
				GameBoard.updateInfoPanels();
			}
			GameBoard.rounds++;
			GameBoard.changeStartSquare();
			GameBoard.groupBattle(allPlayers[0], allPlayers[1], allPlayers[2]);
			for(int k = 0; k < allPlayers.length; k++)
				GameBoard.updateInfoPanels(allPlayers[k]);
			GameBoard.updateInfoPanels();
		}

		GameBoard.rounds-=1;		
		for (int i = 0; i < allPlayers.length; i++)
		{
			GameBoard.updateInfoPanels(allPlayers[i]);
			GameBoard.updateInfoPanels();
			GameBoard.draw();						

		}

		if (allPlayers[0].Stars >= allPlayers[1].Stars)
		{
			if (allPlayers[0].Stars >= allPlayers[2].Stars)
			{
				if (allPlayers[0].Stars == allPlayers[1].Stars)
				{
					if (allPlayers[0].Stars == allPlayers[2].Stars)
					{
						if (allPlayers[0].Coins > allPlayers[1].Coins)
						{
							if (allPlayers[0].Coins > allPlayers[2].Coins)
							{
								System.out.println(allPlayers[0].name + " is the WINNER!!!!!!");
								System.out.println(allPlayers[0].name + " is the WINNER!!!!!!");
								System.out.println(allPlayers[0].name + " is the WINNER!!!!!!");
							}else
							{
								System.out.println(allPlayers[2].name + " is the WINNER!!!!!!");
								System.out.println(allPlayers[2].name + " is the WINNER!!!!!!");
								System.out.println(allPlayers[2].name + " is the WINNER!!!!!!");
							}
						}else if (allPlayers[1].Coins > allPlayers[2].Coins)
						{
							System.out.println(allPlayers[1].name + " is the WINNER!!!!!!");
							System.out.println(allPlayers[1].name + " is the WINNER!!!!!!");
							System.out.println(allPlayers[1].name + " is the WINNER!!!!!!");
						}else
						{
							System.out.println(allPlayers[2].name + " is the WINNER!!!!!!");
							System.out.println(allPlayers[2].name + " is the WINNER!!!!!!");
							System.out.println(allPlayers[2].name + " is the WINNER!!!!!!");
						}
					} else if (allPlayers[0].Coins > allPlayers[1].Coins)
					{
						System.out.println(allPlayers[0].name + " is the WINNER!!!!!!");
						System.out.println(allPlayers[0].name + " is the WINNER!!!!!!");
						System.out.println(allPlayers[0].name + " is the WINNER!!!!!!");
					}else
					{
						System.out.println(allPlayers[1].name + " is the WINNER!!!!!!");
						System.out.println(allPlayers[1].name + " is the WINNER!!!!!!");
						System.out.println(allPlayers[1].name + " is the WINNER!!!!!!");
					}
				} else if (allPlayers[0].Stars == allPlayers[2].Stars)
				{
					if (allPlayers[0].Coins > allPlayers[2].Coins)
					{
						System.out.println(allPlayers[0].name + " is the WINNER!!!!!!");
						System.out.println(allPlayers[0].name + " is the WINNER!!!!!!");
						System.out.println(allPlayers[0].name + " is the WINNER!!!!!!");
					} else
					{
						System.out.println(allPlayers[2].name + " is the WINNER!!!!!!");
						System.out.println(allPlayers[2].name + " is the WINNER!!!!!!");
						System.out.println(allPlayers[2].name + " is the WINNER!!!!!!");
					}
				}else
				{
					System.out.println(allPlayers[0].name + " is the WINNER!!!!!!");
					System.out.println(allPlayers[0].name + " is the WINNER!!!!!!");
					System.out.println(allPlayers[0].name + " is the WINNER!!!!!!");
				}							
			} else
			{
				System.out.println(allPlayers[2].name + " is the WINNER!!!!!!");
				System.out.println(allPlayers[2].name + " is the WINNER!!!!!!");
				System.out.println(allPlayers[2].name + " is the WINNER!!!!!!");
			}
		} else if (allPlayers[1].Stars >= allPlayers[2].Stars)
		{
			if (allPlayers[1].Stars == allPlayers[2].Stars)
			{
				if (allPlayers[1].Coins > allPlayers[2].Coins)
				{
					System.out.println(allPlayers[1].name + " is the WINNER!!!!!!");
					System.out.println(allPlayers[1].name + " is the WINNER!!!!!!");
					System.out.println(allPlayers[1].name + " is the WINNER!!!!!!");
				}
				else
				{
					System.out.println(allPlayers[2].name + " is the WINNER!!!!!!");
					System.out.println(allPlayers[2].name + " is the WINNER!!!!!!");
					System.out.println(allPlayers[2].name + " is the WINNER!!!!!!");
				}
			}else
			{
				System.out.println(allPlayers[1].name + " is the WINNER!!!!!!");
				System.out.println(allPlayers[1].name + " is the WINNER!!!!!!");
				System.out.println(allPlayers[1].name + " is the WINNER!!!!!!");
			}
		}else
		{
			System.out.println(allPlayers[2].name + " is the WINNER!!!!!!");
			System.out.println(allPlayers[2].name + " is the WINNER!!!!!!");
			System.out.println(allPlayers[2].name + " is the WINNER!!!!!!");
		}
	}// end of main
}// end of MarioParty class