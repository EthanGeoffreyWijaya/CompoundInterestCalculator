package com.interestcalculator.CompoundInterestCalculator;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
	/**
	 * Runs all tests in the InterestCalculatorTester class
	 */
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(InterestCalculatorTester.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}

		if (result.wasSuccessful()) System.out.println("All tests passed!");
	}

}
