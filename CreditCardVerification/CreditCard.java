package finalProject;

/**
*Description: This Class will create objects to hold the names and numbers for each credit card that is validated
*Class: Fall - COSC 1437.81003
*AssignmentFinalProject: CreditCard
*Date: 12/15/2015
*@author  Jeremy Pierce
*@version 0.0.0
*/ 
public class CreditCard {
	
	private String cardProvider;
	private String cardNumber;
	
	
	public CreditCard(){
		cardProvider = "";
		cardNumber = "";
	}
	
	/**
	 * 
	 * @param cardNumber The string version of the card number
	 */
	public CreditCard(String cardNumber){
		this.cardNumber = cardNumber;
		cardProvider = this.identifyProvider(cardNumber);
	}
	
	/**
	 * 
	 * @param cardProvider This is the name of the company that issued the card Ex: Visa, MasterCard etc.
	 * @param cardNumber This is the credit card number
	 */
	public CreditCard(String cardProvider, String cardNumber){
		this.cardProvider = cardProvider;
		this.cardNumber = cardNumber;	
	}
	
	/**
	 * 
	 * @param cardNumber The string version of the card number
	 * @return String Returns the name of the card provider EX: Visa, MasterCard etc.
	 */
	public String identifyProvider(String cardNumber){
		String firstTwoDigits = cardNumber.substring(0, 2);
		
		switch(firstTwoDigits){
		case "44":
		case "45":
			return "Visa";
		case "51":
		case "53":
			return "MasterCard";
		case "37":
		case "34":
			return "American Express";
		case "60":
			return "Discover";
		case "31":
		case "33":
			return "JCB";
		case "54":
		case "55":
			return "Diners Club North America";
		case "30":
			return "Diners Club Carte Blanche";
		case "36":
			return "Diners Club International";
		case "58":
			return "Maestro";
		case "67":
			return "LASER";
		case "48":
		case "49":
			return "Visa Electron";
		case "63":
			return "InstaPayment";
			default:
				return "";
		}// end of switch
		
	}// end of identifyProvider method
	
	/**
	 * 
	 * @return boolean Returns true if the card number is valid otherwise it returns false
	 */
	public boolean isValid(){
		if(this.cardNumber.length() < 13 || this.cardNumber.length() > 19){			
			return false;
		}// check to see if card length is within range			
		
		String temp = this.cardNumber.substring(0, this.cardNumber.length()-1);
		StringBuilder temp2;
		int checkDigit, digit, sum = 0;		
		
		checkDigit = Character.getNumericValue(this.cardNumber.charAt(this.cardNumber.length()-1));		
		temp2 = new StringBuilder(temp);
		temp2.reverse();
		
		for(int i = 0; i < temp2.length(); i++){
			digit = Character.getNumericValue(temp2.charAt(i));
			for(int j = 0; j < temp2.length(); j++){// to check for odd digits
				if(i == (2*j)){						
					digit *= 2;
					if(digit >= 10){
						digit %= 10;
						digit++;
					}
					else{
						digit %= 10;
					}
				}// end of if statement to check for odd digits					
				
			}// end of second for loop
			
			sum += digit;
			
		}// end of for loop
		sum += checkDigit;
		if((sum) % 10 == 0) return true;		
		
		return false;
	}// end of isValid method
	
	/**
	 * @return String Returns a string with the name and the number of the card
	 */
	public String toString(){		
		return "" + cardNumber + "  \t" + cardProvider;
	}// end of toString method

}// end of class