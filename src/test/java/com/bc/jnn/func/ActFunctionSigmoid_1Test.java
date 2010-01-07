/*
 * $Id: ActFunctionSigmoid_1Test.java,v 1.5 2005-02-02 20:07:42 norman Exp $
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

public class ActFunctionSigmoid_1Test extends TestCase {

    private ActFunctionSigmoid_1 _func;

    public ActFunctionSigmoid_1Test(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(ActFunctionSigmoid_1Test.class);
    }

    /**
     * Initializes the test environment.
     */
    protected void setUp() {
        _func = new ActFunctionSigmoid_1();
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
            _func.setParameter(one);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
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
        double[] params_1 = new double[]{0.6, 0.4};
        double[] params_2 = new double[]{-1.0, 0.98};
        JnnUnit unit_1 = new JnnUnit();
        JnnUnit unit_2 = new JnnUnit();

        unit_1.setInput(1.0);
        unit_2.setInput(0.7);

        _func.setParameter(params_1);
        _func.evaluate(unit_1);
        assertEquals(0.5399149, unit_1.getActivation(), 1e-6);

        _func.evaluate(unit_2);
        assertEquals(0.5099987, unit_2.getActivation(), 1e-6);

        _func.setParameter(params_2);
        _func.evaluate(unit_1);
        assertEquals(0.8765330, unit_1.getActivation(), 1e-6);

        _func.evaluate(unit_2);
        assertEquals(0.8410418, unit_2.getActivation(), 1e-6);
    }
}
