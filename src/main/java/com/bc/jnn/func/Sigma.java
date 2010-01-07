/*
 * $Id: Sigma.java,v 1.3 2005-08-29 22:53:18 norman Exp $
 *
 * Copyright (c) 2003 Brockmann Consult GmbH. All right reserved.
 * http://www.brockmann-consult.de
 */
package com.bc.jnn.func;

/**
 * Provides the sigma function in its normal form ({@link #evaluate(double)})
 * and an optimized, tabulated version ({@link #evaluateOpt(double)}) of it.
 */
public class Sigma {

    /**
     * Lower bound of inner region.
     */
    public final static double SIG_X1 = -5.0;

    /**
     * Upper bound of inner region.
     */
    public final static double SIG_X2 = +5.0;

    /**
     * Lower bound of outer region.
     */
    public final static double SIG_XA = -100.0;

    /**
     * Upper bound of outer region.
     */
    public final static double SIG_XB = +100.0;

    /**
     * The number of subdivisions in the range {@link SIG_X1} <= x < {@link SIG_X2}.
     */
    public final static int SIG_N = 1024;

    /**
     * The number of subdivisions in the ranges {@link SIG_XA} <= x < {@link SIG_X1} and  {@link SIG_X2} <= x < {@link SIG_XB}.
     */
    public final static int SIG_P = 1024;


    private final static double SIG_SX = (SIG_N - 1.0) / (SIG_X2 - SIG_X1);
    private final static double SIG_TX = (SIG_P - 1.0) / (SIG_XB - SIG_X2);
    private final static double[] SIG_YA = new double[SIG_P];
    private final static double[] SIG_YB = new double[SIG_P];
    private final static double[] SIG_Y = new double[SIG_N];

    static {
        for (int i = 0; i < SIG_N; i++) {
            SIG_Y[i] = evaluate(SIG_X1 + i / SIG_SX);
        }
        for (int i = 0; i < SIG_P; i++) {
            SIG_YA[i] = evaluate(SIG_XA + i / SIG_TX);
        }
        for (int i = 0; i < SIG_P; i++) {
            SIG_YB[i] = evaluate(SIG_X2 + i / SIG_TX);
        }
    }

    /**
     * Computes the sigma function as <code>1.0 / (1.0 + Math.exp(-x))</code>.
     * @param x the argument
     * @return the value of the sigma function
     */
    public static double evaluate(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    /**
     * Computes the sigma function from tabulated values as follows:
     * <ld>
     * <li>if (x &lt; {@link #SIG_XA}) y = 0.0</li>
     * <li>if (x &gt;= {@link #SIG_XB}) y = 1.0</li>
     * <li>if (x &lt; {@link #SIG_X1}) y = lin. interpolation between two nearest of {@link #SIG_P} points in outer range {@link #SIG_XA} to {@link #SIG_X1}</li>
     * <li>if (x &gt;= {@link #SIG_X2}) y = lin. interpolation between two nearest of {@link #SIG_P} points in outer range {@link #SIG_X2} to {@link #SIG_XB}</li>
     * <li>else y = lin. interpolation between two nearest of {@link #SIG_N} points in inner range {@link #SIG_X1} to {@link #SIG_X2}</li> 
     * </ld>
     * @param x the argument
     * @return the value of the sigma function
     */
    public static double evaluateOpt(double x) {
        if (x < SIG_XA) {
            return 0.0;
        } else if (x >= SIG_XB) {
            return 1.0;
        } else if (x < SIG_X1) {
            final double di = SIG_TX * (x - SIG_XA);
            final int i = (int) di;
            return SIG_YA[i] + (di - i) * (SIG_YA[i + 1] - SIG_YA[i]);
        } else if (x >= SIG_X2) {
            final double di = SIG_TX * (x - SIG_X2);
            final int i = (int) di;
            return SIG_YB[i] + (di - i) * (SIG_YB[i + 1] - SIG_YB[i]);
        } else {
            final double di = SIG_SX * (x - SIG_X1);
            final int i = (int) di;
            return SIG_Y[i] + (di - i) * (SIG_Y[i + 1] - SIG_Y[i]);
        }
    }
}
