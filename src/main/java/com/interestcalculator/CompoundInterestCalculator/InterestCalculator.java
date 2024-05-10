package com.interestcalculator.CompoundInterestCalculator;

import java.util.Scanner;

public class InterestCalculator {
	/**
	 * Array of error messages with error code as index [See checkDouble()]
	 */
	private static String[] errorMsgs = {
			"Passed!",
			"You must input a number",
			"Your number cannot be zero or negative",
			"Your number must be 1 or larger",
	};
	
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
	protected static int checkDouble(String str) {
		double result;
		
		try {
			result = Double.parseDouble(str);
		} catch (Exception e) {
			return 1;
		}
		
		if (result <= 0) return 2;
		if (result < 1) return 3;
		
		return 0;
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
	protected static double takeInput(String msg, Scanner scnr, int... successCodes ) {
		double db;
		String temp;
		boolean pass = false;
		
		do {
			// Print message for user
			System.out.println(msg);
			
			temp = scnr.nextLine().strip();
			// Check if input is valid
			int result = checkDouble(temp);
			// Check if error codes are acceptable
			for (int x : successCodes) {
				if (x == result) {
					pass = true;
					break;
				}
			}
			// Print error message if failed
			if (!pass) System.out.println(errorMsgs[result]);
			// Restart loop to prompt for user input again
		} while (!pass);
		db = Double.parseDouble(temp);
		
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
		deposit = takeInput("Input your deposit amount in USD:", scnr, 0, 3);
		
		// Get rate of interest
		compoundRate = takeInput("How many times in one year is interest compounded?", scnr, 0, 3);
		
		// Get interest amount from console
		interest = takeInput("Input your interest rate in %:", scnr, 0, 3);
		
		// Get multiplication factor from console
		mfactor = takeInput("Input your multiplication factor:", scnr, 0);
		
		scnr.close();

		double years = calcInterest(mfactor, compoundRate, interest);
		System.out.println("It will take " + years + " years for $" + deposit 
				+ " to become $" + (deposit * mfactor) + " with a " + interest + "% interest.");
	}

}
 
