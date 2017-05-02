package assignment04;

/**
*Description: This program will build a Car class
*Class: Fall - COSC 1437.81003
*Assignment4: Car Class
*Date: 10/1/2015
*@author  Jeremy Pierce
*@version 0.0.0
*/ 
public class Car {
	
	private int yearModel, speed;
	private String make;
	
	/**
	* @param int as yearOfCar, String as makeOfCar
	* @return none
	* @throws Nothing is implemented
	*/
	public Car(int yearOfCar, String makeOfCar){
		
		yearModel = yearOfCar;
		make = makeOfCar;
		speed = 0;
		
	}// end of constructor
	
	/**
	* @param none
	* @return none
	* @throws Nothing is implemented
	*/
	public void accelerate(){
		
		speed += 5;
	}
	
	/**
	* @param none
	* @return none
	* @throws Nothing is implemented
	*/
	public void brake(){
		
		speed -= 5;
	}

	/**
	* @param none
	* @return none
	* @throws Nothing is implemented
	*/
	public int getYearModel() {
		return yearModel;
	}

	/**
	* @param none
	* @return none
	* @throws Nothing is implemented
	*/
	public int getSpeed() {
		return speed;
	}

	/**
	* @param none
	* @return none
	* @throws Nothing is implemented
	*/
	public String getMake() {
		return make;
	}
	
	

}// end of class
