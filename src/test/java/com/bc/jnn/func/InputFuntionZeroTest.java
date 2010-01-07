package com.bc.jnn.func;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import com.bc.jnn.JnnUnit;

/**
 * Created by IntelliJ IDEA.
 * User: tom
 * Date: 28.01.2004
 * Time: 15:39:57
 * To change this template use Options | File Templates.
 */
public class InputFuntionZeroTest extends TestCase {

    public InputFuntionZeroTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(InputFuntionZeroTest.class);
    }

    /**
     * Tests the function object
     */
    public void testIt() {
        InputFunctionZero iFunc = new InputFunctionZero();

        assertTrue(iFunc instanceof IInputFunction);

        // must set the unit input to zero
        JnnUnit unit_1 = new JnnUnit();
        JnnUnit unit_2 = new JnnUnit();

        unit_1.setInput(0.976);
        iFunc.evaluate(unit_1);
        assertEquals(0.0, unit_1.getInput(), 1e-6);

        unit_2.setInput(1239.9);
        iFunc.evaluate(unit_2);
        assertEquals(0.0, unit_2.getInput(), 1e-6);
    }
}
