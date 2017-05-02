package assignment06;

import java.util.ArrayList;
import java.util.Scanner;

/**
*Description: This program will build a GameOf21 class. It will simulate the game of 
*BlackJack but it will use two objects from the Die class and roll dice instead of cards.
*Class: Fall - COSC 1437.81003
*Assignment6: GameOf21 Class
*Date: 10/22/2015
*@author  Jeremy Pierce
*@version 0.0.0
*/ 
public class GameOf21 {

	 /**
		* @param String as args
		* @return void
		* @throws Nothing is implemented
		*/
	public static void main(String[] args) {
		
		Die firstDie = new Die(6);
		Die secondDie = new Die(6);
		java.util.Scanner input = new Scanner(System.in);
		ArrayList<String> invalidEntry = new ArrayList<String>();
		invalidEntry.add("\n\nYou Have Enter an Invalid Value.\n\n");
		invalidEntry.add("\n\nInvalid Entry.\n\n");
		invalidEntry.add("\n\n...Um...Invalid Entry.\n\n");
		invalidEntry.add("\n\nHey Dumb Dumb Is Your Brain On?\n\n");
		invalidEntry.add("\n\n...\n\n");
		invalidEntry.add("\n\nOk Look Bozo! This Is Your Last Chance.\n\n");
		int invalidEntryCounter = 0;
		
		int playerHand;
		int dealerHand;
		String choice;
		boolean gameLoop = true;
		boolean endGameLoop = true;
		
		while(true){
			
			playerHand = roll(firstDie, secondDie);			
			dealerHand = roll(firstDie, secondDie);

			while(gameLoop){			

				System.out.print("You currently have " + playerHand
						+ "\nWould you like to HIT or STAY? (H/S): ");
				choice = input.nextLine();

				switch(choice){
				case "h":
				case "H":
					playerHand += roll(firstDie, secondDie);
					if(playerHand > 21) gameLoop = gameOver(playerHand, dealerHand);
					break;
				case "s":
				case "S":
					while(dealerHand < 16){						
						dealerHand += roll(firstDie, secondDie);
					}
					gameLoop = gameOver(playerHand, dealerHand);					
					break;
				default:
					if(invalidEntryCounter > invalidEntry.size() - 1){
						System.out.println("\n\nGoodbye!");
						System.exit(0);
					}
					System.out.println(invalidEntry.get(invalidEntryCounter++));
				}// end of switch

			}// end of game while loop
			invalidEntryCounter = 0;
			
			gameLoop = true;
			playerHand = dealerHand = 0;
			invalidEntryCounter = 0;			
				while(endGameLoop){
					System.out.print("Would You Like To Play Again? Y/N: ");
					choice = input.nextLine();

					switch(choice){
					case "n":
					case "N":
						System.exit(0);
					case "y":
					case "Y":
						endGameLoop = false;
						break;
					default:
						if(invalidEntryCounter > invalidEntry.size() - 1){
							System.out.print("\n\nGoodbye!");
							System.exit(0);
						}
						System.out.println(invalidEntry.get(invalidEntryCounter++));						
					}// end of switch
					
				}// end of endGame while loop
				invalidEntryCounter = 0;
				endGameLoop = true;
			
		}// end of first while loop
		
	}// end of main
	
	 /**
		* @param int as player which hold the total of the player's hand
		* @param int as dealer which hold the total of the dealer's hand
		* @return boolean
		* @throws Nothing is implemented
		*/
	public static boolean gameOver(int player, int dealer){
		String winnerLoser = "Loser!";
		
		if(dealer > 21 || (player > dealer && player <= 21)){
			winnerLoser = "Winner!";
				
		}// end of if statement
		
		if(player == dealer) winnerLoser = "Draw!";
		
		System.out.println(winnerLoser + 
				"\nPlayer's Hand: " + player +
				"\nDealer's Hand: " + dealer);	
		
		return false;
	}// end of gameOver method
	
	/**
	* @param Die as die1 which hold the value of the first die
	* @param Die as die2 which hold the value of the second die
	* @return int as the total of the two dice rolled
	* @throws Nothing is implemented
	*/
	public static int roll(Die die1, Die die2){
		die1.roll();
		die2.roll();
		return  die1.getValue() + die2.getValue();
	}// end of roll method

}// end of class
