package com.bc.jnn;

import com.bc.jnn.func.IInputFunction;
import com.bc.jnn.func.JnnFunctionFactory;
import com.bc.jnn.func.IActivationFunction;
import com.bc.jnn.func.IOutputFunction;

/**
 * Created by IntelliJ IDEA.
 * User: tom
 * Date: 21.01.2004
 * Time: 15:20:38
 * To change this template use Options | File Templates.
 */
public final class JnnLayer {

    private int _numUnits;
    private int _inpFuncID;
    private int _actFuncID;
    private int _outFuncID;
    private double[] _actParams;
    private JnnUnit[] _units;
    private IInputFunction _inpFunction;
    private IActivationFunction _actFunction;
    private IOutputFunction _outFunction;

    /**
     * Constructs the object with default parameters.
     */
    public JnnLayer() {
        _numUnits = 1;
        _inpFuncID = JnnConstants.NN_FUNC_SUM_1;
        _actFuncID = JnnConstants.NN_FUNC_SIGMOID_1;
        _outFuncID = JnnConstants.NN_FUNC_IDENTITY;
        _actParams = new double[2];
        _actParams[0] = 0.0;
        _actParams[1] = 1.0;
    }

    /**
     * Retrieves the number of units in this layer.
     * @return
     */
    public int getNumUnits() {
        return _numUnits;
    }

    /**
     * Sets the lnumber of units for this layer
     * @param numUnits
     */
    public void setNumUnits(int numUnits) {
        _units = new JnnUnit[numUnits];
        _numUnits = numUnits;
    }

    /**
     * Set a unit to the layer at the given (0-based) index
     * @param index
     * @param unit
     */
    public void setUnitAt(int index, JnnUnit unit) {
        _units[index] = unit;
    }

    /**
     * Retrieves the unit at the given (0-based) index
     * @param index
     * @return
     */
    public JnnUnit getUnitAt(int index) {
        return _units[index];
    }

    /**
     * Retrieves the input function index (see JnnConstants).
     * @return
     */
    public int getInputFunction() {
        return _inpFuncID;
    }

    /**
     * Sets the input function index (see JnnConstants).
     * @param inpFunc
     */
    public void setInputFunction(int inpFunc) {
        _inpFuncID = inpFunc;
    }

    /**
     * Retrieves the activation function index (see JnnConstants).
     * @return
     */
    public int getActivationFunction() {
        return _actFuncID;
    }

    /**
     * Sets the activation function index (see JnnConstants).
     * @param actFunc
     */
    public void setActivationFunction(int actFunc) {
        _actFuncID = actFunc;
    }

    /**
     * Retrieves the output function index (see JnnConstants).
     * @return
     */
    public int getOutputFunction() {
        return _outFuncID;
    }

    /**
     * Sets the output function index (see JnnConstants).
     * @param outFunc
     */
    public void setOutputFunction(int outFunc) {
        _outFuncID = outFunc;
    }

    /**
     * Retrieves the threshold for the activation function
     * @return
     */
    public double getActivationThreshold() {
        return _actParams[0];
    }

    /**
     * Sets the activation threshold for the activation function
     * @param thresh
     */
    public void setActivationThreshold(double thresh) {
        _actParams[0] = thresh;
    }

    /**
     * Retrieves the slope for the activation function
     * @return
     */
    public double getActivationSlope() {
        return _actParams[1];
    }

    /**
     * Sets the activation slope for the activation function
     * @param slope
     */
    public void setActivationSlope(double slope) {
        _actParams[1] = slope;
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
        if (_numUnits < 1) {
            msg.append("Invalid number of units: '" + _numUnits + "' (should be > 0)");
            return false;
        }

        if (_units == null) {
            msg.append("No units defined");
            return false;
        }

        switch (_inpFuncID) {
        case JnnConstants.NN_FUNC_ZERO:
        case JnnConstants.NN_FUNC_SUM_1:
        case JnnConstants.NN_FUNC_SUM_2:
            _inpFunction = JnnFunctionFactory.getInputFunction(_inpFuncID, optimizing);
            break;
        default:
            msg.append("Invalid input function ID '" + _inpFuncID + "'");
            return false;
        }

        switch (_actFuncID) {
        case JnnConstants.NN_FUNC_IDENTITY:
        case JnnConstants.NN_FUNC_THRESHOLD:
        case JnnConstants.NN_FUNC_SEMILINEAR:
        case JnnConstants.NN_FUNC_LINEAR:
        case JnnConstants.NN_FUNC_SIGMOID_1:
        case JnnConstants.NN_FUNC_SIGMOID_2:
        case JnnConstants.NN_FUNC_TANG_SIGMOID:
        case JnnConstants.NN_FUNC_RBF_1:
        case JnnConstants.NN_FUNC_RBF_2:
            _actFunction = JnnFunctionFactory.getActivationFunction(_actFuncID, optimizing);
            _actFunction.setParameter(_actParams);
            break;
        default:
            msg.append("Invalid activation function ID '" + _actFuncID + "'");
            return false;
        }

        switch (_outFuncID) {
        case JnnConstants.NN_FUNC_IDENTITY:
        case JnnConstants.NN_FUNC_LINEAR:
        case JnnConstants.NN_FUNC_EXPONENTIAL:
        case JnnConstants.NN_FUNC_LOGARITHMIC:
            _outFunction = JnnFunctionFactory.getOutputFunction(_outFuncID, optimizing);
            break;
        default:
            msg.append("Invalid output function ID '" + _outFuncID + "'");
            return false;
        }

        for (int i = 0; i < _units.length; i++) {
            if (_units[i] != null) {
                if (!_units[i].verifyIntegrity(msg)) {
                    return false;
                }
            } else {
                msg.append("No unit at 0-based index: '" + i + "'");
                return false;
            }
        }
        return true;
    }

    /**
     * Calculated the input function on the units connected
     */
    public void calcInputFunction() {
        for (int i = 0; i < _numUnits; i++) {
            _inpFunction.evaluate(_units[i]);
        }
    }

    /**
     * Calculates the activation function on the units attached
     */
    public void calcActivationFunction() {
        for (int i = 0; i < _numUnits; i++) {
            _actFunction.evaluate(_units[i]);
        }
    }

    /**
     * Calculated the output function on the units connected
     */
    public void calcOutputFunction() {
        for (int i = 0; i < _numUnits; i++) {
            _outFunction.evaluate(_units[i]);
        }
    }

    /**
     * Sets the input for the units of this layer. The size of
     * the vector must correspond to the number of units in the layer
     * @param input
     */
    public void setInputData(double[] input) {
        if (input.length != _numUnits) {
            throw new IllegalArgumentException("Invalid number of input data '" + input.length
                                               + "' expected '" + _numUnits + "'");
        }

        double temp;
        for (int i = 0; i < _numUnits; i++) {
            temp = _units[i].getInput();
            temp += input[i];
            _units[i].setInput(temp);
        }
    }

    /**
     * Retrieves the output of the units of this layer. The size of
     * the vector must correspond to the number of units in the layer
     * @param out
     */
    public double[] getOutputData(double[] out) {
        if (out.length != _numUnits) {
            throw new IllegalArgumentException("Invalid number of output data '" + out.length
                                               + "' expected '" + _numUnits + "'");
        }
        for (int i = 0; i < _numUnits; i++) {
            out[i] = _units[i].getOutput();
        }
        return out;
    }
}
