/*
 * $Id: JnnConnection.java,v 1.5 2005-02-02 23:11:50 norman Exp $
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
package com.bc.jnn;

public final class JnnConnection {

    private int _srcLayerIndex;
    private int _srcUnitIndex;
    private double _weight;
    private JnnUnit _inUnit;

    /**
     * Retrieves the (0-based) source layer index of the connection
     *
     * @return
     */
    public int getSourceLayerIndex() {
        return _srcLayerIndex;
    }

    /**
     * Sets the (0-based) source layer index for this connection.
     *
     * @param index
     */
    public void setSourceLayerIndex(int index) {
        _srcLayerIndex = index;
    }

    /**
     * Retrieves the (0-based) source unit index of the connection
     *
     * @return
     */
    public int getSourceUnitIndex() {
        return _srcUnitIndex;
    }

    /**
     * Sets the (0-based) source unit index for this connection.
     *
     * @param index
     */
    public void setSourceUnitIndex(int index) {
        _srcUnitIndex = index;
    }

    /**
     * Retrieves the weight of the connection.
     *
     * @return
     */
    public double getWeight() {
        return _weight;
    }

    /**
     * Sets the weight for the connection.
     *
     * @param weight
     */
    public void setWeight(double weight) {
        _weight = weight;
    }

    /**
     * Sets the input unit for this connection
     *
     * @param inUnit
     */
    public void setInputUnit(JnnUnit inUnit) {
        _inUnit = inUnit;
    }

    /**
     * Gets the input unit for this connection
     */
    public JnnUnit getInputUnit() {
        return _inUnit;
    }
}
