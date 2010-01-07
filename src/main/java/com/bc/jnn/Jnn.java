package com.bc.jnn;

import com.bc.jnn.nnio.NnaNetBuilder;
import com.bc.jnn.nnio.NnaParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * The <code>Jnn</code> class provides a high level access to the
 * JNN API. E.g. it can be used to read neural nets from NNA files using
 * a single method call.
 */
public final class Jnn {
    public static final String VERSION_STRING = "1.5";
    public static boolean optimizing = false;

    /**
     * Gets whether or not the JNN library tries to generate optimized versions of neural nets.
     * @return true, if so
     * @see #setOptimizing(boolean)
     */
    public static boolean isOptimizing() {
        return optimizing;
    }

    /**
     * Sets whether or not the JNN library will try to generate optimized versions of the neural nets
     * created by the {@link #readNna} methods.
     *
     * <p>Currently, the only optimization implemented is the replacement of the evaluate activation function
     * with a one using tabulated, pre-computed values and where actual values are computed by a linear
     * interpolation (see {@link com.bc.jnn.func.Sigma}). The tabulated sigmoid function is up to 4 times faster
     * than the "true" evaluate function which is evaluate(x) = 1.0 / (1.0 + Math.exp(-x)).
     *
     * <p>Note that setting this option on might reduce overall accuracy of the neural net.
     *
     * @param optimizing if true, the JNN library tries to runtime-optimize the neural nets returned by
     * subsequent calls of {@link #readNna}.
     */
    public static void setOptimizing(boolean optimizing) {
        Jnn.optimizing = optimizing;
    }

    /**
     * Reads a neural net from the given NNA (Neural Net ASCII) file.
     *
     * @param file the file
     * @return the neural net
     * @throws IOException  if an I/O exception occurs
     * @throws JnnException if a syntax or sematic error occurs
     */
    public static JnnNet readNna(File file) throws IOException, JnnException {
        final Reader reader = new FileReader(file);
        try {
            return readNna(reader);
        } finally {
            reader.close();
        }
    }

    /**
     * Reads a neural net from the given NNA (Neural Net ASCII) reader.
     *
     * @param reader the reader
     * @return the neural net
     * @throws IOException  if an I/O exception occurs
     * @throws JnnException if a syntax or sematic error occurs
     */
    public static JnnNet readNna(Reader reader) throws IOException, JnnException {
        final NnaParser parser = new NnaParser();
        final NnaNetBuilder builder = new NnaNetBuilder();
        parser.parse(reader, builder);
        final JnnNet net = builder.getCurrentNet();
        final StringBuffer report = new StringBuffer();
        if (!net.init(isOptimizing(), report)) {
            throw new JnnException("Verification of neural network failed: " + report);
        }
        return net;
    }

    private Jnn() {
    }
}
