package finalProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;

/**
*Description: This Class is the driver class for the final project. I reads credit card numbers and checks to see if they are valid
*Class: Fall - COSC 1437.81003
*AssignmentFinalProject: Validation
*Date: 12/15/2015
*@author  Jeremy Pierce
*@version 0.0.0
*/ 
public class Validation {

	/**
	 * 
	 * @param args
	 *            Command Line arguments
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		ArrayList<String> validNumbers = new ArrayList<String>();
		ArrayList<String> invalidNumbers = new ArrayList<String>();
		ArrayList<CreditCard> validCards = new ArrayList<CreditCard>();
		Scanner scanner = new Scanner(new File("src/finalProject/CardNumbersToCheck.txt"));
		String temp;
		CreditCard card;
		Object[] options = { "Input A Card Number", "Scan From A File"};
		ImageIcon icon1 = new ImageIcon("src/finalProject/Professor GanLaugh.png");
		ImageIcon icon2 = new ImageIcon("src/finalProject/Professor GanLaugh.png");
		ImageIcon icon3 = new ImageIcon("src/finalProject/Professor GanLaugh.png");
		JPanel panel = new JPanel();
		JTextPane textPane = new JTextPane();
		String display = "";

		int n;
		do {			
			n = JOptionPane.showOptionDialog(panel,
					"Greetings Mortals!\nI am here to validate your credit card numbers"
							+ "\nAnd if you are lucky I won't turn you into anything unnatural.\n\n\tMake Your Decision",
							"Professor GanLaugh's Credit Card Verifier", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE, icon1, options, options[1]);
		} while (n == -1);

		if (n == 0) {
			temp = JOptionPane.showInputDialog(panel, "Enter The Card Number", "Enter Number Here");
			card = new CreditCard(temp);
			if (card.isValid()) {
				JOptionPane.showMessageDialog(panel, "Mortal! The Number You Have Entered Is Valid!\n" + card.toString(), "Congratulation!",
						JOptionPane.INFORMATION_MESSAGE, icon2);
			} else
				JOptionPane.showMessageDialog(panel, "Mortal! The Number You Have Entered Is Invalid!", "Failure!",
						JOptionPane.WARNING_MESSAGE, icon3);

		} else if (n == 1) {

			while (scanner.hasNextLine()) {
				temp = scanner.nextLine();
				card = new CreditCard(temp);
				if (card.isValid())
					validNumbers.add(temp);
				else
					invalidNumbers.add(temp);
			} // end of while loop

			validNumbers = sortArray(validNumbers);
			invalidNumbers = sortArray(invalidNumbers);

			for (String s : validNumbers)
				validCards.add(new CreditCard(s));

			for (int i = 0; i < validCards.size(); i++)
				display += validCards.get(i).toString() + "\n";

			textPane.setText(display);
			panel.add(textPane);

			JOptionPane.showMessageDialog(panel, textPane, "Behold! The Valid Cards!", JOptionPane.PLAIN_MESSAGE, icon2);

			display = "";
			for (int i = 0; i < invalidNumbers.size(); i++)
				display += invalidNumbers.get(i) + "\n";

			textPane.setText(display);
			panel.add(textPane);

			JOptionPane.showMessageDialog(panel, textPane, "Invalid Numbers!", JOptionPane.PLAIN_MESSAGE, icon3);

		} else if (n == 2) {

		}

		scanner.close();
	}// end of main

	/**
	 * 
	 * @param list
	 *            The ArrayList object that you want to sort
	 * @return Returns the sorted ArrayList
	 */
	public static ArrayList<String> sortArray(ArrayList<String> list) {
		Collections.sort(list);
		return list;
	}// end of sortArray method

}// end of class
