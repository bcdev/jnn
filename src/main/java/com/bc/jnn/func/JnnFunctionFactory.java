package com.bc.jnn.func;

import com.bc.jnn.JnnConstants;

/**
 * The factory for all supported input-, activation- and output-functions.  
 */
public class JnnFunctionFactory {

    /**
     * Retrieves the input function object for the identifier passed in
     * @param inpFuncId
     * @return
     */
    public static IInputFunction getInputFunction(int inpFuncId, boolean optimize) {
        switch (inpFuncId) {
        case JnnConstants.NN_FUNC_ZERO:
            return new InputFunctionZero();

        case JnnConstants.NN_FUNC_SUM_1:
            return new InputFunctionSum_1();

        case JnnConstants.NN_FUNC_SUM_2:
            return new InputFunctionSum_2();

        default:
            throw new IllegalArgumentException("illegal input function index '" + inpFuncId + "'");
        }
    }

    /**
     * Retrieves the activation function object for the identifier passed in
     * @param actFuncId
     * @return
     */
    public static IActivationFunction getActivationFunction(int actFuncId, boolean optimize) {
        switch (actFuncId) {
        case JnnConstants.NN_FUNC_IDENTITY:
            return new ActFunctionIdentity();

        case JnnConstants.NN_FUNC_THRESHOLD:
            return new ActFunctionThreshold();

        case JnnConstants.NN_FUNC_LINEAR:
            return new ActFunctionLinear();

        case JnnConstants.NN_FUNC_SEMILINEAR:
            return new ActFunctionSemiLinear();

        case JnnConstants.NN_FUNC_SIGMOID_1:
            if (optimize)
                return new ActFunctionSigmoidOpt();
            else
                return new ActFunctionSigmoid_1();

        case JnnConstants.NN_FUNC_SIGMOID_2:
            throw new IllegalArgumentException("unsupported activation function: Sigmoid_2");

        case JnnConstants.NN_FUNC_TANG_SIGMOID:
            return new ActFunctionTangentSigmoid();

        case JnnConstants.NN_FUNC_RBF_1:
            throw new IllegalArgumentException("unsupported activation function: Rbf_1");

        case JnnConstants.NN_FUNC_RBF_2:
            throw new IllegalArgumentException("unsupported activation function: Rbf_2");

        default:
            throw new IllegalArgumentException("illegal activation function index '" + actFuncId + "'");
        }
    }

    /**
     * Retrieves the output function object for the identifier passed in
     * @param outFuncId
     * @return
     */
    public static IOutputFunction getOutputFunction(int outFuncId, boolean optimize) {
        switch (outFuncId) {
        case JnnConstants.NN_FUNC_IDENTITY:
            return new OutputFunctionIdentity();

        case JnnConstants.NN_FUNC_LINEAR:
            return new OutputFunctionLinear();

        case JnnConstants.NN_FUNC_EXPONENTIAL:
            return new OutputFunctionExponential();

        case JnnConstants.NN_FUNC_LOGARITHMIC:
            return new OutputFunctionLogarithmic();

        default:
            throw new IllegalArgumentException("illegal output function index '" + outFuncId + "'");
        }
    }
}
