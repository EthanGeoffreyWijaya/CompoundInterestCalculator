package com.interestcalculator.CompoundInterestCalculator;

import java.util.Scanner;
import org.junit.*;
import static org.junit.Assert.assertEquals;

public class InterestCalculatorTester {

	/**
	 * Tester method for checkDouble()
	 */
	@Test
	public void testCheckDouble() {
		// 1 Check if error code = 0, meaning input is number >= 1
		String errmsg1 = "error 1: checkDouble failed to return 0 when all requirements passed";
		assertEquals(errmsg1, 0, InterestCalculator.checkDouble("5"));
		assertEquals(errmsg1, 0, InterestCalculator.checkDouble("200"));
		assertEquals(errmsg1, 0, InterestCalculator.checkDouble("50000"));
		assertEquals(errmsg1, 0, InterestCalculator.checkDouble("5.45"));

		// 2 Check if error code = 1 upon null input
		assertEquals("error 2: checkDouble failed to return 1 with null input"
				, 1, InterestCalculator.checkDouble(null));
		
		// 3 Check if error code = 1 upon empty input
		assertEquals("error 3: checkDouble failed to return 1 with empty input"
				, 1, InterestCalculator.checkDouble(""));
		
		// 4 Check if error code = 1 if input is not a valid double
		String errmsg4 = "error 4: checkDouble failed to return 1 with non-double input (words and invalid numbers)";
		assertEquals(errmsg4, 1, InterestCalculator.checkDouble("hello"));
		assertEquals(errmsg4, 1, InterestCalculator.checkDouble("aerghjuyt"));
		assertEquals(errmsg4, 1, InterestCalculator.checkDouble("dh8237yeg8d23o7y"));
		assertEquals(errmsg4, 1, InterestCalculator.checkDouble("50.0.0"));
		assertEquals(errmsg4, 1, InterestCalculator.checkDouble("\'\"44"));
		
		// 5 Check if error code = 2 upon 0 input
		assertEquals("error 5: checkDouble failed to return 2 with input 0"
				, 2, InterestCalculator.checkDouble("0"));
		
		// 6 Check if error code = 2 upon negative input
		assertEquals("error 6: checkDouble failed to return 2 with input -1000"
				, 2, InterestCalculator.checkDouble("-1000"));
		
		// 7 Check if error code = 3 if input < 1
		assertEquals("error 6: checkDouble failed to return 3 with input 0.5"
				, 3, InterestCalculator.checkDouble("0.5"));
	}
	
	/**
	 * Tester method for takeInput()
	 */
	@Test
	public void testTakeInput() {
		// 1 Take input that isn't null, isn't 0, and isn't negative
		Scanner scnr = new Scanner("aba\n-4\n\n0\n0.2\n5");
		assertEquals("error 1: Failed taking input for numbers > 0"
				, 0.2, InterestCalculator.takeInput("", scnr, 0, 3), 0.001);
		scnr.close();
		
		// 2 Take input that isn't null, isn't 0, isn't negative, and isn't < 1
		scnr = new Scanner("aba\n-4\n\n0\n0.2\n5");
		assertEquals("error 2: Failed taking input for 0 < number < 1"
				, 5, InterestCalculator.takeInput("", scnr, 0), 0.001);
		scnr.close();
		
		// 3 Only accept input that is 0 or negative
		scnr = new Scanner("aba\n-4\n\n0\n0.2\n5");
		assertEquals("error 3: Failed taking input for numbers < 0"
				, -4, InterestCalculator.takeInput("", scnr, 2), 0.001);
		scnr.close();
	}
	
	/**
	 * Tester method for calcInterest()
	 */
	@Test
	public void testCalcInterest() {
		// 1 Normal case: mfactor = 5, compound rate = annual (1), interest = 10
		assertEquals("error 1: mfactor = 5, compound rate = 1, interest = 10"
				, 17, InterestCalculator.calcInterest(5, 1, 10), 0.001);
		
		// 2 100% interest rate: mfactor = 2, compound rate = 1, interest = 200
		assertEquals("error 2: mfactor = 2, compound rate = 1, interest = 200"
				, 1, InterestCalculator.calcInterest(2, 1, 200), 0.001);
		
		// 3 mfactor is 1, mfactor = 1, compound rate = 1, interest = 200
		assertEquals("error 3: mfactor = 1, compound rate = 1, interest = 200"
				, 0, InterestCalculator.calcInterest(1, 1, 200), 0.001);
		
		// 4 Compounded monthly: mfactor = 5, compound rate = 12, interest = 10
		assertEquals("error 4: mfactor = 5, compound rate = 12, interest = 10"
				, 17, InterestCalculator.calcInterest(5, 12, 10), 0.001);
	}
}
