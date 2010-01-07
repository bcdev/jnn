/*
 * $Id: ActFunctionSigmoid_1.java,v 1.11 2005-08-29 22:24:34 norman Exp $
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

import com.bc.jnn.JnnUnit;

public class ActFunctionSigmoid_1 implements IActivationFunction {

    private double _slope;
    private double _thresh;

    /**
     * Sets the 'thres' and 'slope' parameters of the activation function
     *
     * @param params the parameters: params[0] = threshold, params[1] = slope
     */
    public void setParameter(double[] params) {
        if (params.length < 2) {
            throw new IllegalArgumentException("params.length < 2");
        }
        _thresh = params[0];
        _slope = params[1];
    }

    /**
     * Evaluates the activation function on the unit passed in
     *
     * @param unit
     */
    public final void evaluate(JnnUnit unit) {
        double x = _slope * (unit.getInput() - _thresh);
        unit.setActivation(Sigma.evaluate(x));
    }
}
