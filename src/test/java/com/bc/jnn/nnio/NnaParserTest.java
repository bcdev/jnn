package com.bc.jnn.nnio;

import junit.framework.TestCase;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.bc.jnn.JnnException;

public class NnaParserTest extends TestCase {
    private static final String TEST_CODE = "; The definition of the neural net\n" +
            " [Net]\n" +
            "   NumLayers= 3 \n" +
            "   InpLayer = 1 \n" +
            "   OutLayer = 3 \n" +
            "\n" +
            "; The definition of the first layer \n" +
            "[ Layer( 1 )] \n" +
            "   NumUnits= 2 \n" +
            "   InpFunc = Sum_1 \n" +
            "   ActFunc = Identity \n" +
            "   OutFunc = Linear " +
            "\n" +
            "; The definition of the hidden layer \n" +
            "[Layer(2)] \n" +
            "   NumUnits= 4 \n" +
            "   InpFunc = Sum_1 \n" +
            "   ActFunc = Sigmoid_1 \n" +
            "   ActSlope = 0.13 \n" +
            "   OutFunc = Linear " +
            "\n" +
            "; The definition of the output layer \n" +
            "[Layer(3)] \n" +
            "   NumUnits= 1 \n" +
            "   InpFunc = Sum_1 \n" +
            "   ActFunc = Sigmoid_1 \n" +
            "   ActSlope = 0.01 \n" +
            "   OutFunc = Linear \n" +
            "\n" +
            "[Unit(1,1)] \n" +
            "   OutScale = 10.65 \n" +
            "   OutBias  = -1.026 \n" +
            "   NumConns = 0 \n" +
            "\n" +
            "[Unit(1,2)] \n" +
            "   OutScale = 0.0067 \n" +
            "   OutBias  = -4.33 \n" +
            "   NumConns = 0 \n" +
            "\n" +
            "[Unit(2,1)] \n" +
            "   OutScale = 1. \n" +
            "   OutBias  = 0. \n" +
            "   NumConns = 2 \n" +
            "   C(1) = 1 , 1 ,7.072e-01 \n" +
            "   C(2) = 1 , 2 ,-7.06e-03 \n";


    public void testValidNet() throws IOException, JnnException {
        ParserHandlerMock handler = new ParserHandlerMock();
        new NnaParser().parse(createReader(TEST_CODE), handler);
        final HashMap m = handler.getMap();

        assertEquals("Start", m.get("Net.Start"));
        assertEquals(new Double(3), m.get("Net.NumLayers"));
        assertEquals(new Double(1), m.get("Net.InpLayer"));
        assertEquals(new Double(3), m.get("Net.OutLayer"));
        assertEquals("End", m.get("Net.End"));

        assertEquals("Start", m.get("Layer.0.Start"));
        assertEquals(new Double(2), m.get("Layer.0.NumUnits"));
        assertEquals("Sum_1", m.get("Layer.0.InpFunc"));
        assertEquals("Identity", m.get("Layer.0.ActFunc"));
        assertEquals("Linear", m.get("Layer.0.OutFunc"));
        assertEquals("End", m.get("Layer.0.End"));

        assertEquals("Start", m.get("Layer.1.Start"));
        assertEquals(new Double(4), m.get("Layer.1.NumUnits"));
        assertEquals("Sum_1", m.get("Layer.1.InpFunc"));
        assertEquals("Sigmoid_1", m.get("Layer.1.ActFunc"));
        assertEquals(new Double(0.13), m.get("Layer.1.ActSlope"));
        assertEquals("Linear", m.get("Layer.1.OutFunc"));
        assertEquals("End", m.get("Layer.1.End"));

        assertEquals("Start", m.get("Layer.2.Start"));
        assertEquals(new Double(1), m.get("Layer.2.NumUnits"));
        assertEquals("Sum_1", m.get("Layer.2.InpFunc"));
        assertEquals("Sigmoid_1", m.get("Layer.2.ActFunc"));
        assertEquals(new Double(0.01), m.get("Layer.2.ActSlope"));
        assertEquals("Linear", m.get("Layer.2.OutFunc"));
        assertEquals("End", m.get("Layer.2.End"));

        assertEquals("Start", m.get("Unit.0.0.Start"));
        assertEquals(new Double(10.65), m.get("Unit.0.0.OutScale"));
        assertEquals(new Double(-1.026), m.get("Unit.0.0.OutBias"));
        assertEquals(new Double(0), m.get("Unit.0.0.NumConns"));
        assertEquals("End", m.get("Unit.0.0.End"));

        assertEquals("Start", m.get("Unit.0.1.Start"));
        assertEquals(new Double(0.0067), m.get("Unit.0.1.OutScale"));
        assertEquals(new Double(-4.33), m.get("Unit.0.1.OutBias"));
        assertEquals(new Double(0), m.get("Unit.0.1.NumConns"));
        assertEquals("End", m.get("Unit.0.1.End"));

        assertEquals("Start", m.get("Unit.1.0.Start"));
        assertEquals(new Double(1), m.get("Unit.1.0.OutScale"));
        assertEquals(new Double(0), m.get("Unit.1.0.OutBias"));
        assertEquals(new Double(2), m.get("Unit.1.0.NumConns"));
        assertEquals(new Double(7.072e-01), m.get("Unit.1.0.C.0.0.0"));
        assertEquals(new Double(-7.06e-03), m.get("Unit.1.0.C.1.0.1"));
        assertEquals("End", m.get("Unit.1.0.End"));
    }

    public void testNullReaderNotAllowed() throws IOException, JnnException {
        try {
            new NnaParser().parse(null, new ParserHandlerMock());
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void testNullHandlerAllowed() throws IOException, JnnException {
        try {
            new NnaParser().parse(createReader(TEST_CODE), null);
        } catch (IllegalArgumentException notExpected) {
            fail();
        }
    }

    static Reader createReader(String code) {
        return new StringReader(code);
    }
}
