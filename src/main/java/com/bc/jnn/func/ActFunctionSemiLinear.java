/*
 * $Id: ActFunctionSemiLinear.java,v 1.7 2006-04-03 10:35:29 olaf Exp $
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

public class ActFunctionSemiLinear implements IActivationFunction {

    private double _thresh;
    private double _slope;

    /**
     * Sets the 'thres' and 'slope' parameters of the activation function
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
     * @param unit
     */
    public final void evaluate(JnnUnit unit) {
        double activation = _slope * (unit.getInput() - _thresh);
        if (activation < 0.0) {
            activation = 0.0;
        }
        if (activation > 1.0) {
            activation = 1.0;
        }
        unit.setActivation(activation);
    }

}
