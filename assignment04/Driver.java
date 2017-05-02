package assignment04;

/**
*Description: This is the Driver
*Class: Fall - COSC 1437.81003
*Assignment4: Driver class for the Car class
*Date: 10/1/2015
*@author  Jeremy Pierce
*@version 0.0.0
*/ 
public class Driver {

	/**
	* @param String as args
	* @return none
	* @throws Nothing is implemented
	*/
	public static void main(String[] args) {
		
		Car car1 = new Car(1982, "Corvette");
		
		for(int i = 0; i < 5; i++){
			car1.accelerate();
			System.out.println(car1.getSpeed());
		}// end of for loop
		
		for(int i = 0; i < 5; i++){
			car1.brake();
			System.out.println(car1.getSpeed());
		}// end of for loop
		

	}

}
