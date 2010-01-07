package com.bc.jnn.func;

import com.bc.jnn.JnnUnit;

/**
 * Created by IntelliJ IDEA.
 * User: tom
 * Date: 28.01.2004
 * Time: 15:41:59
 * To change this template use Options | File Templates.
 */
public class InputFunctionZero implements IInputFunction {

    /**
     * Evaluates the input function on the unit passed in
     * @param unit
     */
    public final void evaluate(JnnUnit unit) {
        unit.setInput(0.0);
    }
}
