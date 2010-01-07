/*
 * $Id: NnaDef.java,v 1.3 2005-07-23 03:29:11 tom Exp $
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

import com.bc.jnn.JnnConstants;

public class NnaDef {

    // section constants
    public static final String NN_DELIM_SECTION_START = "[";
    public static final String NN_DELIM_SECTION_END = "]";

    public static final String NN_DELIM_IDX_START = "(";
    public static final String NN_DELIM_IDX_END = ")";
    public static final String NN_DELIM_UNIT_IDX = ",";

    public static final String NN_NAME_NET = "Net";
    public static final String NN_NAME_LAYER = "Layer";
    public static final String NN_NAME_UNIT = "Unit";


    // [ net ] section constants
    public static final String NN_NAME_MIN_VERSION = "MinVersion";
    public static final String NN_NAME_MAJ_VERSION = "MajVersion";
    public static final String NN_NAME_NUM_LAYERS = "NumLayers";
    public static final String NN_NAME_INP_LAYER = "InpLayer";
    public static final String NN_NAME_OUT_LAYER = "OutLayer";
    public static final String NN_NAME_PRECISION = "Precision";
    public static final String[] NN_NAME_PRECISION_VALUE_SET = new String[]{"Single", "Double"};

    // [ layer(i) ] section constants
    public static final String NN_NAME_NUM_UNITS = "NumUnits";
    public static final String NN_NAME_INP_FNID = "InpFunc";
    public static final String NN_NAME_ACT_FNID = "ActFunc";
    public static final String NN_NAME_OUT_FNID = "OutFunc";
    public static final String NN_NAME_ACT_THRES = "ActThres";
    public static final String NN_NAME_ACT_SLOPE = "ActSlope";

    public static final String NN_NAME_ZERO = "Zero";
    public static final String NN_NAME_SUM_1 = "Sum_1";
    public static final String NN_NAME_SUM_2 = "Sum_2";
    public static final String NN_NAME_IDENTITY = "Identity";
    public static final String NN_NAME_THRESHOLD = "Threshold";
    public static final String NN_NAME_LINEAR = "Linear";
    public static final String NN_NAME_SEMILINEAR = "SemiLinear";
    public static final String NN_NAME_EXPONENTIAL = "Exponential";
    public static final String NN_NAME_LOGARITHMIC = "Logarithmic";
    public static final String NN_NAME_SIGMOID_1 = "Sigmoid_1";
    public static final String NN_NAME_SIGMOID_2 = "Sigmoid_2";
    public static final String NN_NAME_TANG_SIGMOID = "TangSigmoid";
    public static final String NN_NAME_RBF_1 = "Rbf_1";
    public static final String NN_NAME_RBF_2 = "Rbf_2";

    // [ unit(i, j) ] section constants
    public static final String NN_NAME_NUM_CONNS = "NumConns";
    public static final String NN_NAME_INP_BIAS = "InpBias";
    public static final String NN_NAME_INP_SCALE = "InpScale";
    public static final String NN_NAME_OUT_BIAS = "OutBias";
    public static final String NN_NAME_OUT_SCALE = "OutScale";
    public static final String NN_NAME_CONNECTION = "C";

    /**
     * Converts a function name (as defined in the NnaDef) to appropriate function index
     * @param name
     * @return
     */

    public static int functionNameToIndex(String name) {
        if (name == null) {
            throw new IllegalArgumentException("parameter 'name' is null");
        }

        int index = JnnConstants.NN_FUNC_UNDEFINED;

        if (name.equalsIgnoreCase(NN_NAME_IDENTITY)) {
            index = JnnConstants.NN_FUNC_IDENTITY;
        } else if (name.equalsIgnoreCase(NN_NAME_THRESHOLD)) {
            index = JnnConstants.NN_FUNC_THRESHOLD;
        } else if (name.equalsIgnoreCase(NN_NAME_LINEAR)) {
            index = JnnConstants.NN_FUNC_LINEAR;
        } else if (name.equalsIgnoreCase(NN_NAME_SEMILINEAR)) {
            index = JnnConstants.NN_FUNC_SEMILINEAR;
        } else if (name.equalsIgnoreCase(NN_NAME_EXPONENTIAL)) {
            index = JnnConstants.NN_FUNC_EXPONENTIAL;
        } else if (name.equalsIgnoreCase(NN_NAME_LOGARITHMIC)) {
            index = JnnConstants.NN_FUNC_LOGARITHMIC;
        } else if (name.equalsIgnoreCase(NN_NAME_SIGMOID_1)) {
            index = JnnConstants.NN_FUNC_SIGMOID_1;
        }else if (name.equalsIgnoreCase(NN_NAME_TANG_SIGMOID)) {
            index = JnnConstants.NN_FUNC_TANG_SIGMOID;
        } else if (name.equalsIgnoreCase(NN_NAME_SIGMOID_2)) {
            index = JnnConstants.NN_FUNC_SIGMOID_2;
        } else if (name.equalsIgnoreCase(NN_NAME_RBF_1)) {
            index = JnnConstants.NN_FUNC_RBF_1;
        } else if (name.equalsIgnoreCase(NN_NAME_RBF_2)) {
            index = JnnConstants.NN_FUNC_RBF_2;
        } else if (name.equalsIgnoreCase(NN_NAME_ZERO)) {
            index = JnnConstants.NN_FUNC_ZERO;
        } else if (name.equalsIgnoreCase(NN_NAME_SUM_1)) {
            index = JnnConstants.NN_FUNC_SUM_1;
        } else if (name.equalsIgnoreCase(NN_NAME_SUM_2)) {
            index = JnnConstants.NN_FUNC_SUM_2;
        }
        return index;
    }
}