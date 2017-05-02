package assignment06;

import java.util.Random;


/**
*Description: This program will build a Die class
*Class: Fall - COSC 1437.81003
*Assignment6: Die Class
*Date: 10/22/2015
*@author  Jeremy Pierce
*@version 0.0.0
*/ 
public class Die{
   private int sides;   // Number of sides
   private int value;   // The die's value
   
   
   /**
	* @param int as numSides which is the number of how many sides you want each die to have
	* @return void
	* @throws Nothing is implemented
	*/
   public Die(int numSides){
      sides = numSides;
      roll();
   }// end of Die constructor
   
   
   /**
	* @param none
	* @return void
	* @throws Nothing is implemented
	*/
   public void roll(){
      // Create a Random object.
      Random rand = new Random();
      
      // Get a random value for the die.
      value = rand.nextInt(sides) + 1;
   }// end of roll method
   
  
   /**
  	* @param none
  	* @return int as the number of sides each die has
  	* @throws Nothing is implemented
  	*/
   public int getSides(){
      return sides;
   }// end of getSides method
   
  
   /**
  	* @param none
  	* @return int as the value of the die from the last roll.
  	* @throws Nothing is implemented
  	*/
   public int getValue(){
      return value;
   }// end of getValue method
   
}// end of class