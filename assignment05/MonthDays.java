package assignment05;


/**
*Description: This program will build a MonthDays class
*Class: Fall - COSC 1437.81003
*Assignment5: MonthDays Class
*Date: 10/8/2015
*@author  Jeremy Pierce
*@version 0.0.0
*/ 
public class MonthDays {
	
	private int month;
	private int year;
	
	/**
	* @param myMonth as 1-12 number representation of the month chosen
	* @param myYear as number representation of the year chosen
	* @return none
	* @throws Nothing is implemented
	*/
	public MonthDays(int myMonth, int myYear){
		month = myMonth;
		year = myYear;
	}
	
	/**
	* @param none
	* @return int as number of days in the month
	* @throws Nothing is implemented
	*/
	public int getNumberOfDays(){
		int temp = 0;
		switch(month){
		case 2:// February
			if(year % 4 == 0)temp = 29;
			else temp = 28;
			break;
		case 4:// April
		case 6: // June
		case 9:// September
		case 11:// November
			temp = 30;
			break;
		default:
			temp = 31;
			break;
		}
		return temp;
	}

}
