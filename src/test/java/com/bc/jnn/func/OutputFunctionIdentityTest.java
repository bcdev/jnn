/*
 * $Id: OutputFunctionIdentityTest.java,v 1.1 2004-01-30 15:01:11 tom Exp $
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

public class OutputFunctionIdentityTest extends TestCase {

    private OutputFunctionIdentity _func;

    public OutputFunctionIdentityTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(OutputFunctionIdentityTest.class);
    }

    /**
     * Initializes the test environment
     */
    protected void setUp() {
        _func = new OutputFunctionIdentity();
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
     *  y = x
     */
    public void testCorrectCalculation() {
        JnnUnit unit_1 = new JnnUnit();
        JnnUnit unit_2 = new JnnUnit();

        unit_1.setActivation(1.5);
        unit_2.setActivation(-0.2);

        _func.evaluate(unit_1);
        assertEquals(1.5, unit_1.getOutput(), 1e-6);

        _func.evaluate(unit_2);
        assertEquals(-0.2, unit_2.getOutput(), 1e-6);
    }
}
