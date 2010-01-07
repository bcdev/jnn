/*
 * $Id: OutputFunctionExponential.java,v 1.3 2005-01-31 23:35:07 norman Exp $
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

public class OutputFunctionExponential implements IOutputFunction {

    /**
     * Evaluates the output function on the unit passed in
     * @param unit
     */
    public final void evaluate(JnnUnit unit) {
        double output = Math.exp(unit.getOutputScale() * unit.getActivation() + unit.getOutputBias());
        unit.setOutput(output);
    }

}
