/*
 * $Id: JnnUnitTests.java,v 1.8 2005-08-29 22:20:17 norman Exp $
 *
 * Copyright (C) 2002 by Brockmann Consult (info@brockmann-consult.de)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation. This program is distributed in the hope it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.bc.jnn;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

public class JnnUnitTests extends TestCase {

    private JnnUnit _unit;

    public JnnUnitTests(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(JnnUnitTests.class);
    }

    /**
     * Initializes the test environment
     */
    protected void setUp() {
        _unit = new JnnUnit();
        assertNotNull(_unit);
    }

    /**
     * Tests that the default constructor works correctly
     */
    public void testDefaultValues() {
        assertEquals(0, _unit.getNumConnections());
        assertEquals(1.0, _unit.getInputScale(), 1e-6);
        assertEquals(0.0, _unit.getInputBias(), 1e-6);
        assertEquals(1.0, _unit.getOutputScale(), 1e-6);
        assertEquals(0.0, _unit.getOutputBias(), 1e-6);
        assertEquals(0.0, _unit.getInput(), 1e-6);
        assertEquals(0.0, _unit.getOutput(), 1e-6);
        assertEquals(0.0, _unit.getActivation(), 1e-6);
    }

    /**
     * Tests that the numConnections accessor methods work correctly
     */
    public void testSetGetNumConnections() {
        int expNumConns_1 = 5;
        int expNumConns_2 = 89;

        _unit.setNumConnections(expNumConns_1);
        assertEquals(expNumConns_1, _unit.getNumConnections());

        _unit.setNumConnections(expNumConns_2);
        assertEquals(expNumConns_2, _unit.getNumConnections());

        // must fail on negtavie number
        try {
            _unit.setNumConnections(-2);
        } catch (NegativeArraySizeException expected) {
        }
    }

    /**
     * Tests that the connection accessors are working correctly
     */
    public void testAddGetConnection() {
        int expNumConn = 7;

        _unit.setNumConnections(expNumConn);
        assertEquals(expNumConn, _unit.getNumConnections());

        JnnConnection con_1 = new JnnConnection();
        JnnConnection con_2 = new JnnConnection();
        JnnConnection con_3 = new JnnConnection();

        _unit.setConnectionAt(4, con_1);
        assertSame(con_1, _unit.getConnectionAt(4));

        _unit.setConnectionAt(1, con_2);
        assertSame(con_2, _unit.getConnectionAt(1));

        // must fail when trying to add or retrieve out of the predefined range
        try {
            _unit.setConnectionAt(-1, con_3);
            fail();
        } catch (ArrayIndexOutOfBoundsException expected) {
        }

        try {
            _unit.setConnectionAt(7, con_3);
            fail();
        } catch (ArrayIndexOutOfBoundsException expected) {
        }
    }

    /**
     * Tests the expected error behaviour when trying to add or get a
     * connection when no connections are set yet
     */
    public void testMustFailWhenNoConnectionsAreSetYet() {
        JnnConnection con = new JnnConnection();

        try {
            _unit.setConnectionAt(1, con);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            _unit.getConnectionAt(2);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    /**
     * Tests that the input scale accessors work correctly
     */
    public void testSetGetInputScale() {
        double expInpScale_1 = 0.5;
        double expInpScale_2 = 1.89;

        _unit.setInputScale(expInpScale_1);
        assertEquals(expInpScale_1, _unit.getInputScale(), 1e-6);

        _unit.setInputScale(expInpScale_2);
        assertEquals(expInpScale_2, _unit.getInputScale(), 1e-6);
    }

    /**
     * Tests that the input Bias accessors work correctly
     */
    public void testSetGetInputBias() {
        double expInpBias_1 = -5.5;
        double expInpBias_2 = 6.339;

        _unit.setInputBias(expInpBias_1);
        assertEquals(expInpBias_1, _unit.getInputBias(), 1e-6);

        _unit.setInputBias(expInpBias_2);
        assertEquals(expInpBias_2, _unit.getInputBias(), 1e-6);
    }

    /**
     * Tests that the output scale accessors work correctly
     */
    public void testSetGetOutputScale() {
        double expOutScale_1 = 3.9;
        double expOutScale_2 = 23.7;

        _unit.setOutputScale(expOutScale_1);
        assertEquals(expOutScale_1, _unit.getOutputScale(), 1e-6);

        _unit.setOutputScale(expOutScale_2);
        assertEquals(expOutScale_2, _unit.getOutputScale(), 1e-6);
    }

    /**
     * Tests that the output Bias accessors work correctly
     */
    public void testSetGetOutputBias() {
        double expOutBias_1 = 33.5;
        double expOutBias_2 = -.99346;

        _unit.setOutputBias(expOutBias_1);
        assertEquals(expOutBias_1, _unit.getOutputBias(), 1e-6);

        _unit.setOutputBias(expOutBias_2);
        assertEquals(expOutBias_2, _unit.getOutputBias(), 1e-6);
    }

    /**
     * Tests the initFunctions method for correctness
     */
    public void testVerifyIntegrity() {
        // nothing is set - a legal state for a unit
        assertEquals(true, _unit.verifyIntegrity());

        // must fail - set two connections but didn't add them
        _unit.setNumConnections(2);
        assertEquals(false, _unit.verifyIntegrity());
    }

    /**
     * Tests that the setGetInputMethods work correctly
     */
    public void testSetGetInput() {
        double expIn_1 = 23.7;
        double expIn_2 = -7.22;

        _unit.setInput(expIn_1);
        assertEquals(expIn_1, _unit.getInput(), 1e-6);

        _unit.setInput(expIn_2);
        assertEquals(expIn_2, _unit.getInput(), 1e-6);
    }

    /**
     * Tests that the setGetOutputMethods work correctly
     */
    public void testSetGetOutput() {
        double expOut_1 = 2.6;
        double expOut_2 = 0.4;

        _unit.setOutput(expOut_1);
        assertEquals(expOut_1, _unit.getOutput(), 1e-6);

        _unit.setOutput(expOut_2);
        assertEquals(expOut_2, _unit.getOutput(), 1e-6);
    }

    /**
     * Tests that the setGetOutputMethods work correctly
     */
    public void testSetGetActivation() {
        double expAct_1 = -0.6;
        double expAct_2 = 10.4;

        _unit.setActivation(expAct_1);
        assertEquals(expAct_1, _unit.getActivation(), 1e-6);

        _unit.setActivation(expAct_2);
        assertEquals(expAct_2, _unit.getActivation(), 1e-6);
    }
}

