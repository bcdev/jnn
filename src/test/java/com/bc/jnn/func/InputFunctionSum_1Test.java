package com.bc.jnn.func;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import com.bc.jnn.JnnUnit;
import com.bc.jnn.JnnConnection;

/**
 * Created by IntelliJ IDEA.
 * User: tom
 * Date: 28.01.2004
 * Time: 16:17:20
 * To change this template use Options | File Templates.
 */
public class InputFunctionSum_1Test extends TestCase {

    private InputFunctionSum_1 _func;

    public InputFunctionSum_1Test(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(InputFunctionSum_1Test.class);
    }

    /**
     * Initializes the test environment.
     */
    protected void setUp() {
        _func = new InputFunctionSum_1();
        assertNotNull(_func);
    }

    /**
     * First test setup
     */
    public void testWithOneConnection() {
        JnnUnit outUnit = new JnnUnit();
        JnnUnit inUnit = new JnnUnit();
        JnnConnection conn = new JnnConnection();

        inUnit.setOutput(2.0);
        conn.setInputUnit(inUnit);
        conn.setWeight(0.4);

        outUnit.setNumConnections(1);
        outUnit.setConnectionAt(0, conn);

        _func.evaluate(outUnit);

        assertEquals(0.8, outUnit.getInput(), 1e-6);
    }

    /**
     * First test setup
     */
    public void testWithOneConnectionAndScale() {
        JnnUnit outUnit = new JnnUnit();
        JnnUnit inUnit = new JnnUnit();
        JnnConnection conn = new JnnConnection();

        inUnit.setOutput(2.0);
        conn.setInputUnit(inUnit);
        conn.setWeight(0.4);

        outUnit.setNumConnections(1);
        outUnit.setConnectionAt(0, conn);
        outUnit.setInputScale(2.0);

        _func.evaluate(outUnit);

        assertEquals(1.6, outUnit.getInput(), 1e-6);
    }

    /**
     * First test setup
     */
    public void testWithOneConnectionAndBias() {
        JnnUnit outUnit = new JnnUnit();
        JnnUnit inUnit = new JnnUnit();
        JnnConnection conn = new JnnConnection();

        inUnit.setOutput(2.0);
        conn.setInputUnit(inUnit);
        conn.setWeight(0.4);

        outUnit.setNumConnections(1);
        outUnit.setConnectionAt(0, conn);
        outUnit.setInputBias(0.1);

        _func.evaluate(outUnit);

        assertEquals(0.9, outUnit.getInput(), 1e-6);
    }

    /**
     * First test setup
     */
    public void testWithOneConnectionAndBiasAndScale() {
        JnnUnit outUnit = new JnnUnit();
        JnnUnit inUnit = new JnnUnit();
        JnnConnection conn = new JnnConnection();

        inUnit.setOutput(2.0);
        conn.setInputUnit(inUnit);
        conn.setWeight(0.4);

        outUnit.setNumConnections(1);
        outUnit.setConnectionAt(0, conn);
        outUnit.setInputBias(0.1);
        outUnit.setInputScale(2.0);

        _func.evaluate(outUnit);

        assertEquals(1.7, outUnit.getInput(), 1e-6);
    }

    /**
     * second test setup
     */
    public void testWithThreeConnections() {
        JnnUnit outUnit = new JnnUnit();
        JnnUnit inUnit_1 = new JnnUnit();
        JnnUnit inUnit_2 = new JnnUnit();
        JnnUnit inUnit_3 = new JnnUnit();
        JnnConnection conn_1 = new JnnConnection();
        JnnConnection conn_2 = new JnnConnection();
        JnnConnection conn_3 = new JnnConnection();

        inUnit_1.setOutput(2.0);
        conn_1.setInputUnit(inUnit_1);
        conn_1.setWeight(0.4);

        inUnit_2.setOutput(3.0);
        conn_2.setInputUnit(inUnit_2);
        conn_2.setWeight(0.5);

        inUnit_3.setOutput(4.0);
        conn_3.setInputUnit(inUnit_3);
        conn_3.setWeight(0.6);

        outUnit.setNumConnections(3);
        outUnit.setConnectionAt(0, conn_1);
        outUnit.setConnectionAt(1, conn_2);
        outUnit.setConnectionAt(2, conn_3);

        _func.evaluate(outUnit);

        assertEquals(4.7, outUnit.getInput(), 1e-6);
    }
}
