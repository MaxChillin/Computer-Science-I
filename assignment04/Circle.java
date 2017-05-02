package assignment04;

/**
*Description: This program will build the Circle class
*Class: Fall - COSC 1437.81003
*Assignment4: Circle class
*Date: 10/1/2015
*@author  Jeremy Pierce
*@version 0.0.0
*/ 
public class Circle {
	
	private double radius;
	private final double PI = Math.PI;
	
	public Circle(double circleRadius){
		radius = circleRadius;
	}// end of constructor

	public double getRadius() {
		return radius;
	}

	public void setRadius(double circleRadius) {
		radius = circleRadius;
	}
	
	public double getArea(){
		return PI * radius * radius;
	}
	
	public double getDiameter(){
		return radius * 2;
	}
	
	public double getCircumference(){
		return 2 * PI * radius;
	}

}// end of class
