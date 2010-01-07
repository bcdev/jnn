/*
 * $Id: ActFunctionThresholdTest.java,v 1.2 2005-01-31 04:06:57 norman Exp $
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

public class ActFunctionThresholdTest extends TestCase {

    private ActFunctionThreshold _func;

    public ActFunctionThresholdTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(ActFunctionThresholdTest.class);
    }

    /**
     * Initializes the test environment.
     */
    protected void setUp() {
        _func = new ActFunctionThreshold();
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
        double[] one = new double[]{3.5};
        double[] two = new double[]{2.4, 1.9};
        double[] three = new double[]{2.4, 1.9, 34.9};

        try {
            _func.setParameter(zero);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            _func.setParameter(one);
            _func.setParameter(two);
            _func.setParameter(three);
        } catch (IllegalArgumentException notExpected) {
            fail();
        }
    }

    /**
     * Tests the correct evaluation of the function
     */
    public void testEvaluate() {
        // pUnit->fAct = 1.0 / (1.0 + exp(fT - fS * pUnit->fInp));
        double[] params_1 = new double[]{0.6};
        double[] params_2 = new double[]{0.2};
        JnnUnit unit_1 = new JnnUnit();
        JnnUnit unit_2 = new JnnUnit();

        unit_1.setInput(0.8);
        unit_2.setInput(0.1);

        _func.setParameter(params_1);
        _func.evaluate(unit_1);
        assertEquals(1.0, unit_1.getActivation(), 1e-6);

        _func.evaluate(unit_2);
        assertEquals(0.0, unit_2.getActivation(), 1e-6);

        _func.setParameter(params_2);
        _func.evaluate(unit_1);
        assertEquals(1.0, unit_1.getActivation(), 1e-6);

        _func.evaluate(unit_2);
        assertEquals(0.0, unit_2.getActivation(), 1e-6);
    }
}
