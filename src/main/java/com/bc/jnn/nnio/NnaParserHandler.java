package com.bc.jnn.nnio;

import com.bc.jnn.JnnException;


public interface NnaParserHandler {

    void handleNetStart() throws JnnException;

    void handleNetEntry(String key, String value) throws JnnException;

    void handleNetEntry(String key, double value) throws JnnException;

    void handleNetEnd() throws JnnException;


    void handleLayerStart(int layerIndex) throws JnnException;

    void handleLayerEntry(String key, String value) throws JnnException;

    void handleLayerEntry(String key, double value) throws JnnException;

    void handleLayerEnd() throws JnnException;


    void handleUnitStart(int layerIndex, int unitIndex) throws JnnException;

    void handleUnitEntry(String key, String value) throws JnnException;

    void handleUnitEntry(String key, double value) throws JnnException;

    void handleUnitConnection(int connIndex, int layerIndex, int unitIndex, double weight) throws JnnException;

    void handleUnitEnd() throws JnnException;
}
