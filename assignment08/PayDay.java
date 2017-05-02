package assignment08;

import java.util.Scanner;

public class PayDay {

	public static void main(String[] args) {
		
		int hours = -1;
		double payRate = -1.0;
		String temp;
		
		Payroll payroll = new Payroll();
		Scanner input = new Scanner(System.in);
		
		for(int i = 0; i < payroll.employeeId.length; i++){
			do{
				System.out.println("Please Enter The Hours Worked For Employee " + payroll.employeeId[i] + " : ");
				temp = input.nextLine();
				hours = Integer.parseInt(temp);
				if(hours < 0) System.out.println("You Enter An Invalid Number.");
			}while(hours < 0);
			payroll.setHours(payroll.employeeId[i], hours);			
			
			do{
				System.out.println("Please Enter The Pay Rate For Employee " + payroll.employeeId[i] + " : ");
				temp = input.nextLine();
				payRate = Double.parseDouble(temp);
				if(payRate < 7.25) System.out.println("You Enter An Invalid Number.");
			}while(payRate < 7.25);
			payroll.setPayRate(payroll.employeeId[i], payRate);
			
		}// end of first for loop
		
		for(int i = 0; i < payroll.employeeId.length; i++){
			System.out.println(payroll.employeeId[i] + " Wages: $" + payroll.getGrossPay(payroll.employeeId[i]) + 
					"\t\tHours: " + payroll.getHours(payroll.employeeId[i]) + "\tPay Rate: $" + payroll.getPayRate(payroll.employeeId[i]));
		}// end of second for loop

	}// end of main

}// end of class
