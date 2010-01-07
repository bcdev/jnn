package com.bc.jnn.func;

import com.bc.jnn.JnnUnit;
import com.bc.jnn.JnnConnection;

/**
 * Created by IntelliJ IDEA.
 * User: tom
 * Date: 28.01.2004
 * Time: 16:00:16
 * To change this template use Options | File Templates.
 */
public class InputFunctionSum_2 implements IInputFunction {

    /**
     * Evaluates the input function on the unit passed in
     * @param unit
     */
    public final void evaluate(JnnUnit unit) {
        unit.setInput(0.0);

        double sum = 0.0;
        double weightSum = 0.0;

        JnnConnection con;

        final int n = unit.getNumConnections();
        for (int i = 0; i < n; i++) {
            con = unit.getConnectionAt(i);
            sum += con.getInputUnit().getOutput();
            weightSum += con.getWeight() * con.getInputUnit().getOutput();
        }

        weightSum /= sum;
        weightSum *= unit.getInputScale();
        weightSum += unit.getInputBias();

        unit.setInput(weightSum);
    }
}
