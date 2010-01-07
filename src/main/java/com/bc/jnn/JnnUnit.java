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

public final class JnnUnit {

    private int _numConns;
    private double _input;
    private double _activation;
    private double _output;
    private double _inpScale;
    private double _inpBias;
    private double _outScale;
    private double _outBias;

    private JnnConnection[] _connections;

    /**
     * Constructs the object with default parameters.
     */
    public JnnUnit() {
        _numConns = 0;
        _input = 0.0;
        _inpScale = 1.0;
        _inpBias = 0.0;
        _outScale = 1.0;
        _outBias = 0.0;
    }

    /**
     * Retrieves the number of connections for this unit.
     * @return
     */
    public int getNumConnections() {
        return _numConns;
    }

    /**
     * Sets the number of connections of this unit
     * @param numConns
     */
    public void setNumConnections(int numConns) {
        _numConns = numConns;
        if (_connections == null || numConns != _connections.length) {
            _connections = new JnnConnection[numConns];
        }
    }

    /**
     * Retrieves the input scaling of the unit.
     * @return
     */
    public double getInputScale() {
        return _inpScale;
    }

    /**
     * Sets the input scale of this unit
     * @param inpScale
     */
    public void setInputScale(double inpScale) {
        _inpScale = inpScale;
    }

    /**
     * Retrieves the input bias of the unit.
     * @return
     */
    public double getInputBias() {
        return _inpBias;
    }

    /**
     * Sets the input bias for this unit.
     * @param inpBias
     */
    public void setInputBias(double inpBias) {
        _inpBias = inpBias;
    }

    /**
     * Retrieves the output scale of the unit.
     * @return
     */
    public double getOutputScale() {
        return _outScale;
    }

    /**
     * Sets the output scale of this unit
     * @param outScale
     */
    public void setOutputScale(double outScale) {
        _outScale = outScale;
    }

    /**
     * Retrieves the output bias of the unit
     * @return
     */
    public double getOutputBias() {
        return _outBias;
    }

    /**
     * Sets the output bias for this unit.
     * @param outBias
     */
    public void setOutputBias(double outBias) {
        _outBias = outBias;
    }

    /**
     * Retrieves the input value of the unit
     * @return
     */
    public double getInput() {
        return _input;
    }

    /**
     * Sets the input for this unit.
     * @param input
     */
    public void setInput(double input) {
        _input = input;
    }

    /**
     * Retrieves the output value of the unit
     * @return
     */
    public double getOutput() {
        return _output;
    }

    /**
     * Sets the activation for this unit.
     * @param activation
     */
    public void setActivation(double activation) {
        _activation = activation;
    }

    /**
     * Retrieves the activation value of the unit
     * @return
     */
    public double getActivation() {
        return _activation;
    }

    /**
     * Sets the output for this unit.
     * @param output
     */
    public void setOutput(double output) {
        _output = output;
    }

    /**
     * Adds a connection at the given (0-based) index.
     * @param index
     * @param connection
     */
    public void setConnectionAt(int index, JnnConnection connection) {
        _connections[index] = connection;
    }

    /**
     * Retrieves the connection at the given (0-based) index
     * @param index
     * @return
     */
    public JnnConnection getConnectionAt(int index) {
        return _connections[index];
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
        if (_numConns < 0) {
            msg.append("Invalid number of connections '" + _numConns + "' (should be >= 0)");
            return false;
        }
        if ((_numConns > 0) && (_connections == null)) {
            msg.append("No connections defined");
            return false;
        }

        for (int i = 0; i < _numConns; i++) {
            if (_connections[i] == null) {
                msg.append("No connection at 0-based index: '" + i + "'");
                return false;
            }
        }
        return true;
    }
}
