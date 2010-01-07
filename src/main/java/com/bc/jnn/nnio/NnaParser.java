package com.bc.jnn.nnio;

import com.bc.jnn.JnnException;

import java.io.IOException;
import java.io.Reader;

public class NnaParser {
    private static final String KW_NET = "Net";
    private static final String KW_LAYER = "Layer";
    private static final String KW_UNIT = "Unit";
    private static final String KW_CONNECTION = "C";

    private NnaTokenizer tokenizer;
    private NnaParserHandler handler;

    public NnaParser() {
    }

    public void parse(Reader reader, NnaParserHandler handler) throws IOException, JnnException {
        this.tokenizer = new NnaTokenizer(reader);
        this.handler = handler != null ? handler : new NullHandler();
        try {
            parse();
        } finally {
            this.tokenizer = null;
            this.handler = null;
        }
    }

    private void parse() throws IOException, JnnException {
        parseNet();
        parseLayersAndUnits();
        parseZeroOrMoreEols();
        parseEof();
    }


    private void parseNet() throws IOException, JnnException {
        parseZeroOrMoreEols();

        parseCharacter('[');
        parseKeyword(KW_NET);
        parseCharacter(']');
        parseEolOrEof();

        handler.handleNetStart();
        parseNetEntries();
        handler.handleNetEnd();
    }

    private void parseLayersAndUnits() throws IOException, JnnException {
        while (true) {
            parseZeroOrMoreEols();
            if (tokenizer.isEof()) {
                break;
            }
            parseLayerOrUnit();
        }
    }

    private void parseLayerOrUnit() throws IOException, JnnException {
        parseCharacter('[');
        String s = parseKeyword();
        if (s.equalsIgnoreCase(KW_LAYER)) {
            parseCharacter('(');
            int layerIndex = parseIndex("layer index");
            parseCharacter(')');
            parseCharacter(']');
            parseEolOrEof();
            handler.handleLayerStart(layerIndex - 1);
            parseLayerEntries();
            handler.handleLayerEnd();
        } else if (s.equals(KW_UNIT)) {
            parseCharacter('(');
            int layerIndex = parseIndex("layer index");
            parseCharacter(',');
            int unitIndex = parseIndex("unit index");
            parseCharacter(')');
            parseCharacter(']');
            parseEolOrEof();
            handler.handleUnitStart(layerIndex - 1, unitIndex - 1);
            parseUnitEntries();
            handler.handleUnitEnd();
        } else {
            tokenizer.pushBackToken();
            throw new JnnParseException(createUnexpectedTokenMessage("'Layer' or 'Unit'"));
        }
    }

    private void parseNetEntries() throws IOException, JnnException {
        parseZeroOrMoreEols();
        while (true) {
            tokenizer.nextToken();
            if (tokenizer.isKeyword()) {
                String key = tokenizer.getKeyword();
                parseCharacter('=');
                tokenizer.nextToken();
                if (tokenizer.isKeyword()) {
                    String value = tokenizer.getKeyword();
                    parseEolOrEof();
                    handler.handleNetEntry(key, value);
                } else {
                    double value = tokenizer.getNumber();
                    parseEolOrEof();
                    handler.handleNetEntry(key, value);
                }
            } else if (tokenizer.isEol()) {
                // continue
            } else {
                tokenizer.pushBackToken();
                break;
            }
        }
    }

    private void parseLayerEntries() throws IOException, JnnException {
        parseZeroOrMoreEols();
        while (true) {
            tokenizer.nextToken();
            if (tokenizer.isKeyword()) {
                String key = tokenizer.getKeyword();
                parseCharacter('=');
                tokenizer.nextToken();
                if (tokenizer.isKeyword()) {
                    String value = tokenizer.getKeyword();
                    parseEolOrEof();
                    handler.handleLayerEntry(key, value);
                } else {
                    double value = tokenizer.getNumber();
                    parseEolOrEof();
                    handler.handleLayerEntry(key, value);
                }
            } else if (tokenizer.isEol()) {
                // continue
            } else {
                tokenizer.pushBackToken();
                break;
            }
        }
    }

    private void parseUnitEntries() throws IOException, JnnException {
        parseZeroOrMoreEols();
        while (true) {
            tokenizer.nextToken();
            if (tokenizer.isKeyword()) {
                String key = tokenizer.getKeyword();
                if (key.equalsIgnoreCase(KW_CONNECTION)) {
                    parseCharacter('(');
                    int connIndex = parseIndex("connection index");
                    parseCharacter(')');
                    parseCharacter('=');
                    int connLayerIndex = parseIndex("connected layer index");
                    parseCharacter(',');
                    int connUnitIndex = parseIndex("connected unit index");
                    parseCharacter(',');
                    double weight = parseNumber("connection weight");
                    parseEolOrEof();
                    handler.handleUnitConnection(connIndex - 1, connLayerIndex - 1, connUnitIndex - 1, weight);
                } else {
                    parseCharacter('=');
                    tokenizer.nextToken();
                    if (tokenizer.isKeyword()) {
                        String value = tokenizer.getKeyword();
                        parseEolOrEof();
                        handler.handleUnitEntry(key, value);
                    } else {
                        double value = tokenizer.getNumber();
                        parseEolOrEof();
                        handler.handleUnitEntry(key, value);
                    }
                }
            } else if (tokenizer.isEol()) {
                // continue
            } else {
                tokenizer.pushBackToken();
                break;
            }
        }
    }

