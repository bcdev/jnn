package com.bc.jnn;

import com.bc.jnn.func.IInputFunction;
import com.bc.jnn.func.JnnFunctionFactory;
import com.bc.jnn.func.IActivationFunction;
import com.bc.jnn.func.IOutputFunction;

import java.text.MessageFormat;

/**
 * Created by IntelliJ IDEA.
 * User: tom
 * Date: 21.01.2004
 * Time: 15:20:38
 * To change this template use Options | File Templates.
 */
public final class JnnLayer implements Cloneable {

    private int numUnits;
    private int inpFuncID;
    private int actFuncID;
    private int outFuncID;
    private double[] actParams;
    private JnnUnit[] units;
    private IInputFunction inpFunction;
    private IActivationFunction actFunction;
    private IOutputFunction outFunction;

    /**
     * Constructs the object with default parameters.
     */
    public JnnLayer() {
        numUnits = 1;
        inpFuncID = JnnConstants.NN_FUNC_SUM_1;
        actFuncID = JnnConstants.NN_FUNC_SIGMOID_1;
        outFuncID = JnnConstants.NN_FUNC_IDENTITY;
        actParams = new double[2];
        actParams[0] = 0.0;
        actParams[1] = 1.0;
    }

    /**
     * Retrieves the number of units in this layer.
     * @return
     */
    public int getNumUnits() {
        return numUnits;
    }

    /**
     * Sets the lnumber of units for this layer
     * @param numUnits
     */
    public void setNumUnits(int numUnits) {
        units = new JnnUnit[numUnits];
        this.numUnits = numUnits;
    }

    /**
     * Set a unit to the layer at the given (0-based) index
     * @param index
     * @param unit
     */
    public void setUnitAt(int index, JnnUnit unit) {
        units[index] = unit;
    }

    /**
     * Retrieves the unit at the given (0-based) index
     * @param index
     * @return
     */
    public JnnUnit getUnitAt(int index) {
        return units[index];
    }

    /**
     * Retrieves the input function index (see JnnConstants).
     * @return
     */
    public int getInputFunction() {
        return inpFuncID;
    }

    /**
     * Sets the input function index (see JnnConstants).
     * @param inpFunc
     */
    public void setInputFunction(int inpFunc) {
        inpFuncID = inpFunc;
    }

    /**
     * Retrieves the activation function index (see JnnConstants).
     * @return
     */
    public int getActivationFunction() {
        return actFuncID;
    }

    /**
     * Sets the activation function index (see JnnConstants).
     * @param actFunc
     */
    public void setActivationFunction(int actFunc) {
        actFuncID = actFunc;
    }

    /**
     * Retrieves the output function index (see JnnConstants).
     * @return
     */
    public int getOutputFunction() {
        return outFuncID;
    }

    /**
     * Sets the output function index (see JnnConstants).
     * @param outFunc
     */
    public void setOutputFunction(int outFunc) {
        outFuncID = outFunc;
    }

    /**
     * Retrieves the threshold for the activation function
     * @return
     */
    public double getActivationThreshold() {
        return actParams[0];
    }

    /**
     * Sets the activation threshold for the activation function
     * @param thresh
     */
    public void setActivationThreshold(double thresh) {
        actParams[0] = thresh;
    }

    /**
     * Retrieves the slope for the activation function
     * @return
     */
    public double getActivationSlope() {
        return actParams[1];
    }

    /**
     * Sets the activation slope for the activation function
     * @param slope
     */
    public void setActivationSlope(double slope) {
        actParams[1] = slope;
    }

    /**
     * Verifies the integrity of the layer and sets up internal structures.
     * @return whether the verification was successful or not
     * @deprecated use {@link #initFunctions(boolean, StringBuffer)} instead
     */
    public boolean initFunctions() {
        return initFunctions(false, new StringBuffer());
    }

