/*
 * $Id: ActFunctionThreshold.java,v 1.4 2005-01-31 23:35:07 norman Exp $
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
import com.bc.jnn.Jnn;

public class ActFunctionThreshold implements IActivationFunction {

    private double _thresh;

    /**
     * Sets the 'thres' parameter of the activation function
     * @param params the parameters: params[0] = threshold
     */
    public void setParameter(double[] params) {
        if (params.length < 1) {
            throw new IllegalArgumentException("params.length < 1");
        }
        _thresh = params[0];
    }

    /**
     * Evaluates the activation function on the unit passed in
     * @param unit
     */
    public final void evaluate(JnnUnit unit) {
        if (unit.getInput() > _thresh) {
            unit.setActivation(1.0);
        } else {
            unit.setActivation(0.0);
        }
    }
}
