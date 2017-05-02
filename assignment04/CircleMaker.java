package assignment04;

import java.util.Scanner;

/**
*Description: This program will build an object from the Circle class
*Class: Fall - COSC 1437.81003
*Assignment4: CircleMaker class for the Circle class
*Date: 10/1/2015
*@author  Jeremy Pierce
*@version 0.0.0
*/ 
public class CircleMaker {

	/**
	* @param String as args
	* @return none
	* @throws Nothing is implemented
	*/
	public static void main(String[] args) {
		
		double temp;
		Scanner input = new Scanner(System.in);
		
		System.out.print("Please Enter the size of the radius: ");
		temp = input.nextDouble();
		input.nextLine();
		
		Circle circle = new Circle(temp);
		
		System.out.println("The area of the circle is " + circle.getArea());
		System.out.println("The diameter of the circle is " + circle.getDiameter());
		System.out.println("The circumference of the circle is " + circle.getCircumference());
		

	}// end of main

}// end of class