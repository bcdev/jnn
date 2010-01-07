package com.bc.jnn.func;

import com.bc.jnn.JnnUnit;
import com.bc.jnn.JnnConnection;

/**
 * Created by IntelliJ IDEA.
 * User: tom
 * Date: 28.01.2004
 * Time: 15:59:31
 * To change this template use Options | File Templates.
 */
public class InputFunctionSum_1 implements IInputFunction {

    /**
     * Evaluates the input function on the unit passed in
     * @param unit
     */
    public final void evaluate(JnnUnit unit) {
        // init
        unit.setInput(0.0);

        double sum = 0.0;
        JnnConnection con;

        final int n = unit.getNumConnections();
        for (int i = 0; i < n; i++) {
            con = unit.getConnectionAt(i);
            sum += con.getWeight() * con.getInputUnit().getOutput();
        }

        sum *= unit.getInputScale();
        sum += unit.getInputBias();

        unit.setInput(sum);
    }
}
