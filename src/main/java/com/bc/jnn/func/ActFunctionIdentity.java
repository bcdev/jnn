/*
 * $Id: ActFunctionIdentity.java,v 1.5 2006-04-03 10:15:10 olaf Exp $
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

public class ActFunctionIdentity implements IActivationFunction {

    /**
     * Sets the parameter of the activation function
     * @param params ignored
     */
    public void setParameter(double[] params) {
    }

    /**
     * Evaluates the activation function on the unit passed in
     * @param unit
     */
    public final void evaluate(JnnUnit unit) {
        unit.setActivation(unit.getInput());
    }
}