    /**
     * Initializes the input, activation and output functions and checks the integrity of the layer.
     * @param optimizing true, if initialization shall use optimization techiques
     * @return true, if the layers's integrity is guranteed
     */
    public boolean initFunctions(boolean optimizing, StringBuffer msg) {
        if (numUnits < 1) {
            msg.append("Invalid number of units: '" + numUnits + "' (should be > 0)");
            return false;
        }

        if (units == null) {
            msg.append("No units defined");
            return false;
        }

        switch (inpFuncID) {
        case JnnConstants.NN_FUNC_ZERO:
        case JnnConstants.NN_FUNC_SUM_1:
        case JnnConstants.NN_FUNC_SUM_2:
            inpFunction = JnnFunctionFactory.getInputFunction(inpFuncID, optimizing);
            break;
        default:
            msg.append("Invalid input function ID '" + inpFuncID + "'");
            return false;
        }

        switch (actFuncID) {
        case JnnConstants.NN_FUNC_IDENTITY:
        case JnnConstants.NN_FUNC_THRESHOLD:
        case JnnConstants.NN_FUNC_SEMILINEAR:
        case JnnConstants.NN_FUNC_LINEAR:
        case JnnConstants.NN_FUNC_SIGMOID_1:
        case JnnConstants.NN_FUNC_SIGMOID_2:
        case JnnConstants.NN_FUNC_TANG_SIGMOID:
        case JnnConstants.NN_FUNC_RBF_1:
        case JnnConstants.NN_FUNC_RBF_2:
            actFunction = JnnFunctionFactory.getActivationFunction(actFuncID, optimizing);
            actFunction.setParameter(actParams);
            break;
        default:
            msg.append("Invalid activation function ID '" + actFuncID + "'");
            return false;
        }

        switch (outFuncID) {
        case JnnConstants.NN_FUNC_IDENTITY:
        case JnnConstants.NN_FUNC_LINEAR:
        case JnnConstants.NN_FUNC_EXPONENTIAL:
        case JnnConstants.NN_FUNC_LOGARITHMIC:
            outFunction = JnnFunctionFactory.getOutputFunction(outFuncID, optimizing);
            break;
        default:
            msg.append(MessageFormat.format("Invalid output function ID ''{0}''", outFuncID));
            return false;
        }

        for (int i = 0; i < units.length; i++) {
            if (units[i] != null) {
                if (!units[i].verifyIntegrity(msg)) {
                    return false;
                }
            } else {
                msg.append(MessageFormat.format("No unit at 0-based index: ''{0}''", i));
                return false;
            }
        }
        return true;
    }

    /**
     * Calculated the input function on the units connected
     */
    public void calcInputFunction() {
        for (int i = 0; i < numUnits; i++) {
            inpFunction.evaluate(units[i]);
        }
    }

    /**
     * Calculates the activation function on the units attached
     */
    public void calcActivationFunction() {
        for (int i = 0; i < numUnits; i++) {
            actFunction.evaluate(units[i]);
        }
    }

    /**
     * Calculated the output function on the units connected
     */
    public void calcOutputFunction() {
        for (int i = 0; i < numUnits; i++) {
            outFunction.evaluate(units[i]);
        }
    }

    /**
     * Sets the input for the units of this layer. The size of
     * the vector must correspond to the number of units in the layer
     * @param input
     */
    public void setInputData(double[] input) {
        if (input.length != numUnits) {
            throw new IllegalArgumentException(MessageFormat.format("Invalid number of input data ''{0}'' expected ''{1}''", input.length, numUnits));
        }

        double temp;
        for (int i = 0; i < numUnits; i++) {
            temp = units[i].getInput();
            temp += input[i];
            units[i].setInput(temp);
        }
    }

    /**
     * Retrieves the output of the units of this layer. The size of
     * the vector must correspond to the number of units in the layer
     * @param out
     */
    public double[] getOutputData(double[] out) {
        if (out.length != numUnits) {
            throw new IllegalArgumentException(MessageFormat.format("Invalid number of output data ''{0}'' expected ''{1}''", out.length, numUnits));
        }
        for (int i = 0; i < numUnits; i++) {
            out[i] = units[i].getOutput();
        }
        return out;
    }

    @Override
    public JnnLayer clone() {
        try {
            JnnLayer clonedLayer = (JnnLayer) super.clone();
            JnnUnit[] clonedUnits = units.clone();
            for (int i = 0; i < clonedUnits.length; i++) {
                clonedUnits[i] = clonedUnits[i].clone();
            }
            clonedLayer.units = clonedUnits;
            clonedLayer.actParams = actParams.clone();
            return clonedLayer;
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }
}
