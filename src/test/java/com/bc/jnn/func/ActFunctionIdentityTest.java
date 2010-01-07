/*
 * $Id: ActFunctionIdentityTest.java,v 1.3 2005-01-31 04:06:57 norman Exp $
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

public class ActFunctionIdentityTest extends TestCase {

    private ActFunctionIdentity _func;

    public ActFunctionIdentityTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(ActFunctionIdentityTest.class);
    }

    /**
     * Initializes the test environment
     */
    protected void setUp() {
        _func = new ActFunctionIdentity();
        assertNotNull(_func);
    }

    /**
     * Just tests that the interface is implemented correctly
     */
    public void testImplementsCorrectInterface() {
        assertTrue(_func instanceof IActivationFunction);
    }

    /**
     * Shall accept minimum number of parameters
     */
    public void testSetParameter() {
        double[] zero = new double[0];
        double[] one = new double[]{2.9};
        double[] three = new double[]{2.9, 2.4, 1.9};

        try {
            _func.setParameter(zero);
            _func.setParameter(one);
            _func.setParameter(three);
        } catch (IllegalArgumentException notExpected) {
            fail();
        }
    }

    /**
     * Tests that the activation works correctly
     */
    public void testCorrectActivation() {
        JnnUnit unit_1 = new JnnUnit();
        JnnUnit unit_2 = new JnnUnit();
        double dIn_1 = 2.7;
        double dIn_2 = -238.4;

        unit_1.setInput(dIn_1);
        _func.evaluate(unit_1);
        assertEquals(dIn_1, unit_1.getActivation(), 1e-6);

        unit_2.setInput(dIn_2);
        _func.evaluate(unit_2);
        assertEquals(dIn_2, unit_2.getActivation(), 1e-6);
    }
}
