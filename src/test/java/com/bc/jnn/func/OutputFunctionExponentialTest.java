/*
 * $Id: OutputFunctionExponentialTest.java,v 1.1 2004-01-30 15:01:11 tom Exp $
 *
 * Copyright (C) 2002,2003  by Brockmann Consult (info@brockmann-consult.de)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation. This program is distributed in the hope it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */
package com.bc.jnn.func;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import com.bc.jnn.JnnUnit;

public class OutputFunctionExponentialTest extends TestCase {

    private OutputFunctionExponential _func;

    public OutputFunctionExponentialTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(OutputFunctionExponentialTest.class);
    }

    /**
     * Initializes the test environment
     */
    protected void setUp() {
        _func = new OutputFunctionExponential();
        assertNotNull(_func);
    }

    /**
     * Just tests that the interface is implemented correctly
     */
    public void testImplementsCorrectInterface() {
        assertTrue(_func instanceof IOutputFunction);
    }

    /**
     * Tests that the metod evaluates correctly
     *  y = exp(scale * x + bias)
     */
    public void testCorrectCalculation() {
        JnnUnit unit_1 = new JnnUnit();
        JnnUnit unit_2 = new JnnUnit();

        unit_1.setActivation(1.5);
        unit_1.setOutputBias(0.98);
        unit_1.setOutputScale(2.1);
        _func.evaluate(unit_1);
        assertEquals(62.177923, unit_1.getOutput(), 1e-6);

        unit_2.setActivation(0.5);
        unit_2.setOutputBias(1.1);
        unit_2.setOutputScale(0.34);
        _func.evaluate(unit_2);
        assertEquals(3.560853, unit_2.getOutput(), 1e-6);
    }

}