    private String parseKeyword() throws IOException, JnnException {
        tokenizer.nextToken();
        if (!tokenizer.isKeyword()) {
            tokenizer.pushBackToken();
            throw new JnnParseException(createUnexpectedTokenMessage("keyword"));
        }
        return tokenizer.getKeyword();
    }

    private int parseIndex(String type) throws IOException, JnnException {
        tokenizer.nextToken();
        if (!tokenizer.isNumber()) {
            tokenizer.pushBackToken();
            throw new JnnParseException(createUnexpectedTokenMessage(type));
        }
        int index = new Double(tokenizer.getNumber()).intValue();
        return index;
    }

    private double parseNumber(String type) throws IOException, JnnException {
        tokenizer.nextToken();
        if (!tokenizer.isNumber()) {
            tokenizer.pushBackToken();
            throw new JnnParseException(createUnexpectedTokenMessage(type));
        }
        return tokenizer.getNumber();
    }

    private void parseCharacter(char character) throws IOException, JnnException {
        tokenizer.nextToken();
        if (!tokenizer.isCharacter() || tokenizer.getCharacter() != character) {
            tokenizer.pushBackToken();
            throw new JnnParseException(createUnexpectedTokenMessage("'" + character + "'"));
        }
    }

    private void parseKeyword(String keyword) throws IOException, JnnException {
        tokenizer.nextToken();
        if (!tokenizer.isKeyword() || !keyword.equalsIgnoreCase(tokenizer.getKeyword())) {
            tokenizer.pushBackToken();
            throw new JnnParseException(createUnexpectedTokenMessage("'" + keyword + "'"));
        }
    }

    private void parseEof() throws IOException, JnnException {
        tokenizer.nextToken();
        if (!tokenizer.isEof()) {
            tokenizer.pushBackToken();
            throw new JnnParseException(createUnexpectedTokenMessage("end of file"));
        }
    }

    private void parseEolOrEof() throws IOException, JnnException {
        tokenizer.nextToken();
        if (!tokenizer.isEol() && !tokenizer.isEof()) {
            tokenizer.pushBackToken();
            throw new JnnParseException(createUnexpectedTokenMessage("end of line"));
        }
    }

    private void parseZeroOrMoreEols() throws IOException {
        while (true) {
            tokenizer.nextToken();
            if (!tokenizer.isEol()) {
                tokenizer.pushBackToken();
                break;
            }
        }
    }

    private String createUnexpectedTokenMessage(String type) {
        StringBuffer sb = new StringBuffer(32);
        sb.append("line ");
        sb.append(tokenizer.getLineNumber());
        sb.append(": ");
        sb.append(type);
        sb.append(" expected, but found ");
        if (tokenizer.isEof()) {
            sb.append("end of file");
        } else if (tokenizer.isEol()) {
            sb.append("end of line");
        } else if (tokenizer.isKeyword()) {
            sb.append("keyword '");
            sb.append(tokenizer.getKeyword());
            sb.append("'");
        } else if (tokenizer.isNumber()) {
            sb.append("number '");
            sb.append(tokenizer.getNumber());
            sb.append("'");
        } else if (tokenizer.isCharacter()) {
            sb.append("character '");
            sb.append(tokenizer.getCharacter());
            sb.append("'");
        } else {
            sb.append("nothing");
        }
        return sb.toString();
    }


    private class NullHandler implements NnaParserHandler {
        public void handleNetStart() {
        }

        public void handleNetEntry(String key, String value) {
        }

        public void handleNetEntry(String key, double value) {
        }

        public void handleNetEnd() {
        }

        public void handleLayerStart(int layerIndex) {
        }

        public void handleLayerEntry(String key, String value) {
        }

        public void handleLayerEntry(String key, double value) {
        }

        public void handleLayerEnd() {
        }

        public void handleUnitStart(int layerIndex, int unitIndex) {
        }

        public void handleUnitEntry(String key, String value) {
        }

        public void handleUnitEntry(String key, double value) {
        }

        public void handleUnitConnection(int connIndex, int connLayerIndex, int connUnitIndex, double weight) {
        }

        public void handleUnitEnd() {
        }
    }

}
