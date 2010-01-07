/*
 * $Id: ActFunctionTangentSigmoid.java,v 1.5 2006-04-10 12:18:30 marcoz Exp $
 *
 * Copyright (C) 2002 by Brockmann Consult (info@brockmann-consult.de)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation. This program is distributed in the hope it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.bc.jnn.func;

import com.bc.jnn.JnnUnit;

public class ActFunctionTangentSigmoid implements IActivationFunction {

    private double _bias;

    /**
     * Sets the 'bias' parameter of this activation function
     * @param params  the parameters: params[0] = bias
     */
    public void setParameter(double[] params) {
        if (params.length < 1) {
            throw new IllegalArgumentException("params.length < 1");
        }
        _bias = params[0];
    }

    /**
     * Evaluates this activation function on the unit passed in
     * @param unit
     */
    public final void evaluate(JnnUnit unit) {
        double activation = (2.0 / (1.0 + Math.exp(-2.0 * (unit.getInput() + _bias)))) - 1.0;
        unit.setActivation(activation);
    }
}
