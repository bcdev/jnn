/*
 * $Id: JnnNetTest.java,v 1.13 2005-08-29 22:20:17 norman Exp $
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

package com.bc.jnn;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class JnnNetTest extends TestCase {

    private static final File _schillerNetFile_1 = ResourceProvider.getResourceAsFile("MVA_wcrtm_fwd.nna");

    private JnnNet _net;

    public JnnNetTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(JnnNetTest.class);
    }

    /**
     * Initializes the test environment
     */
    protected void setUp() {
        _net = new JnnNet();
        assertNotNull(_net);
    }


    /**
     * Tests the correct default construction of the neiural net.
     */
    public void testDefaultConstruction() {
        String expVersionString = JnnConstants.NN_VERSION_MAJOR + "." + JnnConstants.NN_VERSION_MINOR;

        assertEquals(JnnConstants.NN_VERSION_MAJOR, _net.getVersionMajor());
        assertEquals(JnnConstants.NN_VERSION_MINOR, _net.getVersionMinor());
        assertEquals(expVersionString, _net.getVersionString());

        assertEquals(1, _net.getNumLayers());
        assertEquals(JnnConstants.NN_NOT_SET, _net.getInputLayerIndex());
        assertEquals(JnnConstants.NN_NOT_SET, _net.getOutputLayerIndex());

        assertEquals(JnnConstants.NN_PREC_DOUBLE, _net.getPrecision());
    }


    /**
     * Tests the version minor accessors for correct functionality.
     */
    public void testSetGetVersionMinor() {
        int expVersMinor_1 = 0;
        int expVersMinor_2 = 5;

        _net.setVersionMinor(expVersMinor_1);
        assertEquals(expVersMinor_1, _net.getVersionMinor());

        _net.setVersionMinor(expVersMinor_2);
        assertEquals(expVersMinor_2, _net.getVersionMinor());
    }

    /**
     * Tests the version minor accessors for correct functionality.
     */
    public void testSetGetVersionMajor() {
        int expVersMajor_1 = 2;
        int expVersMajor_2 = 7;

        _net.setVersionMajor(expVersMajor_1);
        assertEquals(expVersMajor_1, _net.getVersionMajor());

        _net.setVersionMajor(expVersMajor_2);
        assertEquals(expVersMajor_2, _net.getVersionMajor());
    }

    /**
     * Tests that the version string accessor works correctly
     */
    public void testGetVersionString() {
        int expVersMinor_1 = 0;
        int expVersMinor_2 = 5;
        int expVersMajor_1 = 2;
        int expVersMajor_2 = 7;

        String expVersString_1 = expVersMajor_1 + "." + expVersMinor_1;
        String expVersString_2 = expVersMajor_2 + "." + expVersMinor_2;

        _net.setVersionMajor(expVersMajor_1);
        _net.setVersionMinor(expVersMinor_1);
        assertEquals(expVersString_1, _net.getVersionString());

        _net.setVersionMajor(expVersMajor_2);
        _net.setVersionMinor(expVersMinor_2);
        assertEquals(expVersString_2, _net.getVersionString());
    }

    /**
     * Tests the set/getNumLayers accessors for correctness
     */
    public void testSetGetNumLayers() {
        int expNumLayers_1 = 4;
        int expNumLayers_2 = 9;

        _net.setNumLayers(expNumLayers_1);
        assertEquals(expNumLayers_1, _net.getNumLayers());

        _net.setNumLayers(expNumLayers_2);
        assertEquals(expNumLayers_2, _net.getNumLayers());

        // force failures
        try {
            _net.setNumLayers(-3);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Tests the correct functionality of the input layer index accessors.
     */
    public void testSetGetInputLayerIdx() {
        int expIdxLayer_1 = 4;
        int expIdxLayer_2 = 0;

        _net.setInputLayerIndex(expIdxLayer_1);
        assertEquals(expIdxLayer_1, _net.getInputLayerIndex());

        _net.setInputLayerIndex(expIdxLayer_2);
        assertEquals(expIdxLayer_2, _net.getInputLayerIndex());
    }

    /**
     * Tests the correct functionality of the output layer index accessors.
     */
    public void testSetGetOutputLayerIdx() {
        int expIdxLayer_1 = 3;
        int expIdxLayer_2 = 5;

        _net.setOutputLayerIndex(expIdxLayer_1);
        assertEquals(expIdxLayer_1, _net.getOutputLayerIndex());

        _net.setInputLayerIndex(expIdxLayer_2);
        assertEquals(expIdxLayer_2, _net.getInputLayerIndex());
    }


    /**
     * Tests the set / get precision accessors for correct functionality
     */
    public void testSetGetPrecision() {
        _net.setPrecision(JnnConstants.NN_PREC_SINGLE);
        assertEquals(JnnConstants.NN_PREC_SINGLE, _net.getPrecision());

        _net.setPrecision(JnnConstants.NN_PREC_DOUBLE);
        assertEquals(JnnConstants.NN_PREC_DOUBLE, _net.getPrecision());

        // other values must throw exception
        try {
            _net.setPrecision(22);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
        }

        try {
            _net.setPrecision(-2);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Tests the verify and set up method for correctness
     */
    public void testVerifyAndSetUp() {
        // must fail - no layers defined
        assertEquals(false, _net.init());
    }


    /**
     * Tests the verify and set up method for correctness
     */
    public void testVerifyAndSetUp_2() {
        // test that the input and output layer indices are checked correctly
        setUpSimpleTwoLayerNet();
        assertEquals(true, _net.init());

        // false - out of range input
        _net.setInputLayerIndex(0);
        _net.setOutputLayerIndex(2);
        assertEquals(false, _net.init());

        _net.setInputLayerIndex(3);
        _net.setOutputLayerIndex(2);
        assertEquals(false, _net.init());

        // false - out of range output
        _net.setInputLayerIndex(1);
        _net.setOutputLayerIndex(-1);
        assertEquals(false, _net.init());

        _net.setInputLayerIndex(1);
        _net.setOutputLayerIndex(6);
        assertEquals(false, _net.init());
    }

    /**
     * Tests the correctness of the layer accessors
     */
    public void testSetGetLayer() {
        JnnLayer layerRet;
        _net.setNumLayers(4);

        JnnLayer layer_1 = new JnnLayer();
        JnnLayer layer_2 = new JnnLayer();
        _net.setLayerAt(0, layer_1);
        _net.setLayerAt(1, layer_2);
        layerRet = _net.getLayerAt(0);
        assertSame(layer_1, layerRet);
        layerRet = _net.getLayerAt(1);
        assertSame(layer_2, layerRet);

        // shall not accep out of range indices
        try {
            _net.setLayerAt(-1, layer_1);
            fail();
        } catch (ArrayIndexOutOfBoundsException expexted) {
        }

        try {
            _net.setLayerAt(4, layer_1);
            fail();
        } catch (ArrayIndexOutOfBoundsException expexted) {
        }
    }

    /**
     * Tests for the correct behaviour when the number of layers is not determined yet
     */
    public void testSetLayerWhenNoLayerNumberIsSet() {
        JnnLayer layer_1 = new JnnLayer();
        try {
            _net.setLayerAt(2, layer_1);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    /**
     * Checks that the net's processing function is working correctly
     */
    public void testProcessNet() {
        double[] input = new double[]{1.0};
        double[] output = new double[1];

        setUpSimpleTwoLayerNet();
        assertEquals(true, _net.init());

        _net.process(input, output);

        assertEquals(0.8, output[0], 1e-6);

        // Test that a cloned net works as well
        JnnNet net = _net.clone();

        output[0] = 0.0;
        net.process(input, output);
        assertEquals(0.8, output[0], 1e-6);
    }

    /**
     * Tests that the schiller forward net is calculated correctly
     */
    public void testProcessSchillerForward() throws IOException, JnnException {
        double[] input = {50.5563, 33.0305, 125.395, 1.64102, -3.93968, -0.929750};
        double[] expOut = {-4.72434, -4.35721, -3.91311, -3.81949, -3.64676, -4.31995, -4.70068, -5.32911};
        double[] output = new double[expOut.length];

        final JnnNet net = Jnn.readNna(_schillerNetFile_1);
        assertNotNull(net);

        net.process(input, output);

        for (int i = 0; i < expOut.length; i++) {
            assertEquals(expOut[i], output[i], 1e-5);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    //////// END OF PUBLIC
    ///////////////////////////////////////////////////////////////////////////

    private void setUpSimpleTwoLayerNet() {
        _net.setNumLayers(2);

        JnnLayer layer_1 = new JnnLayer();
        JnnLayer layer_2 = new JnnLayer();
        JnnUnit unit_1 = new JnnUnit();
        JnnUnit unit_2 = new JnnUnit();
        JnnConnection conn_1 = new JnnConnection();

        unit_1.setNumConnections(0);
        layer_1.setActivationFunction(JnnConstants.NN_FUNC_SEMILINEAR);
        layer_1.setInputFunction(JnnConstants.NN_FUNC_SUM_1);
        layer_1.setNumUnits(1);
        layer_1.setOutputFunction(JnnConstants.NN_FUNC_IDENTITY);
        layer_1.setUnitAt(0, unit_1);

        conn_1.setSourceLayerIndex(0);
        conn_1.setSourceUnitIndex(0);
        conn_1.setWeight(0.8);

        unit_2.setNumConnections(1);
        unit_2.setConnectionAt(0, conn_1);
        layer_2.setActivationFunction(JnnConstants.NN_FUNC_SEMILINEAR);
        layer_2.setInputFunction(JnnConstants.NN_FUNC_SUM_1);
        layer_2.setNumUnits(1);
        layer_2.setOutputFunction(JnnConstants.NN_FUNC_IDENTITY);
        layer_2.setUnitAt(0, unit_2);

        _net.setLayerAt(0, layer_1);
        _net.setLayerAt(1, layer_2);

        // now check the different input layer indices
        // correct
        _net.setInputLayerIndex(0);
        _net.setOutputLayerIndex(1);
    }
}
