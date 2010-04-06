/*
 * $Id: JnnUnit.java,v 1.10 2005-02-02 23:11:50 norman Exp $
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

import java.text.MessageFormat;

public final class JnnUnit implements Cloneable {

    private int numConns;
    private double input;
    private double activation;
    private double output;
    private double inpScale;
    private double inpBias;
    private double outScale;
    private double outBias;

    private JnnConnection[] connections;

    /**
     * Constructs the object with default parameters.
     */
    public JnnUnit() {
        numConns = 0;
        input = 0.0;
        inpScale = 1.0;
        inpBias = 0.0;
        outScale = 1.0;
        outBias = 0.0;
    }

    /**
     * Retrieves the number of connections for this unit.
     * @return
     */
    public int getNumConnections() {
        return numConns;
    }

    /**
     * Sets the number of connections of this unit
     * @param numConns
     */
    public void setNumConnections(int numConns) {
        this.numConns = numConns;
        if (connections == null || numConns != connections.length) {
            connections = new JnnConnection[numConns];
        }
    }

    /**
     * Retrieves the input scaling of the unit.
     * @return
     */
    public double getInputScale() {
        return inpScale;
    }

    /**
     * Sets the input scale of this unit
     * @param inpScale
     */
    public void setInputScale(double inpScale) {
        this.inpScale = inpScale;
    }

    /**
     * Retrieves the input bias of the unit.
     * @return
     */
    public double getInputBias() {
        return inpBias;
    }

    /**
     * Sets the input bias for this unit.
     * @param inpBias
     */
    public void setInputBias(double inpBias) {
        this.inpBias = inpBias;
    }

    /**
     * Retrieves the output scale of the unit.
     * @return
     */
    public double getOutputScale() {
        return outScale;
    }

    /**
     * Sets the output scale of this unit
     * @param outScale
     */
    public void setOutputScale(double outScale) {
        this.outScale = outScale;
    }

    /**
     * Retrieves the output bias of the unit
     * @return
     */
    public double getOutputBias() {
        return outBias;
    }

    /**
     * Sets the output bias for this unit.
     * @param outBias
     */
    public void setOutputBias(double outBias) {
        this.outBias = outBias;
    }

    /**
     * Retrieves the input value of the unit
     * @return
     */
    public double getInput() {
        return input;
    }

    /**
     * Sets the input for this unit.
     * @param input
     */
    public void setInput(double input) {
        this.input = input;
    }

    /**
     * Retrieves the output value of the unit
     * @return
     */
    public double getOutput() {
        return output;
    }

    /**
     * Sets the activation for this unit.
     * @param activation
     */
    public void setActivation(double activation) {
        this.activation = activation;
    }

    /**
     * Retrieves the activation value of the unit
     * @return
     */
    public double getActivation() {
        return activation;
    }

    /**
     * Sets the output for this unit.
     * @param output
     */
    public void setOutput(double output) {
        this.output = output;
    }

    /**
     * Adds a connection at the given (0-based) index.
     * @param index
     * @param connection
     */
    public void setConnectionAt(int index, JnnConnection connection) {
        connections[index] = connection;
    }

    /**
     * Retrieves the connection at the given (0-based) index
     * @param index
     * @return
     */
    public JnnConnection getConnectionAt(int index) {
        return connections[index];
    }

    /**
     * Checks the unit settings for correctness
     * @return whether the check was successful or not
     */
    public boolean verifyIntegrity() {
        return verifyIntegrity(new StringBuffer());
    }

    /**
     * Checks the unit settings for correctness
     * @return whether the check was successful or not
     */
    public boolean verifyIntegrity(StringBuffer msg) {
        if (numConns < 0) {
            msg.append(MessageFormat.format("Invalid number of connections ''{0}'' (should be >= 0)", numConns));
            return false;
        }
        if ((numConns > 0) && (connections == null)) {
            msg.append("No connections defined");
            return false;
        }

        for (int i = 0; i < numConns; i++) {
            if (connections[i] == null) {
                msg.append(MessageFormat.format("No connection at 0-based index: ''{0}''", i));
                return false;
            }
        }
        return true;
    }

    @Override
    public JnnUnit clone() {
        try {
            JnnUnit clonedUnit = (JnnUnit) super.clone();
            JnnConnection[] clonedConnections = connections.clone();
            for (int i = 0; i < clonedConnections.length; i++) {
                clonedConnections[i] = clonedConnections[i].clone();
            }
            clonedUnit.connections = clonedConnections;
            return clonedUnit;
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }
}
