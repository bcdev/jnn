/*
 * $Id: NnaDefTest.java,v 1.2 2005-07-23 03:29:11 tom Exp $
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
package com.bc.jnn.nnio;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import com.bc.jnn.JnnConstants;

public class NnaDefTest extends TestCase {

    public NnaDefTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(NnaDefTest.class);
    }

    /**
     * Tests the ascio constants for correctness
     */
    public void testSectionConstants() {
        assertEquals("[", NnaDef.NN_DELIM_SECTION_START);
        assertEquals("]", NnaDef.NN_DELIM_SECTION_END);
        assertEquals("(", NnaDef.NN_DELIM_IDX_START);
        assertEquals(")", NnaDef.NN_DELIM_IDX_END);
        assertEquals(",", NnaDef.NN_DELIM_UNIT_IDX);

        assertEquals("Net", NnaDef.NN_NAME_NET);
        assertEquals("Layer", NnaDef.NN_NAME_LAYER);
        assertEquals("Unit", NnaDef.NN_NAME_UNIT);
    }


    /**
     * Tests the constants needed for the net section for correctness
     */
    public void testNetSectionConstants() {
        assertEquals("MinVersion", NnaDef.NN_NAME_MIN_VERSION);
        assertEquals("MajVersion", NnaDef.NN_NAME_MAJ_VERSION);
        assertEquals("NumLayers", NnaDef.NN_NAME_NUM_LAYERS);
        assertEquals("InpLayer", NnaDef.NN_NAME_INP_LAYER);
        assertEquals("OutLayer", NnaDef.NN_NAME_OUT_LAYER);
        assertEquals("Precision", NnaDef.NN_NAME_PRECISION);

        assertEquals(2, NnaDef.NN_NAME_PRECISION_VALUE_SET.length);
        assertEquals("Single", NnaDef.NN_NAME_PRECISION_VALUE_SET[0]);
        assertEquals("Double", NnaDef.NN_NAME_PRECISION_VALUE_SET[1]);
    }

    /**
     * Tests the constants needed for the layer section for correctness
     */
    public void testLayerSectionConstants() {
        assertEquals("NumUnits", NnaDef.NN_NAME_NUM_UNITS);
        assertEquals("InpFunc", NnaDef.NN_NAME_INP_FNID);
        assertEquals("ActFunc", NnaDef.NN_NAME_ACT_FNID);
        assertEquals("OutFunc", NnaDef.NN_NAME_OUT_FNID);
        assertEquals("ActThres", NnaDef.NN_NAME_ACT_THRES);
        assertEquals("ActSlope", NnaDef.NN_NAME_ACT_SLOPE);

        assertEquals("Zero", NnaDef.NN_NAME_ZERO);
        assertEquals("Sum_1", NnaDef.NN_NAME_SUM_1);
        assertEquals("Sum_2", NnaDef.NN_NAME_SUM_2);
        assertEquals("Identity", NnaDef.NN_NAME_IDENTITY);
        assertEquals("Threshold", NnaDef.NN_NAME_THRESHOLD);
        assertEquals("Linear", NnaDef.NN_NAME_LINEAR);
        assertEquals("SemiLinear", NnaDef.NN_NAME_SEMILINEAR);
        assertEquals("Exponential", NnaDef.NN_NAME_EXPONENTIAL);
        assertEquals("Logarithmic", NnaDef.NN_NAME_LOGARITHMIC);
        assertEquals("Sigmoid_1", NnaDef.NN_NAME_SIGMOID_1);
        assertEquals("Sigmoid_2", NnaDef.NN_NAME_SIGMOID_2);
        assertEquals("Rbf_1", NnaDef.NN_NAME_RBF_1);
        assertEquals("Rbf_2", NnaDef.NN_NAME_RBF_2);
    }

    /**
     * Tests the constants needed for the unit sections for correctness
     */
    public void testUnitSectionConstants() {
        assertEquals("NumConns", NnaDef.NN_NAME_NUM_CONNS);
        assertEquals("InpBias", NnaDef.NN_NAME_INP_BIAS);
        assertEquals("InpScale", NnaDef.NN_NAME_INP_SCALE);
        assertEquals("OutBias", NnaDef.NN_NAME_OUT_BIAS);
        assertEquals("OutScale", NnaDef.NN_NAME_OUT_SCALE);
        assertEquals("C", NnaDef.NN_NAME_CONNECTION);
    }



    /**
     * Tests the correct functionality of the functionNameToIndex method.
     */
    public void testFunctionNameToIndex() {
        // null string shall throw exception
        try {
            NnaDef.functionNameToIndex(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
        }

        assertEquals(JnnConstants.NN_FUNC_ZERO, NnaDef.functionNameToIndex(NnaDef.NN_NAME_ZERO));
        assertEquals(JnnConstants.NN_FUNC_SUM_1, NnaDef.functionNameToIndex(NnaDef.NN_NAME_SUM_1));
        assertEquals(JnnConstants.NN_FUNC_SUM_2, NnaDef.functionNameToIndex(NnaDef.NN_NAME_SUM_2));
        assertEquals(JnnConstants.NN_FUNC_IDENTITY, NnaDef.functionNameToIndex(NnaDef.NN_NAME_IDENTITY));
        assertEquals(JnnConstants.NN_FUNC_THRESHOLD, NnaDef.functionNameToIndex(NnaDef.NN_NAME_THRESHOLD));
        assertEquals(JnnConstants.NN_FUNC_LINEAR, NnaDef.functionNameToIndex(NnaDef.NN_NAME_LINEAR));
        assertEquals(JnnConstants.NN_FUNC_SEMILINEAR, NnaDef.functionNameToIndex(NnaDef.NN_NAME_SEMILINEAR));
        assertEquals(JnnConstants.NN_FUNC_EXPONENTIAL, NnaDef.functionNameToIndex(NnaDef.NN_NAME_EXPONENTIAL));
        assertEquals(JnnConstants.NN_FUNC_LOGARITHMIC, NnaDef.functionNameToIndex(NnaDef.NN_NAME_LOGARITHMIC));
        assertEquals(JnnConstants.NN_FUNC_SIGMOID_1, NnaDef.functionNameToIndex(NnaDef.NN_NAME_SIGMOID_1));
        assertEquals(JnnConstants.NN_FUNC_SIGMOID_2, NnaDef.functionNameToIndex(NnaDef.NN_NAME_SIGMOID_2));
        assertEquals(JnnConstants.NN_FUNC_TANG_SIGMOID, NnaDef.functionNameToIndex(NnaDef.NN_NAME_TANG_SIGMOID));
        assertEquals(JnnConstants.NN_FUNC_RBF_1, NnaDef.functionNameToIndex(NnaDef.NN_NAME_RBF_1));
        assertEquals(JnnConstants.NN_FUNC_RBF_2, NnaDef.functionNameToIndex(NnaDef.NN_NAME_RBF_2));

        // all other names shall lead to undefined
        assertEquals(JnnConstants.NN_FUNC_UNDEFINED, NnaDef.functionNameToIndex("whataname"));
        assertEquals(JnnConstants.NN_FUNC_UNDEFINED, NnaDef.functionNameToIndex("not_existing_function_name"));

    }
    
}