/*
 * $Id: SigmaTest.java,v 1.1 2005-08-29 22:24:34 norman Exp $
 *
 * Copyright (c) 2003 Brockmann Consult GmbH. All right reserved.
 * http://www.brockmann-consult.de
 */
package com.bc.jnn.func;

import junit.framework.TestCase;

public class SigmaTest extends TestCase {
    private static final double EPS = 1e-5;

    public void testSigmaOpt() {
        assertEquals(0.0, Sigma.evaluate(-1000), 1e-10);
        assertEquals(1.0, Sigma.evaluate(+1000), 1e-10);

        // test around XL
        final double XL = Sigma.SIG_XA;
        testSigmaOpt(XL - 10.0);
        testSigmaOpt(XL);
        testSigmaOpt(XL + 10.0);

        // test around X1
        final double X1 = Sigma.SIG_X1;
        testSigmaOpt(X1 - 1.0);
        testSigmaOpt(X1);
        testSigmaOpt(X1 + 1.0);

        // test around X0
        final double X0 = 0.0;
        testSigmaOpt(X0 - 0.1);
        testSigmaOpt(X0);
        testSigmaOpt(X0 + 0.1);

        // test around X2
        final double X2 = Sigma.SIG_X2;
        testSigmaOpt(X2 - 1.0);
        testSigmaOpt(X2);
        testSigmaOpt(X2 + 1.0);

        // test around XU
        final double XU = Sigma.SIG_XB;
        testSigmaOpt(XU - 10.0);
        testSigmaOpt(XU);
        testSigmaOpt(XU + 10.0);
    }

    public void testPerf() {
        final int n = 1000000; // 10^6
        double x, sum, mean;

        long t0;
        double dt1, dt2;

        sum = 0;
        t0 = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            x = getX(i, n);
            sum += Sigma.evaluate(x);
        }
        dt1 = (System.currentTimeMillis() - t0) / 1000.0;
        mean = sum / n;
        System.out.println("dt1 = " + dt1 + ", mean = " + mean + " (sigmoid)");


        sum = 0;
        t0 = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            x = getX(i, n);
            sum += Sigma.evaluateOpt(x);
        }
        dt2 = (System.currentTimeMillis() - t0) / 1000.0;
        mean = sum / n;
        System.out.println("dt2 = " + dt2 + ", mean = " + mean+ " (optimized sigmoid)");

        System.out.println("acceleration: " + dt1 / dt2);
    }

    private static double getX(int i, final int n) {
        return 10.0 * ((i - 1.0) / n - 0.5);
    }

    private static void testSigmaOpt(final double x) {
        assertEquals(Sigma.evaluate(x), Sigma.evaluateOpt((float) x), EPS);
    }
}
