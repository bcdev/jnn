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

public final class JnnConnection implements Cloneable {

    private int srcLayerIndex;
    private int srcUnitIndex;
    private double weight;
    private JnnUnit inUnit;

    /**
     * Retrieves the (0-based) source layer index of the connection
     *
     * @return
     */
    public int getSourceLayerIndex() {
        return srcLayerIndex;
    }

    /**
     * Sets the (0-based) source layer index for this connection.
     *
     * @param index
     */
    public void setSourceLayerIndex(int index) {
        srcLayerIndex = index;
    }

    /**
     * Retrieves the (0-based) source unit index of the connection
     *
     * @return
     */
    public int getSourceUnitIndex() {
        return srcUnitIndex;
    }

    /**
     * Sets the (0-based) source unit index for this connection.
     *
     * @param index
     */
    public void setSourceUnitIndex(int index) {
        srcUnitIndex = index;
    }

    /**
     * Retrieves the weight of the connection.
     *
     * @return
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets the weight for the connection.
     *
     * @param weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Sets the input unit for this connection
     *
     * @param inUnit
     */
    public void setInputUnit(JnnUnit inUnit) {
        this.inUnit = inUnit;
    }

    /**
     * Gets the input unit for this connection
     */
    public JnnUnit getInputUnit() {
        return inUnit;
    }

    @Override
    public JnnConnection clone() {
        try {
            JnnConnection clonedConnection = (JnnConnection) super.clone();
            clonedConnection.inUnit = null;
            return clonedConnection;
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }
}
