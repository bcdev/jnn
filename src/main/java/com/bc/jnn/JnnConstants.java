/*
 * $Id: JnnConstants.java,v 1.6 2005-07-23 03:23:04 tom Exp $
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

public final class JnnConstants {

    // Defines the current version of NNFF
    public static final int NN_VERSION_MAJOR = 1;
    public static final int NN_VERSION_MINOR = 0;

    // Defines the section header identifiers
    // DO NOT CHANGE WITHOUT CHANGING THE NNFF VERSION NO.
    public static final String NN_NET_SECTION_ID = "NET\0";
    public static final String NN_LAYER_SECTION_ID = "LAY\0";
    public static final String NN_UNIT_SECTION_ID = "UNI\0";
    public static final String NN_CONN_SECTION_ID = "CON\0";
    public static final String NN_MATRIX_SECTION_ID = "MAT\0";

    // Enumerates all valid precision codes
    public static final int NN_PREC_SINGLE = 4;
    public static final int NN_PREC_DOUBLE = 8;

    // invalid valiue
    public static final int NN_NOT_SET = -1;

    // function constants
    public static final int NN_FUNC_UNDEFINED = -1;
    public static final int NN_FUNC_ZERO = 0;
    public static final int NN_FUNC_IDENTITY = 1;
    public static final int NN_FUNC_THRESHOLD = 10;
    public static final int NN_FUNC_LINEAR = 11;
    public static final int NN_FUNC_SEMILINEAR = 12;
    public static final int NN_FUNC_EXPONENTIAL = 13;
    public static final int NN_FUNC_LOGARITHMIC = 14;
    public static final int NN_FUNC_SIGMOID_1 = 20;
    public static final int NN_FUNC_SIGMOID_2 = 21;
    public static final int NN_FUNC_TANG_SIGMOID = 22;
    public static final int NN_FUNC_RBF_1 = 30;
    public static final int NN_FUNC_RBF_2 = 31;
    public static final int NN_FUNC_SUM_1 = 40;
    public static final int NN_FUNC_SUM_2 = 41;
}