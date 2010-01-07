package com.bc.jnn.nnio;

import junit.framework.TestCase;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class NnaTokenizerTest extends TestCase {
    public void testConstructorWithNullReader() {
        try {
            new NnaTokenizer(null);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void testValidNumbers() throws IOException {
        NnaTokenizer tokenizer = new NnaTokenizer(createReader("02 -02 3.0 1.5e-3 .5e+3"));
        testToken(tokenizer, 2.0);
        testToken(tokenizer, -2.0);
        testToken(tokenizer, 3.0);
        testToken(tokenizer, 1.5e-3);
        testToken(tokenizer, 0.5e+3);
    }

    public void testCommentsAfterSectionHead() throws IOException {
        NnaTokenizer tokenizer = new NnaTokenizer(createReader("[Layer(3)] ; Comment\n"));
        testToken(tokenizer, '[');
        testToken(tokenizer, "Layer");
        testToken(tokenizer, '(');
        testToken(tokenizer, 3);
        testToken(tokenizer, ')');
        testToken(tokenizer, ']');
        testEOL(tokenizer);
    }

    public void testCommentsAfterStatement() throws IOException {
        NnaTokenizer tokenizer = new NnaTokenizer(createReader("\nOutFunc = Linear ; ID = 11\n"));
        testEOL(tokenizer);
        testToken(tokenizer, "OutFunc");
        testToken(tokenizer, '=');
        testToken(tokenizer, "Linear");
        testEOL(tokenizer);
    }

    public void testValidNnaTokens() throws IOException {

        NnaTokenizer tokenizer = new NnaTokenizer(createReader("[Net] \n" +
                                                               "[ Unit (5 ,3)]  \n" +
                                                               "; Comment\n" +
                                                               "  Conn(2, 45) = 7,482,-2.5654  \r\n\r\n" +
                                                               "NumConns = 9"));
        testToken(tokenizer, '[');
        testToken(tokenizer, "Net");
        testToken(tokenizer, ']');
        testEOL(tokenizer);
        testToken(tokenizer, '[');
        testToken(tokenizer, "Unit");
        testToken(tokenizer, '(');
        testToken(tokenizer, 5);
        testToken(tokenizer, ',');
        testToken(tokenizer, 3);
        testToken(tokenizer, ')');
        testToken(tokenizer, ']');
        testEOL(tokenizer);
        testEOL(tokenizer);
        testToken(tokenizer, "Conn");
        testToken(tokenizer, '(');
        testToken(tokenizer, 2);
        testToken(tokenizer, ',');
        testToken(tokenizer, 45);
        testToken(tokenizer, ')');
        testToken(tokenizer, '=');
        testToken(tokenizer, 7);
        testToken(tokenizer, ',');
        testToken(tokenizer, 482);
        testToken(tokenizer, ',');
        testToken(tokenizer, -2.5654);
        testEOL(tokenizer);
        testEOL(tokenizer);
        testToken(tokenizer, "NumConns");
        testToken(tokenizer, '=');
        testToken(tokenizer, 9);
        testEOF(tokenizer);
    }

    private void testToken(NnaTokenizer tokenizer, String kw) throws IOException {
        tokenizer.nextToken();
        assertTrue(tokenizer.isKeyword());
        assertEquals(kw, tokenizer.getKeyword());
    }

    private void testToken(NnaTokenizer tokenizer, double n) throws IOException {
        tokenizer.nextToken();
        assertTrue(tokenizer.isNumber());
        assertEquals(n, tokenizer.getNumber(), 1.e-10);
    }

    private void testToken(NnaTokenizer tokenizer, int n) throws IOException {
        tokenizer.nextToken();
        assertTrue(tokenizer.isNumber());
        assertEquals(n, (int) Math.floor(tokenizer.getNumber()));
    }

    private void testToken(NnaTokenizer tokenizer, char c) throws IOException {
        tokenizer.nextToken();
        assertTrue(tokenizer.isCharacter());
        assertEquals(c, tokenizer.getCharacter());
    }

    private void testEOL(NnaTokenizer tokenizer) throws IOException {
        tokenizer.nextToken();
        assertTrue(tokenizer.isEol());
    }

    private void testEOF(NnaTokenizer tokenizer) throws IOException {
        tokenizer.nextToken();
        assertTrue(tokenizer.isEof());
    }

    static Reader createReader(String code) {
        return new StringReader(code);
    }
}
