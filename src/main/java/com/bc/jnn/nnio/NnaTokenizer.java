package com.bc.jnn.nnio;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;

public class NnaTokenizer {
    private StreamTokenizer tokenizer;

    public NnaTokenizer(Reader reader) {
        if (reader == null) {
            throw new IllegalArgumentException("reader == null");
        }
        tokenizer = new StreamTokenizer(reader);
        tokenizer.resetSyntax();
        tokenizer.whitespaceChars(' ', ' ');
        tokenizer.whitespaceChars('\t', '\t');
        tokenizer.whitespaceChars('\r', '\r');
        tokenizer.commentChar(';');
        tokenizer.wordChars('A', 'Z');
        tokenizer.wordChars('a', 'z');
        tokenizer.wordChars('_', '_');
        tokenizer.wordChars('-', '-');
        tokenizer.wordChars('+', '+');
        tokenizer.wordChars('.', '.');
        tokenizer.wordChars('0', '9');
        tokenizer.eolIsSignificant(true);
    }

    public void nextToken() throws IOException {
        tokenizer.nextToken();
        if (isKeyword()) {
            try {
                final double nval = Double.parseDouble(getKeyword());
                tokenizer.nval = nval;
                tokenizer.ttype = StreamTokenizer.TT_NUMBER;
            } catch (NumberFormatException e) {
            }
        }
    }

    public char getCharacter() {
        return (char) tokenizer.ttype;
    }

    public String getKeyword() {
        return tokenizer.sval;
    }

    public double getNumber() {
        return tokenizer.nval;
    }

    public int getLineNumber() {
        return tokenizer.lineno();
    }

    public void pushBackToken() {
        tokenizer.pushBack();
    }

    public boolean isKeyword() {
        return tokenizer.ttype == StreamTokenizer.TT_WORD;
    }

    public boolean isNumber() {
        return tokenizer.ttype == StreamTokenizer.TT_NUMBER;
    }

    public boolean isEol() {
        return tokenizer.ttype == StreamTokenizer.TT_EOL;
    }

    public boolean isEof() {
        return tokenizer.ttype == StreamTokenizer.TT_EOF;
    }

    public boolean isCharacter() {
        return tokenizer.ttype >= 32;
    }
}
