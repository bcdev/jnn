package com.bc.jnn.nnio;

import java.util.HashMap;

class ParserHandlerMock implements NnaParserHandler {
    private HashMap map = new HashMap();
    private String currentNet;
    private String currentLayer;
    private String currentUnit;

    public HashMap getMap() {
        return map;
    }

    public void handleNetStart() {
        currentNet = "Net";
        map.put(currentNet + ".Start", "Start");
    }

    public void handleNetEntry(String key, String value) {
        map.put(currentNet + "." + key, value);
    }

    public void handleNetEntry(String key, double value) {
        map.put(currentNet + "." + key, new Double(value));
    }

    public void handleNetEnd() {
        map.put(currentNet + ".End", "End");
    }

    public void handleLayerStart(int layerIndex) {
        currentLayer = "Layer." + layerIndex;
        map.put(currentLayer + ".Start", "Start");
    }

    public void handleLayerEntry(String key, String value) {
        map.put(currentLayer + "." + key, value);
    }

    public void handleLayerEntry(String key, double value) {
        map.put(currentLayer + "." + key, new Double(value));
    }

    public void handleLayerEnd() {
        map.put(currentLayer + ".End", "End");
    }

    public void handleUnitStart(int layerIndex, int unitIndex) {
        currentUnit = "Unit." + layerIndex + "." + unitIndex;
        map.put(currentUnit + ".Start", "Start");
    }

    public void handleUnitEntry(String key, String value) {
        map.put(currentUnit + "." + key, value);
    }

    public void handleUnitEntry(String key, double value) {
        map.put(currentUnit + "." + key, new Double(value));
    }

    public void handleUnitConnection(int connIndex, int connLayerIndex, int connUnitIndex, double weight) {
        map.put(currentUnit + ".C." + connIndex + "." + connLayerIndex + "." + connUnitIndex, new Double(weight));
    }

    public void handleUnitEnd() {
        map.put(currentUnit + ".End", "End");
    }
}
