package assignment07;

/**
*Description: This program will build a Dashboard class then make an object of the FuelGauge class 
*then make an object of the Odometer class and passes the FuelGauge object as a parameter to the Odometer object
*Class: Fall - COSC 1437.81003
*Assignment7: Dashboard Class
*Date: 10/31/2015
*@author  Jeremy Pierce
*@version 0.0.0
*/ 
public class Dashboard {

	/**
	* @param String as args
	* @return void
	* @throws Nothing is implemented
	*/
	public static void main(String[] args) {
		
		FuelGauge fuelGauge = new FuelGauge();
		Odometer odometer = new Odometer(0, fuelGauge);		

		while (!fuelGauge.fullTank()) {
			fuelGauge.incrementGallons();
			System.out.println("You have " + fuelGauge.getGallons() + " gallons of gas.");
		}// end of while loop to fill the gas tank

		while (fuelGauge.hasGas()) {
			odometer.incrementMileage();
			System.out.println("You have driven " + odometer.getMileage() + " miles," + "\nand you have "
					+ fuelGauge.getGallons() + " gallons of gas remaining.");
		}// end of while loop to drive until out of gas

	}// end of main method

}// end of Dashboard class
