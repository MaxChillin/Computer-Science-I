package assignment04;

import java.text.DecimalFormat;

/**
*Description: This program will build the Temperature class
*Class: Fall - COSC 1437.81003
*Assignment4: Temperature class
*Date: 10/1/2015
*@author  Jeremy Pierce
*@version 0.0.0
*/ 
public class Temperature {
	
	private double ftemp;
	
	/**
	* @param double as temp
	* @return none
	* @throws Nothing is implemented
	*/
	public Temperature(double temp){
		ftemp = temp;		
	}// end of constructor

	/**
	* @param none
	* @return temperature in Fahrenheit
	* @throws Nothing is implemented
	*/
	public double getFahrenheit() {
		return ftemp;
	}

	/**
	* @param double as temp
	* @return none
	* @throws Nothing is implemented
	*/
	public void setFahrenheit(double temp) {
		ftemp = temp;
	}
	
	/**
	* @param none
	* @return temperature in Celsius
	* @throws Nothing is implemented
	*/
	public String getCelsius() {
		DecimalFormat format = new DecimalFormat("##0.0");
		double temp = (ftemp - 32.0) * (5.0/9.0);
		return format.format(temp);
	}
	
	/**
	* @param none
	* @return temperature in Kelvin
	* @throws Nothing is implemented
	*/
	public String getKelvin() {
		DecimalFormat format = new DecimalFormat("##0.0");
		double temp = ((5.0 / 9.0) * (ftemp - 32.0)) + 273.0;
		return format.format(temp);
	}	

}// end of class
