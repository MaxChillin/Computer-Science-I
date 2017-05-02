package assignment09;


/**
*Description: This program will build a MorseCode class
*Class: Fall - COSC 1437.81003
*Assignment9: MorseCode Class
*Date: 11/24/2015
*@author  Jeremy Pierce
*@version 0.0.0
*/ 
public class MorseCode {
	
	private char[] charList = {' ',',','.','?','0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	private String[] morseCode = {" ","--..--",".-.-.-","..--..","-----",".----","..---","...--","....-",".....","-....","--...","---..","----.",".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."};
	
	/**
	* @param english as String
	* @return String
	* @throws Nothing is implemented
	*/
	public String toMorse(String english){
		String temp = "";
		int asciiOne, asciiTwo, counter = 0;
		char[] charArray = english.toUpperCase().toCharArray();
		char c;
		
		while(counter < charArray.length){
			asciiOne = charArray[counter];
			for(int i = 0; i < charList.length; i++){
				asciiTwo = charList[i];
				if(asciiTwo == asciiOne){
					temp += morseCode[i];
				}
			}// end of for loop
			counter++;
		}// end of while loop		
		
		return temp;
	}// end of toMorse method	

}// end of class
