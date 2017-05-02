package assignment07;

/**
*Description: This program will build a FuelGauge class
*Class: Fall - COSC 1437.81003
*Assignment7: FuelGauge Class
*Date: 10/31/2015
*@author  Jeremy Pierce
*@version 0.0.0
*/ 
public class FuelGauge {
	
	public final int MAX_GALLONS = 15;
	private int gallons;
	
	/**
	* @param none
	* @return void
	* @throws Nothing is implemented
	*/
	public FuelGauge(){		
		gallons = 0;
	}// end of no argument constructor
	
	/**
	* @param int a g which is the number of gallons of gas
	* @return void
	* @throws Nothing is implemented
	*/
	public FuelGauge(int g){		
		gallons = g;
	}// end of parameter constructor
	
	/**
	* @param none
	* @return int as gallons which is the number of gallons of gas
	* @throws Nothing is implemented
	*/
	public int getGallons(){
		return gallons;
	}// end of getGallons method
	
	/**
	* @param none
	* @return void
	* @throws Nothing is implemented
	*/
	public void incrementGallons(){
		if(gallons >= MAX_GALLONS)return;
		gallons++;		
	}// end of incrementGallons method
	
	/**
	* @param none
	* @return void
	* @throws Nothing is implemented
	*/
	public void decrementGallons(){
		if(gallons <= 0)return;
		gallons--;
	}// end of decrementGallons method
	
	/**
	* @param none
	* @return boolean value
	* @throws Nothing is implemented
	*/
	public boolean hasGas(){
		if(gallons > 0) return true;
		
		return false;
	}// end of hasGas method
	
	/**
	* @param none
	* @return boolean value
	* @throws Nothing is implemented
	*/
	public boolean fullTank(){
		if(gallons >= MAX_GALLONS) return true;
		
		return false;
	}// end of fullTank method

}// end of FuelGauge class
