package com.interestcalculator.CompoundInterestCalculator;

import java.util.Scanner;

public class InterestCalculator {	
	/**
	 * Check if input fits requirements and returns an error code if a requirement fails:
	 *
	 * @param str The string whose contents to check against the requirements
	 * @return An error code corresponding to the errorMsgs array which describes
	 * 				the conditions the string has met:
	 * 				0 - All requirements passed
	 * 				1 - Is not a double (String or null)
	 * 				2 - Number is <= 0
	 * 				3 - Number is < 1
	 */
	protected static double checkAndParseDouble(String str) {
		double result;
		
		try {
			result = Double.parseDouble(str);
		} catch (Exception e) {
			throw new RuntimeException("You must input a number");
		}
		
		if (result <= 0) throw new RuntimeException("Your number cannot be zero or negative");
		
		return Double.parseDouble(str);
	}

	/**
	 * Takes user input and checks if it follows the desired requirements.
	 * 		If requirements not met, prints error message and prompts user again.
	 * 
	 * @param msg A string containing the message to prompt the user with
	 * @param scnr A scanner object from which to take input
	 * @param successCodes	int representing which error code from checkDouble is 
	 * 							allowed to consider succesful input
	 * 
	 * @return
	 */
	protected static double takeInput(String msg, Scanner scnr, boolean isMfactor ) {
		double db = 0;
		String temp;
		boolean pass = false;
		
		do {
			// Print message for user
			System.out.println(msg);
			
			temp = scnr.nextLine().strip();
			
			try {
				// Check if input is valid
				db = checkAndParseDouble(temp);
				if (isMfactor && db < 1) {
					System.out.println("The multiplication factor cannot be less than 1.");
				} else {
					pass= true;
				}
			} catch(RuntimeException e) {
				System.out.println(e.getMessage());
			}
			// Restart loop to prompt for user input again
		} while (!pass);
		
		return db;
	}
	
	/**
	 * Calculates the required interest to reach a final amount using the multiplication factor
	 *		rate of compounding interest, and the annual interest rate.
	 *
	 * @param mfactor A double for the multiplication factor to multiply with the original deposit
	 * @param compoundRate A double for the amount of times interest is compounded in a year
	 * @param interest A double for the annual interest rate in %
	 * @return
	 */
	protected static double calcInterest(double mfactor, double compoundRate, double interest) {
		// Calculate the number of years using compound interest formula:
		// 		D = initial deposit
		//		m = multiplication factor
		//		y = number of years
		//		n = number of times interest applied during one year
		//		i = interest (1 + interest rate / n)
		//
		//		D*m = D*i^(ny) (Multiply interest every year to get final deposit amount)
		//		m = i^(ny)
		//		y = (log_i m)/n, apply change of base formula => y = log m/nlog i
		return Math.ceil(Math.log(mfactor)/(compoundRate * Math.log(1+(interest/100)/compoundRate)));
	}

	/**
	 * Main function. Takes in input deposit amount, annual interest, and 
	 * 		multiplication factor. Will print to console the number of years
	 *		it will take for annual interest to raise the initial deposit
	 *		to the deposit x multiplication factor.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		double deposit;
		double interest;
		double mfactor;
		double compoundRate;
		
		// Get deposit amount from console
		deposit = takeInput("Input your deposit amount in USD:", scnr, false);
		
		// Get rate of interest
		compoundRate = takeInput("How many times in one year is interest compounded?", scnr, false);
		
		// Get interest amount from console
		interest = takeInput("Input your interest rate in %:", scnr, false);
		
		// Get multiplication factor from console
		mfactor = takeInput("Input your multiplication factor:", scnr, true);
		
		scnr.close();

		double years = calcInterest(mfactor, compoundRate, interest);
		System.out.println("It will take " + years + " years for $" + deposit 
				+ " to become $" + (deposit * mfactor) + " with a " + interest + "% interest.");
	}

}
 
