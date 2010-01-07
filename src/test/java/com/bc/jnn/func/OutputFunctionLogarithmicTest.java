/*
 * $Id: OutputFunctionLogarithmicTest.java,v 1.1 2004-01-30 15:01:11 tom Exp $
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

public class OutputFunctionLogarithmicTest extends TestCase {

    private OutputFunctionLogarithmic _func;

    public OutputFunctionLogarithmicTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(OutputFunctionLogarithmicTest.class);
    }

    /**
     * Initializes the test environment
     */
    protected void setUp() {
        _func = new OutputFunctionLogarithmic();
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
     *  y = log(scale * x + bias)
     */
    public void testCorrectCalculation() {
        JnnUnit unit_1 = new JnnUnit();
        JnnUnit unit_2 = new JnnUnit();

        unit_1.setActivation(1.5);
        unit_1.setOutputBias(0.2);
        unit_1.setOutputScale(0.9);
        unit_2.setActivation(-0.2);
        unit_2.setOutputBias(0.4);
        unit_2.setOutputScale(0.1);

        _func.evaluate(unit_1);
        assertEquals(0.438254, unit_1.getOutput(), 1e-6);

        _func.evaluate(unit_2);
        assertEquals(-0.967584, unit_2.getOutput(), 1e-6);
    }
}
