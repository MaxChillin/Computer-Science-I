package assignment05;

import java.util.Scanner;

/**
*Description: This program will build an object from the MonthDays class and calculate the number
*			of days
*Class: Fall - COSC 1437.81003
*Assignment5: DayFinder class 
*Date: 10/8/2015
*@author  Jeremy Pierce
*@version 0.0.0
*/ 
public class DayFinder {
	
	/**
	* @param String as args
	* @return none
	* @throws Nothing is implemented
	*/
	public static void main(java.lang.String[] args){
		
		int month;
		int year;
		Scanner input = new Scanner(java.lang.System.in);
		MonthDays monthDays;
		
		java.lang.System.out.print("Please Enter a Month (1-12): ");
		month = input.nextInt();
		input.nextLine();
		
		java.lang.System.out.print("Please Enter a Year: ");
		year = input.nextInt();
		input.nextLine();
		
		monthDays = new MonthDays(month, year);
		
		java.lang.System.out.println(monthDays.getNumberOfDays() + " days");
		
	}

}
