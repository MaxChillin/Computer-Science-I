package assignment07;

/**
*Description: This program will build an Odometer class
*Class: Fall - COSC 1437.81003
*Assignment7: Odometer Class
*Date: 10/31/2015
*@author  Jeremy Pierce
*@version 0.0.0
*/ 
public class Odometer {
	
	public final int MAX_MILEAGE = 999999;
	public final int MPG = 24;
	private int mileage;
	private FuelGauge fuelGauge;
	
	/**
	* @param int as mileage which is the number of miles driven
	* @param FuelGauge as fg which is an object of the FuelGauge class
	* @return void
	* @throws Nothing is implemented
	*/
	public Odometer(int mileage, FuelGauge fg){
		this.mileage = mileage;
		fuelGauge = fg;		
	}// end of parameter constructor
	
	/**
	* @param none
	* @return int as mileage
	* @throws Nothing is implemented
	*/
	public int getMileage(){
		return mileage;
	}// end of getMileage method
	
	/**
	* @param none
	* @return void
	* @throws Nothing is implemented
	*/
	public void incrementMileage(){
		if(mileage >= MAX_MILEAGE){
			mileage = 0;
			fuelGauge.decrementGallons();			
			return;
		}else{
			if(mileage != 0 && mileage % MPG == 0) fuelGauge.decrementGallons();
			mileage++;
		}
	}// end of incrementMileage method

}// end of Odometer class
