/*
 * $Id: IOutputFunction.java,v 1.2 2005-01-31 23:36:27 norman Exp $
 *
 * Copyright (C) 2002,2003 by Brockmann Consult (info@brockmann-consult.de)
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

public interface IOutputFunction {

    /**
     * Evaluates the output function on the unit passed in
     * @param unit
     */
    void evaluate(JnnUnit unit);
}
