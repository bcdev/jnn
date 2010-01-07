package com.bc.jnn.func;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import com.bc.jnn.JnnConstants;

/**
 * Created by IntelliJ IDEA.
 * User: tom
 * Date: 28.01.2004
 * Time: 15:11:16
 * To change this template use Options | File Templates.
 */
public class JnnFunctionFactoryTests extends TestCase {

    public JnnFunctionFactoryTests(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(JnnFunctionFactoryTests.class);
    }

    /**
     * Tests that the input functions are returned correctly
     */
    public void testGetInputFunctions() {
        IInputFunction inpFunc;

        try {
            inpFunc = JnnFunctionFactory.getInputFunction(JnnConstants.NN_FUNC_ZERO, false);
            assertNotNull(inpFunc);
            assertTrue(inpFunc instanceof InputFunctionZero);

            inpFunc = JnnFunctionFactory.getInputFunction(JnnConstants.NN_FUNC_SUM_1, false);
            assertNotNull(inpFunc);
            assertTrue(inpFunc instanceof InputFunctionSum_1);

            inpFunc = JnnFunctionFactory.getInputFunction(JnnConstants.NN_FUNC_SUM_2, false);
            assertNotNull(inpFunc);
            assertTrue(inpFunc instanceof InputFunctionSum_2);
        } catch (Exception e) {
            fail("NO exception expected");
        }

        // try some that must fail
        try {
            inpFunc = JnnFunctionFactory.getInputFunction(JnnConstants.NN_FUNC_EXPONENTIAL, false);
            assertNotNull(inpFunc);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
        }

        try {
            inpFunc = JnnFunctionFactory.getInputFunction(345389, false);
            assertNotNull(inpFunc);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Tests that the activation functions are returned correctly
     */
    public void testGetActivationFunction() {
        IActivationFunction actFunc;

        try {
            actFunc = JnnFunctionFactory.getActivationFunction(JnnConstants.NN_FUNC_IDENTITY, false);
            assertNotNull(actFunc);
            assertTrue(actFunc instanceof ActFunctionIdentity);

            actFunc = JnnFunctionFactory.getActivationFunction(JnnConstants.NN_FUNC_THRESHOLD, false);
            assertNotNull(actFunc);
            assertTrue(actFunc instanceof ActFunctionThreshold);

            actFunc = JnnFunctionFactory.getActivationFunction(JnnConstants.NN_FUNC_LINEAR, false);
            assertNotNull(actFunc);
            assertTrue(actFunc instanceof ActFunctionLinear);

            actFunc = JnnFunctionFactory.getActivationFunction(JnnConstants.NN_FUNC_SEMILINEAR, false);
            assertNotNull(actFunc);
            assertTrue(actFunc instanceof ActFunctionSemiLinear);

            actFunc = JnnFunctionFactory.getActivationFunction(JnnConstants.NN_FUNC_SIGMOID_1, false);
            assertNotNull(actFunc);
            assertTrue(actFunc instanceof ActFunctionSigmoid_1);

            actFunc = JnnFunctionFactory.getActivationFunction(JnnConstants.NN_FUNC_SIGMOID_1, true);
            assertNotNull(actFunc);
            assertTrue(actFunc instanceof ActFunctionSigmoidOpt);

            actFunc = JnnFunctionFactory.getActivationFunction(JnnConstants.NN_FUNC_TANG_SIGMOID, false);
            assertNotNull(actFunc);
            assertTrue(actFunc instanceof ActFunctionTangentSigmoid);
        } catch (Exception e) {
            fail("NO exception expected");
        }

        // check unsupported opcodes
        try {
            actFunc = JnnFunctionFactory.getActivationFunction(JnnConstants.NN_FUNC_SIGMOID_2, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
        }

        try {
            actFunc = JnnFunctionFactory.getActivationFunction(JnnConstants.NN_FUNC_RBF_1, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
        }

        try {
            actFunc = JnnFunctionFactory.getActivationFunction(JnnConstants.NN_FUNC_RBF_2, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
        }

        // check illegal opcodes
        try {
            actFunc = JnnFunctionFactory.getActivationFunction(-9, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
        }

        try {
            actFunc = JnnFunctionFactory.getActivationFunction(172, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Tests that the output functions are returned correctly
     */
    public void testGetOutputFunction() {
        IOutputFunction func;

        try {
            func = JnnFunctionFactory.getOutputFunction(JnnConstants.NN_FUNC_IDENTITY, false);
            assertNotNull(func);
            assertTrue(func instanceof OutputFunctionIdentity);

            func = JnnFunctionFactory.getOutputFunction(JnnConstants.NN_FUNC_LINEAR, false);
            assertNotNull(func);
            assertTrue(func instanceof OutputFunctionLinear);

            func = JnnFunctionFactory.getOutputFunction(JnnConstants.NN_FUNC_EXPONENTIAL, false);
            assertNotNull(func);
            assertTrue(func instanceof OutputFunctionExponential);

            func = JnnFunctionFactory.getOutputFunction(JnnConstants.NN_FUNC_LOGARITHMIC, false);
            assertNotNull(func);
            assertTrue(func instanceof OutputFunctionLogarithmic);
        } catch (Exception e) {
            fail("NO exception expected");
        }

        // check unsupported opcodes
        try {
            func = JnnFunctionFactory.getOutputFunction(JnnConstants.NN_FUNC_SIGMOID_2, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
        }

        try {
            func = JnnFunctionFactory.getOutputFunction(-4, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
        }
    }
}

