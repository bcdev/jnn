package com.bc.jnn;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Created by IntelliJ IDEA.
 * User: tom
 * Date: 21.01.2004
 * Time: 15:18:26
 * To change this template use Options | File Templates.
 */
public class JnnLayerTest extends TestCase {

    private JnnLayer _layer;

    public JnnLayerTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(JnnLayerTest.class);
    }

    /**
     * Initializes the test environment
     */
    protected void setUp() {
        _layer = new JnnLayer();
        assertNotNull(_layer);
    }

    /**
     * Tests that all memmbers of the layer are set to their appropriate default values
     */
    public void testDefaultConstruction() {
        assertEquals(1, _layer.getNumUnits());
        assertEquals(JnnConstants.NN_FUNC_SUM_1, _layer.getInputFunction());
        assertEquals(JnnConstants.NN_FUNC_SIGMOID_1, _layer.getActivationFunction());
        assertEquals(JnnConstants.NN_FUNC_IDENTITY, _layer.getOutputFunction());
        assertEquals(0.0, _layer.getActivationThreshold(), 1e-6);
        assertEquals(1.0, _layer.getActivationSlope(), 1e-6);
    }

    /**
     * Checks the setGetInputFunction methods for correct functionality
     */
    public void testSetGetInputFunction() {
        int expFuncIdx_1 = 6;
        int expFuncIdx_2 = 9;

        _layer.setInputFunction(expFuncIdx_1);
        assertEquals(expFuncIdx_1, _layer.getInputFunction());

        _layer.setInputFunction(expFuncIdx_2);
        assertEquals(expFuncIdx_2, _layer.getInputFunction());
    }

    /**
     * Checks the setGetActivationFunction methods for correct functionality
     */
    public void testSetGetActivationFunction() {
        int expFuncIdx_1 = 7;
        int expFuncIdx_2 = 1;

        _layer.setActivationFunction(expFuncIdx_1);
        assertEquals(expFuncIdx_1, _layer.getActivationFunction());

        _layer.setActivationFunction(expFuncIdx_2);
        assertEquals(expFuncIdx_2, _layer.getActivationFunction());
    }

    /**
     * Checks the setGetOutputFunction methods for correct functionality
     */
    public void testSetGetOutputFunction() {
        int expFuncIdx_1 = 6;
        int expFuncIdx_2 = 2;

        _layer.setOutputFunction(expFuncIdx_1);
        assertEquals(expFuncIdx_1, _layer.getOutputFunction());

        _layer.setOutputFunction(expFuncIdx_2);
        assertEquals(expFuncIdx_2, _layer.getOutputFunction());
    }

    /**
     * Checks the setGetOutputFunction methods for correct functionality
     */
    public void testSetGetNumUnits() {
        int expNumUnits_1 = 34;
        int expNumUnits_2 = 9;

        _layer.setNumUnits(expNumUnits_1);
        assertEquals(expNumUnits_1, _layer.getNumUnits());

        _layer.setNumUnits(expNumUnits_2);
        assertEquals(expNumUnits_2, _layer.getNumUnits());

        // must fail
        try {
            _layer.setNumUnits(-5);
            fail();
        } catch (NegativeArraySizeException expected) {
        }
    }

    /**
     * Checks the setGetActivationThreshold methods for correct functionality
     */
    public void testSetGetActivationThreshold() {
        double expThresh_1 = -12.9f;
        double expThresh_2 = 0.03f;

        _layer.setActivationThreshold(expThresh_1);
        assertEquals(expThresh_1, _layer.getActivationThreshold(), 1e-6);

        _layer.setActivationThreshold(expThresh_2);
        assertEquals(expThresh_2, _layer.getActivationThreshold(), 1e-6);
    }

    /**
     * Checks the setGetActivationSlope methods for correct functionality
     */
    public void testSetGetActivationSlope() {
        double expSlope_1 = 3.5;
        double expSlope_2 = -22.9;

        _layer.setActivationSlope(expSlope_1);
        assertEquals(expSlope_1, _layer.getActivationSlope(), 1e-6);

        _layer.setActivationSlope(expSlope_2);
        assertEquals(expSlope_2, _layer.getActivationSlope(), 1e-6);
    }

    /**
     * Tests the setup and verify method for correctness
     */
    public void testVerifyIntegrity() {
        JnnUnit unit = new JnnUnit();

        _layer.setNumUnits(1);
        _layer.setUnitAt(0, unit);
        _layer.setInputFunction(JnnConstants.NN_FUNC_SUM_2);
        _layer.setActivationFunction(JnnConstants.NN_FUNC_SEMILINEAR);
        _layer.setOutputFunction(JnnConstants.NN_FUNC_IDENTITY);

        assertEquals(true, _layer.initFunctions());

        // must fail - zero units
        _layer.setNumUnits(0);
        _layer.setInputFunction(JnnConstants.NN_FUNC_SUM_2);
        _layer.setActivationFunction(JnnConstants.NN_FUNC_SEMILINEAR);
        _layer.setOutputFunction(JnnConstants.NN_FUNC_IDENTITY);

        assertEquals(false, _layer.initFunctions());

        // must fail - wrong input function
        _layer.setNumUnits(2);
        _layer.setInputFunction(176);
        _layer.setActivationFunction(JnnConstants.NN_FUNC_SEMILINEAR);
        _layer.setOutputFunction(JnnConstants.NN_FUNC_IDENTITY);

        assertEquals(false, _layer.initFunctions());

        // must fail - wrong activation function
        _layer.setNumUnits(2);
        _layer.setInputFunction(JnnConstants.NN_FUNC_SUM_1);
        _layer.setActivationFunction(95);
        _layer.setOutputFunction(JnnConstants.NN_FUNC_IDENTITY);

        assertEquals(false, _layer.initFunctions());

        // must fail - wrong output function
        _layer.setNumUnits(2);
        _layer.setInputFunction(JnnConstants.NN_FUNC_SUM_1);
        _layer.setActivationFunction(JnnConstants.NN_FUNC_SEMILINEAR);
        _layer.setOutputFunction(1345);

        assertEquals(false, _layer.initFunctions());
    }

    /**
     * Tests the correct functionality of the unit accessor methods
     */
    public void testAddGetUnit() {
        _layer.setNumUnits(7);

        JnnUnit unit_1 = new JnnUnit();
        JnnUnit unit_2 = new JnnUnit();
        JnnUnit unit_3 = new JnnUnit();

        _layer.setUnitAt(1, unit_1);
        assertSame(unit_1, _layer.getUnitAt(1));

        _layer.setUnitAt(5, unit_2);
        assertSame(unit_2, _layer.getUnitAt(5));

        // failures - out of bound
        try {
            _layer.setUnitAt(-1, unit_3);
            fail();
        } catch (ArrayIndexOutOfBoundsException expected) {
        }

        try {
            _layer.setUnitAt(7, unit_3);
            fail();
        } catch (ArrayIndexOutOfBoundsException expected) {
        }

        // failure - invalid index
        try {
            unit_3 = _layer.getUnitAt(-1);
            fail();
        } catch (ArrayIndexOutOfBoundsException expected) {
        }
    }

    /**
     * Tests that the correct error happens when trying to add a unit
     * and no number of units is set yet
     */
    public void testAddUnitWhenNoUnitsAreSet() {
        JnnUnit unit = new JnnUnit();

        try {
            _layer.setUnitAt(6, unit);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            _layer.setUnitAt(0, unit);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    /**
     * Tests for the correct calculation of the input function
     */
    public void testCalcInputFunction() {
        JnnUnit outUnit = new JnnUnit();
        JnnUnit inUnit = new JnnUnit();
        JnnConnection conn = new JnnConnection();

        inUnit.setOutput(2.0);
        conn.setInputUnit(inUnit);
        conn.setWeight(0.4);

        outUnit.setNumConnections(1);
        outUnit.setConnectionAt(0, conn);

        _layer.setNumUnits(1);
        _layer.setUnitAt(0, outUnit);
        _layer.setInputFunction(JnnConstants.NN_FUNC_SUM_1);
        assertTrue(_layer.initFunctions());

        _layer.calcInputFunction();

        assertEquals(0.8, outUnit.getInput(), 1e-6);
    }

    /**
     * Tests for the correct calculation of the input function
     */
    public void testCalcInputFunctionWithTwoUnits() {
        JnnUnit outUnit_1 = new JnnUnit();
        JnnUnit outUnit_2 = new JnnUnit();
        JnnUnit inUnit = new JnnUnit();
        JnnConnection conn = new JnnConnection();

        inUnit.setOutput(2.0);
        conn.setInputUnit(inUnit);
        conn.setWeight(0.4);

        outUnit_1.setNumConnections(1);
        outUnit_1.setConnectionAt(0, conn);
        outUnit_2.setNumConnections(1);
        outUnit_2.setConnectionAt(0, conn);

        _layer.setNumUnits(2);
        _layer.setUnitAt(0, outUnit_1);
        _layer.setUnitAt(1, outUnit_2);
        _layer.setInputFunction(JnnConstants.NN_FUNC_SUM_2);
        assertTrue(_layer.initFunctions());

        _layer.calcInputFunction();

        assertEquals(0.4, outUnit_1.getInput(), 1e-6);
        assertEquals(0.4, outUnit_2.getInput(), 1e-6);
    }

    /**
     * Tests the setInputData method for correctness
     */
    public void testSetInputData() {
        double[] input = new double[]{2.7};
        JnnUnit outUnit = new JnnUnit();

        _layer.setNumUnits(1);
        _layer.setUnitAt(0, outUnit);
        _layer.initFunctions();

        _layer.setInputData(input);

        assertEquals(input[0], outUnit.getInput(), 1e-6);

        // now check that the input is added to the current input
        outUnit.setInput(1.9);
        _layer.setInputData(input);

        assertEquals(input[0] + 1.9, outUnit.getInput(), 1e-6);

        // force errors due to arrays of illegal size
        double[] tooSmallArray = new double[0];
        double[] tooBigArray = new double[2];

        try {
            _layer.setInputData(tooSmallArray);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
        }

        try {
            _layer.setInputData(tooBigArray);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Tests that the output is retrieved correctly from the units
     */
    public void testGetOutputData() {
        double[] output = new double[]{2.7};
        JnnUnit outUnit = new JnnUnit();
        double dOut = 0.97;

        outUnit.setOutput(dOut);
        _layer.setNumUnits(1);
        _layer.setUnitAt(0, outUnit);
        _layer.initFunctions();

        output = _layer.getOutputData(output);
        assertEquals(dOut, output[0], 1e-6);

        // force errors due to arrays of illegal size
        double[] tooSmallArray = new double[0];
        double[] tooBigArray = new double[2];

        try {
            _layer.getOutputData(tooSmallArray);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
        }

        try {
            _layer.getOutputData(tooBigArray);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Tests that the activation function is performed correctly
     */
    public void testCalcActivationFunction() {
        JnnUnit outUnit_1 = new JnnUnit();
        JnnUnit outUnit_2 = new JnnUnit();

        outUnit_1.setInput(1.4);
        outUnit_2.setInput(0.4);
        _layer.setNumUnits(2);
        _layer.setUnitAt(0, outUnit_1);
        _layer.setUnitAt(1, outUnit_2);

        // identity
        _layer.setActivationFunction(JnnConstants.NN_FUNC_IDENTITY);
        _layer.initFunctions();
        _layer.calcActivationFunction();
        assertEquals(1.4, outUnit_1.getActivation(), 1e-6);
        assertEquals(0.4, outUnit_2.getActivation(), 1e-6);

        // linear y = slope * (x - thresh)
        _layer.setActivationSlope(0.5);
        _layer.setActivationThreshold(0.1);
        _layer.setActivationFunction(JnnConstants.NN_FUNC_LINEAR);
        _layer.initFunctions();
        _layer.calcActivationFunction();
        assertEquals(0.65, outUnit_1.getActivation(), 1e-6);
        assertEquals(0.15, outUnit_2.getActivation(), 1e-6);

        // linear y = slope * (x - thresh), clipped
        _layer.setActivationSlope(0.5);
        _layer.setActivationThreshold(-0.7);
        _layer.setActivationFunction(JnnConstants.NN_FUNC_SEMILINEAR);
        _layer.initFunctions();
        _layer.calcActivationFunction();
        assertEquals(1.0, outUnit_1.getActivation(), 1e-6);
        assertEquals(0.55, outUnit_2.getActivation(), 1e-6);

        // sigmoid_1 y = 1.0 / (1.0 + exp(thresh - slope * x))
        _layer.setActivationSlope(0.5);
        _layer.setActivationThreshold(0.2);
        _layer.setActivationFunction(JnnConstants.NN_FUNC_SIGMOID_1);
        _layer.initFunctions();
        _layer.calcActivationFunction();
        assertEquals(0.64565630, outUnit_1.getActivation(), 1e-6);
        assertEquals(0.52497919, outUnit_2.getActivation(), 1e-6);

        // threshold
        _layer.setActivationThreshold(0.9);
        _layer.setActivationFunction(JnnConstants.NN_FUNC_THRESHOLD);
        _layer.initFunctions();
        _layer.calcActivationFunction();
        assertEquals(1.0, outUnit_1.getActivation(), 1e-6);
        assertEquals(0.0, outUnit_2.getActivation(), 1e-6);
    }

    /**
     * Tests that the output is correctly calculated
     */
    public void testCalcOutputFunction() {
        JnnUnit outUnit_1 = new JnnUnit();
        JnnUnit outUnit_2 = new JnnUnit();

        outUnit_1.setActivation(0.7);
        outUnit_1.setOutputBias(0.1);
        outUnit_1.setOutputScale(0.9);
        outUnit_2.setActivation(0.4);
        outUnit_2.setOutputScale(1.0);
        outUnit_2.setOutputBias(0.0);
        _layer.setNumUnits(2);
        _layer.setUnitAt(0, outUnit_1);
        _layer.setUnitAt(1, outUnit_2);

        // exponential
        //  y = exp(scale * x + bias)
        _layer.setOutputFunction(JnnConstants.NN_FUNC_EXPONENTIAL);
        _layer.initFunctions();
        _layer.calcOutputFunction();

        assertEquals(2.075081, outUnit_1.getOutput(), 1e-6);
        assertEquals(1.491825, outUnit_2.getOutput(), 1e-6);

        // identity
        //  y = x
        _layer.setOutputFunction(JnnConstants.NN_FUNC_IDENTITY);
        _layer.initFunctions();
        _layer.calcOutputFunction();

        assertEquals(0.7, outUnit_1.getOutput(), 1e-6);
        assertEquals(0.4, outUnit_2.getOutput(), 1e-6);

        // linear
        // y = scale * x + bias
        _layer.setOutputFunction(JnnConstants.NN_FUNC_LINEAR);
        _layer.initFunctions();
        _layer.calcOutputFunction();

        assertEquals(0.73, outUnit_1.getOutput(), 1e-6);
        assertEquals(0.4, outUnit_2.getOutput(), 1e-6);

        // logarithmic
        // y = log(scale * x + bias)
        _layer.setOutputFunction(JnnConstants.NN_FUNC_LOGARITHMIC);
        _layer.initFunctions();
        _layer.calcOutputFunction();

        assertEquals(-0.314711, outUnit_1.getOutput(), 1e-6);
        assertEquals(-0.916291, outUnit_2.getOutput(), 1e-6);
    }
}
