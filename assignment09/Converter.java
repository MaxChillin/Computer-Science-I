package assignment09;

import java.util.Scanner;

/**
*Description: This program will build a Converter class to convert English into morse code
*Class: Fall - COSC 1437.81003
*Assignment9: Converter Class
*Date: 11/24/2015
*@author  Jeremy Pierce
*@version 0.0.0
*/ 
public class Converter {

	/**
	* @param String as args
	* @return void
	* @throws Nothing is implemented
	*/
	public static void main(String[] args) {

		MorseCode morseCode = new MorseCode();
		Scanner keyboard = new Scanner(System.in);
		String morse, input;
		boolean loop = true;
		
		System.out.println("Hello And Thank You For Using The Morse Code Translater!");
		
		while(loop){
			System.out.print("\n\nPlease Enter A Sentence: ");
			morse = morseCode.toMorse(keyboard.nextLine());
			
			System.out.println(morse);
			
			System.out.print("\n\nWould You Like To Translate Another Sentence? N/Y: ");
			input = keyboard.nextLine();
			if(input.equalsIgnoreCase("N")) loop = false;
		}// end of while loop		
		System.out.println("\n\n\nThank You For Using The Morse Code Translater! \nHave A Wonderful Day!");
	}// end of main

}// end of class
