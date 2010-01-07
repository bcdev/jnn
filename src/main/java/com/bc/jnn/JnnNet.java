/*
 * $Id: JnnNet.java,v 1.16 2005-08-29 22:20:17 norman Exp $
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
package com.bc.jnn;

public final class JnnNet {

    private int _versionMajor;
    private int _versionMinor;

    private int _numLayers;
    private int _inputLayerIndex;
    private int _outputLayerIndex;

    private int _precision;

    private JnnLayer[] _layers;

    /**
     * Constructs the object with default parameters.
     */
    public JnnNet() {
        _versionMajor = JnnConstants.NN_VERSION_MAJOR;
        _versionMinor = JnnConstants.NN_VERSION_MINOR;

        _numLayers = 1;
        _inputLayerIndex = JnnConstants.NN_NOT_SET;
        _outputLayerIndex = JnnConstants.NN_NOT_SET;

        _precision = JnnConstants.NN_PREC_DOUBLE;
    }

    /**
     * Retrieves the version major as integer.
     *
     * @return
     */
    public int getVersionMajor() {
        return _versionMajor;
    }

    /**
     * Sets the version major
     *
     * @param vers
     */
    public void setVersionMajor(int vers) {
        _versionMajor = vers;
    }

    /**
     * Retrieves the version minor as integer.
     *
     * @return
     */
    public int getVersionMinor() {
        return _versionMinor;
    }

    /**
     * Sets the version minor.
     *
     * @param vers
     */
    public void setVersionMinor(int vers) {
        _versionMinor = vers;
    }

    /**
     * Retrieves the version number as String in the form of "major.minor"
     *
     * @return
     */
    public String getVersionString() {
        return new String(_versionMajor + "." + _versionMinor);
    }

    /**
     * Retrieves the number of layers of the net.
     *
     * @return
     */
    public int getNumLayers() {
        return _numLayers;
    }

    /**
     * Sets the number of layers of the net.
     *
     * @param numLayers
     */
    public void setNumLayers(int numLayers) {
        if (numLayers < 0) {
            throw new IllegalArgumentException("invalid number of layers");
        }
        _numLayers = numLayers;
        if ((_layers == null) || (_layers.length != _numLayers)) {
            _layers = new JnnLayer[_numLayers];
        }
    }

    /**
     * Retrieves the numeric precision of the neural net. Return value is either
     * NN_PREC_SINGLE or NN_PREC_DOUBLE.
     *
     * @return the precision as defined in JnnConstants
     */
    public int getPrecision() {
        return _precision;
    }

    /**
     * Sets the precision of the neural net. Values accepted are
     * NN_PREC_SINGLE or NN_PREC_DOUBLE.
     *
     * @param prec
     */
    public void setPrecision(int prec) {
        if (!((prec == JnnConstants.NN_PREC_SINGLE) || (prec == JnnConstants.NN_PREC_DOUBLE))) {
            throw new IllegalArgumentException("Invalid neural net precision value '" + prec + "'");
        }
        _precision = prec;
    }

    /**
     * Retrieves the 0-based input lasyer index
     *
     * @return
     */
    public int getInputLayerIndex() {
        return _inputLayerIndex;
    }

    /**
     * Sets the 0-based index of the input layer of the NN.
     *
     * @param index
     */
    public void setInputLayerIndex(int index) {
        _inputLayerIndex = index;
    }

    /**
     * Retrieves the 0-based output layer index
     *
     * @return
     */
    public int getOutputLayerIndex() {
        return _outputLayerIndex;
    }

    /**
     * Sets the 0-based output layer index
     *
     * @param index
     */
    public void setOutputLayerIndex(int index) {
        _outputLayerIndex = index;
    }

    /**
     * Retrieves the layer at the given index (0-based!)
     *
     * @param index
     * @return
     */
    public JnnLayer getLayerAt(int index) {
        return _layers[index];
    }

    /**
     * Sets a layer. The position in the net is determined by the index of the layer passed in.
     *
     * @param index the 0-based layer index
     * @param layer the layer to add
     */
    public void setLayerAt(int index, JnnLayer layer) {
        _layers[index] = layer;
    }


    /**
     * Initializes the neural net.
     *
     * @return true, if initialisation was successful
     */
    public boolean init() {
        StringBuffer msg = new StringBuffer(32);
        if (!init(msg))  {
            return false;
        }
        return true;
    }

    /**
     * Initializes the neural net.
     *
     * @param report a string buffer which collects the report of the performed integrity checks
     * @return true, if initialisation was successful
     * @deprecated use {@link #init(boolean, StringBuffer)} instead
     */
    public boolean init(StringBuffer report) {
        return init(false, report);
    }

    /**
     * Initializes the neural net.
     *
     * @param report a string buffer which collects the report of the performed integrity checks
     * @param optimizing true, if initialization shall use optimization techiques
     * @return true, if initialisation was successful
     */
    public boolean init(boolean optimizing, StringBuffer report) {
        return initLayerFunctions(optimizing, report) && verifyConnectionIntegrity(report);
    }


    /**
     * Verifies the integrity of the net.
     *
     * @return whether verification was successful or not
     * @deprecated use {@link #init()} instead
     */
    public boolean verifyIntegrity() {
        return init();
    }

    /**
     * Verifies the integrity of the net.
     *
     * @param report a string buffer which collects the report of the performed check
     * @return whether verification was successful or not
     * @deprecated use {@link #init(StringBuffer)} instead
     */
    public boolean verifyIntegrity(StringBuffer report) {
        return init(report);
    }

    /**
     * Processes thr input vector.
     *
     * @param input
     * @param output
     */
    public void process(double[] input, double[] output) {
        JnnLayer currentLayer;
        for (int i = 0; i < _numLayers; i++) {
            currentLayer = _layers[i];
            currentLayer.calcInputFunction();
            if ((_inputLayerIndex) == i) {
                currentLayer.setInputData(input);
            }
            currentLayer.calcActivationFunction();
            currentLayer.calcOutputFunction();
            if ((_outputLayerIndex) == i) {
                currentLayer.getOutputData(output);
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Initializes the input, activation and output functions of all layers and checks the integrity of the net.
     * @return true, if the net's integrity is guranteed
     * @param optimizing true, if initialization shall use optimization techiques
     */
    private boolean initLayerFunctions(boolean optimizing, StringBuffer msg) {
        if (_numLayers < 1) {
            msg.append("invalid number of layers: '" + _numLayers + "' (should be > 0)");
            return false;
        }
        if (_layers == null) {
            msg.append("no layers defined");
            return false;
        }
        if (_inputLayerIndex < 0 || _inputLayerIndex >= _numLayers) {
            msg.append("invalid input 0-based layer index: '" + _inputLayerIndex + "' (should be >= 0 and < " + _numLayers + ")");
            return false;
        }
        if (_outputLayerIndex < 0 || _outputLayerIndex >= _numLayers) {
            msg.append("invalid output 0-based layer index: '" + _outputLayerIndex + "' (should be >= 0 and < " + _numLayers + ")");
            return false;
        }

        for (int i = 0; i < _layers.length; i++) {
            if (_layers[i] != null) {
                if (!_layers[i].initFunctions(optimizing, msg)) {
                    return false;
                }
            } else {
                msg.append("no layer defined at 0-based index: '" + i + "'");
                return false;
            }
        }
        return true;
    }


    /**
     * Checks that all connections defined in the net are valid.
     *
     * @return whether this is true or not
     */
    private boolean verifyConnectionIntegrity(StringBuffer msg) {
        // @todo - 2 tb/tb rename this method
        JnnLayer currentLayer;
        JnnLayer testLayer;
        JnnUnit currentUnit;
        JnnUnit testUnit;
        JnnConnection currentConnection;
        int layerIndex;
        int unitIndex;

        for (int i = 0; i < _layers.length; i++) {
            currentLayer = _layers[i];
            for (int j = 0; j < currentLayer.getNumUnits(); j++) {
                currentUnit = currentLayer.getUnitAt(j);
                for (int k = 0; k < currentUnit.getNumConnections(); k++) {
                    currentConnection = currentUnit.getConnectionAt(k);
                    layerIndex = currentConnection.getSourceLayerIndex();
                    if (layerIndex < 0 || layerIndex >= _numLayers) {
                        msg.append("invalid 0-based connection source layer index: '" + layerIndex + "'");
                        return false;
                    }
                    testLayer = _layers[layerIndex];
                    unitIndex = currentConnection.getSourceUnitIndex();
                    if (unitIndex < 0 || unitIndex >= testLayer.getNumUnits()) {
                        msg.append("invalid 0-based connection source unit index: '" + unitIndex + "'");
                        return false;
                    }
                    testUnit = testLayer.getUnitAt(unitIndex);
                    if (testUnit == null) {
                        msg.append("invalid 0-based connection source unit index: '" + unitIndex + "'");
                        return false;
                    }
                    currentConnection.setInputUnit(testUnit);
                }
            }
        }
        return true;
    }
}