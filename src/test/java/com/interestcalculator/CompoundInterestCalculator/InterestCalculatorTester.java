package com.interestcalculator.CompoundInterestCalculator;

import java.util.Scanner;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class InterestCalculatorTester {

	/**
	 * Tester method for checkDouble()
	 * 
	 * Checks for variations of valid input including different number sizes.
	 * Also checks for erroneous input. Including:
	 * 		null
	 * 		empty string
	 * 		0
	 * 		negative number
	 * 		normal string (not a valid double)
	 */
	@Test
	public void testCheckDouble() {
		// 1 Check if parsed double is properly returned upon valid input
		String errmsg1 = "error 1: checkDouble failed to return valid parsed double from string";
		// Ensure exception is not thrown
		try {
			// Normal double
			assertEquals(errmsg1, 5, InterestCalculator.checkAndParseDouble("5"), 0.001);
			// Medium double
			assertEquals(errmsg1, 50000, InterestCalculator.checkAndParseDouble("50000"), 0.001);
			// Large double
			assertEquals(errmsg1, 500000000, InterestCalculator.checkAndParseDouble("500000000"), 0.001);
			// Double with decimal point
			assertEquals(errmsg1, 5.45, InterestCalculator.checkAndParseDouble("5.45"), 0.001);
			// Small double
			assertEquals(errmsg1, 0.00001, InterestCalculator.checkAndParseDouble("0.00001"), 0.00000001);
		} catch (Exception e) {
			System.out.println("error 2: checkDouble should not throw an exception with valid input");
		}

		// 2 Check if exception is thrown with null input
		assertThrows("error 3: checkDouble failed to return 1 with null input"
				, RuntimeException.class, () -> InterestCalculator.checkAndParseDouble(null));
		
		// 3 Check if exception is thrown with empty string input
		assertThrows("error 4: checkDouble failed to return 1 with empty input"
				, RuntimeException.class, () -> InterestCalculator.checkAndParseDouble(""));
		
		// 4 Check if exception is thrown with non-double input
		String errmsg4 = "error 5: checkDouble failed to return 1 with non-double input (words and invalid numbers)";
		// Character string input
		assertThrows(errmsg4, RuntimeException.class, () -> InterestCalculator.checkAndParseDouble("hello"));
		assertThrows(errmsg4, RuntimeException.class, () -> InterestCalculator.checkAndParseDouble("aerghjuyt"));
		// Mix of characters and numbers
		assertThrows(errmsg4, RuntimeException.class, () -> InterestCalculator.checkAndParseDouble("dh8237yeg8d23o7y"));
		// Numbers but invalid double format
		assertThrows(errmsg4, RuntimeException.class, () -> InterestCalculator.checkAndParseDouble("50.0.0"));
		assertThrows(errmsg4, RuntimeException.class, () -> InterestCalculator.checkAndParseDouble("\'\"44"));
		
		// 5 Check if exception is thrown with input of 0
		assertThrows("error 5: checkDouble failed to return 2 with input 0"
				, RuntimeException.class, () -> InterestCalculator.checkAndParseDouble("0"));
		
		// 6 Check if exception is thrown with negative input
		assertThrows("error 6: checkDouble failed to return 2 with input -1000"
				, RuntimeException.class, () -> InterestCalculator.checkAndParseDouble("-1000"));
	}
	
	/**
	 * Tester method for takeInput()
	 * 
	 * Checks if correct data is parsed with mix of erroneous and valid user input
	 * Erroneous input includes:
	 * 		Not valid double
	 * 		Negative number
	 * 		0
	 * 		Number less than 1 (For isMfactor=true case)
	 */
	@Test
	public void testTakeInput() {
		// 1 Check if takeInput parses correct input when user returns characters, negative numbers
		//	and 0 with mfactor set to false
		Scanner scnr = new Scanner("aba\n-4\n\n0\n0.2\n5");
		assertEquals("error 1: Failed taking input for numbers > 0"
				, 0.2, InterestCalculator.takeInput("", scnr, false), 0.001);
		scnr.close();
		
		// 2 Check if takeInout parses correct input when user returns characters, negative numbers
		//	0, and doubles less than 1, with mfactor set to true
		scnr = new Scanner("aba\n-4\n\n0\n0.2\n5");
		assertEquals("error 2: Failed taking input for 0 < number < 1"
				, 5, InterestCalculator.takeInput("", scnr, true), 0.001);
		scnr.close();
	}
	
	/**
	 * Tester method for calcInterest()
	 * 
	 * Tests whether calcInterest returns correct result under various conditions:
	 * 		Annual compound rate
	 * 		Monthly compound rate
	 * 		Daily compound rate
	 * 		Double interest
	 * 		Mfactor of 1
	 */
	@Test
	public void testCalcInterest() {
		// 1 Normal case: mfactor = 20, compound rate = annual (1), interest = 20
		assertEquals("error 1: mfactor = 20, compound rate = 1, interest = 20"
				, 17, InterestCalculator.calcInterest(20, 1, 20), 0.001);
		
		// 2 100% interest rate: mfactor = 2, compound rate = 1, interest = 200
		assertEquals("error 2: mfactor = 2, compound rate = 1, interest = 200"
				, 1, InterestCalculator.calcInterest(2, 1, 200), 0.001);
		
		// 3 mfactor is 1, mfactor = 1, compound rate = 1, interest = 200
		assertEquals("error 3: mfactor = 1, compound rate = 1, interest = 200"
				, 0, InterestCalculator.calcInterest(1, 1, 200), 0.001);
		
		// 4 Compounded monthly: mfactor = 20, compound rate = 12, interest = 20
		assertEquals("error 4: mfactor = 20, compound rate = 12, interest = 20"
				, 16, InterestCalculator.calcInterest(20, 12, 20), 0.001);
		
		// 5 Compounded daily: mfactor = 20, compound rate = 365, interest = 20
		assertEquals("error 5: mfactor = 20, compound rate = 365, interest = 20"
				, 15, InterestCalculator.calcInterest(20, 365, 20), 0.001);
	}
}
