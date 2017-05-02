package assignment08;

import java.text.DecimalFormat;

public class Payroll {	
	
	DecimalFormat format = new DecimalFormat("##0.00");
	final int[] employeeId = {5658845, 4520125, 7895122, 8777541, 8451277, 1302850, 7580489};
	
	private int[] hours = new int[7];
	private double[] payRate = new double[7];
	private double[] wages = new double[7];
	
	public void setHours(int Id, int hours){
		for(int i = 0; i < employeeId.length; i++){
			if(employeeId[i] == Id){
				 this.hours[i] = hours;
				 wages[i] = this.hours[i] * payRate[i];
				 return;
			}// end of if statement
			wages[i] = this.hours[i] * payRate[i];
		}// end of for loop
	}// end of setHours method
	
	public void setPayRate(int Id, double payRate){
		for(int i = 0; i < employeeId.length; i++){
			if(employeeId[i] == Id){
				 this.payRate[i] = payRate;
				 wages[i] = hours[i] * this.payRate[i];
				 return;
			}// end of if statement
			wages[i] = hours[i] * this.payRate[i];
		}// end of for loop
	}// end of setHours method
	
	public int getHours(int Id){
		int hours = 0;
		boolean found = false;
		
		for(int i = 0; i < employeeId.length; i++){
			if(employeeId[i] == Id){
				 hours = this.hours[i];
				 found = true;
				 break;
			}// end of if statement
		}// end of for loop
		
		if(!found) System.out.println("The Id You Entered Is Not Valid.");
		
		return hours;
	}// end of getHours method
	
	public String getPayRate(int Id){
		double payRate = 0;
		boolean found = false;
		
		for(int i = 0; i < employeeId.length; i++){
			if(employeeId[i] == Id){
				 payRate = this.payRate[i];
				 found = true;
				 break;
			}// end of if statement
		}// end of for loop
		
		if(!found) System.out.println("The Id You Entered Is Not Valid.");
		
		return format.format(payRate);
	}// end of getPayRate method
	
	public String getGrossPay(int Id){
		double grossPay = 0.0;
		boolean found = false;
		
		for(int i = 0; i < employeeId.length; i++){
			if(employeeId[i] == Id){
				 grossPay = this.wages[i];
				 found = true;
				 break;
			}// end of if statement
		}// end of for loop
		
		if(!found) System.out.println("The Id You Entered Is Not Valid.");
		
		return format.format(grossPay);
	}// end of getGrossPay method	

}// end of class
