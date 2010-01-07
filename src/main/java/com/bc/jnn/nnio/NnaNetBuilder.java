/*
 * $Id: NnaNetBuilder.java,v 1.3 2005-02-02 23:11:50 norman Exp $
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
package com.bc.jnn.nnio;

import com.bc.jnn.JnnConnection;
import com.bc.jnn.JnnConstants;
import com.bc.jnn.JnnException;
import com.bc.jnn.JnnLayer;
import com.bc.jnn.JnnNet;
import com.bc.jnn.JnnUnit;

public class NnaNetBuilder implements NnaParserHandler {

    private JnnNet currentNet;
    private JnnLayer currentLayer;
    private JnnUnit currentUnit;

    public JnnNet getCurrentNet() {
        return currentNet;
    }

    public void handleNetStart() {
        currentNet = new JnnNet();
    }

    public void handleNetEntry(String key, String value) throws JnnException {
        if (key.equalsIgnoreCase(NnaDef.NN_NAME_PRECISION)) {
            if (NnaDef.NN_NAME_PRECISION_VALUE_SET[0].equalsIgnoreCase(value)) {
                currentNet.setPrecision(JnnConstants.NN_PREC_SINGLE);
            } else if (NnaDef.NN_NAME_PRECISION_VALUE_SET[1].equalsIgnoreCase(value)) {
                currentNet.setPrecision(JnnConstants.NN_PREC_DOUBLE);
            } else {
                throw new JnnException("Invalid net section entry: '" + key + " = " + value + "'.");
            }
        }  else {
            throw new JnnException("Invalid net section entry: '" + key + " = " + value + "'.");
        }
    }

    public void handleNetEntry(String key, double value) throws JnnException {
        if (key.equalsIgnoreCase(NnaDef.NN_NAME_MIN_VERSION)) {
            currentNet.setVersionMinor((int) value);
        } else if (key.equalsIgnoreCase(NnaDef.NN_NAME_MAJ_VERSION)) {
            currentNet.setVersionMajor((int) value);
        } else if (key.equalsIgnoreCase(NnaDef.NN_NAME_NUM_LAYERS)) {
            currentNet.setNumLayers((int) value);
        } else if (key.equalsIgnoreCase(NnaDef.NN_NAME_INP_LAYER)) {
            currentNet.setInputLayerIndex((int) value - 1);
        } else if (key.equalsIgnoreCase(NnaDef.NN_NAME_OUT_LAYER)) {
            currentNet.setOutputLayerIndex((int) value - 1);
        } else {
            throw new JnnException("Invalid net section entry: '" + key + " = " + value + "'.");
        }
    }

    public void handleNetEnd() throws JnnException {
    }

    public void handleLayerStart(int layerIndex) throws JnnException {
        currentLayer = new JnnLayer();
        currentNet.setLayerAt(layerIndex, currentLayer);
    }

    public void handleLayerEntry(String key, String value) throws JnnException {
        if (key.equalsIgnoreCase(NnaDef.NN_NAME_INP_FNID)) {
            currentLayer.setInputFunction(NnaDef.functionNameToIndex(value));
        } else if (key.equalsIgnoreCase(NnaDef.NN_NAME_ACT_FNID)) {
            currentLayer.setActivationFunction(NnaDef.functionNameToIndex(value));
        } else if (key.equalsIgnoreCase(NnaDef.NN_NAME_OUT_FNID)) {
            currentLayer.setOutputFunction(NnaDef.functionNameToIndex(value));
        } else {
            throw new JnnException("Invalid layer section entry: '" + key + " = " + value + "'.");
        }
    }

    public void handleLayerEntry(String key, double value) throws JnnException {
        if (key.equalsIgnoreCase(NnaDef.NN_NAME_NUM_UNITS)) {
            currentLayer.setNumUnits((int) value);
        } else if (key.equalsIgnoreCase(NnaDef.NN_NAME_ACT_THRES)) {
            currentLayer.setActivationThreshold(value);
        } else if (key.equalsIgnoreCase(NnaDef.NN_NAME_ACT_SLOPE)) {
            currentLayer.setActivationSlope(value);
        } else {
            throw new JnnException("Invalid layer section entry: '" + key + " = " + value + "'.");
        }
    }

    public void handleLayerEnd() throws JnnException {
        currentLayer = null;
    }

    public void handleUnitStart(int layerIndex, int unitIndex) throws JnnException {
        currentUnit = new JnnUnit();
        JnnLayer layer = currentNet.getLayerAt(layerIndex);
        if (layer == null) {
            throw new JnnException("[Unit(" + layerIndex + "," + unitIndex + "]: Illegal layer index '" + layerIndex + "'.");
        }
        layer.setUnitAt(unitIndex, currentUnit);
    }

    public void handleUnitEntry(String key, String value) throws JnnException {
        throw new JnnException("Invalid unit section entry: '" + key + " = " + value + "'.");
    }

    public void handleUnitEntry(String key, double value) throws JnnException {
        if (key.equalsIgnoreCase(NnaDef.NN_NAME_NUM_CONNS)) {
            currentUnit.setNumConnections((int) value);
        } else if (key.equalsIgnoreCase(NnaDef.NN_NAME_INP_SCALE)) {
            currentUnit.setInputScale(value);
        } else if (key.equalsIgnoreCase(NnaDef.NN_NAME_INP_BIAS)) {
            currentUnit.setInputBias(value);
        } else if (key.equalsIgnoreCase(NnaDef.NN_NAME_OUT_SCALE)) {
            currentUnit.setOutputScale(value);
        } else if (key.equalsIgnoreCase(NnaDef.NN_NAME_OUT_BIAS)) {
            currentUnit.setOutputBias(value);
        } else {
            throw new JnnException("Invalid unit section entry: '" + key + " = " + value + "'.");
        }
    }

    public void handleUnitConnection(int connIndex, int layerIndex, int unitIndex, double weight) throws JnnException {
        JnnConnection connection = new JnnConnection();
        connection.setSourceLayerIndex(layerIndex);
        connection.setSourceUnitIndex(unitIndex);
        connection.setWeight(weight);
        currentUnit.setConnectionAt(connIndex, connection);
    }

    public void handleUnitEnd() throws JnnException {
        currentUnit = null;
    }
}