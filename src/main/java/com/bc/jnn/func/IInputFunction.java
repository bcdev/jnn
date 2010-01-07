package com.bc.jnn.func;

import com.bc.jnn.JnnUnit;

/**
 * Created by IntelliJ IDEA.
 * User: tom
 * Date: 28.01.2004
 * Time: 15:22:50
 * To change this template use Options | File Templates.
 */
public interface IInputFunction {

    /**
     * Evaluates the input function on the unit passed in
     * @param unit
     */
    void evaluate(JnnUnit unit);
}
