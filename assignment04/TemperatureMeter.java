package assignment04;

/**
*Description: This program will build an object from the Temperature class
*Class: Fall - COSC 1437.81003
*Assignment4: TemperatureMeter class for the Temperature class
*Date: 10/1/2015
*@author  Jeremy Pierce
*@version 0.0.0
*/ 
public class TemperatureMeter {

	/**
	* @param String as args
	* @return none
	* @throws Nothing is implemented
	*/
	public static void main(String[] args) {
		
		Temperature temperature1 = new Temperature(78.0);
		Temperature temperature2 = new Temperature(178.0);
		
		System.out.println("The Temperature1 in Fahrenheit is " + temperature1.getFahrenheit() + " degrees.");
		System.out.println("The Temperature1 in Celsius is " + temperature1.getCelsius() + " degrees.");
		System.out.println("The Temperature1 in Kelvin is " + temperature1.getKelvin() + " degrees.");
		
		System.out.println("The Temperature2 in Fahrenheit is " + temperature2.getFahrenheit() + " degrees.");
		System.out.println("The Temperature2 in Celsius is " + temperature2.getCelsius() + " degrees.");
		System.out.println("The Temperature2 in Kelvin is " + temperature2.getKelvin() + " degrees.");

	}// end of main

}// end of class
