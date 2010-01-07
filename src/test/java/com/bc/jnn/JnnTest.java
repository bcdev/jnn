package com.bc.jnn;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.io.IOException;

public class JnnTest extends TestCase {

    private static final File simple_1 = ResourceProvider.getResourceAsFile("simple_1.nna");
    private static final File simple_2 = ResourceProvider.getResourceAsFile("simple_2.nna");
    private static final File simple_3 = ResourceProvider.getResourceAsFile("simple_3.nna");
    private static final File schiller_1 = ResourceProvider.getResourceAsFile("MVA_wcrtm_fwd.nna");
    private static final File schiller_2 = ResourceProvider.getResourceAsFile("MVA_wcrtm_inv.nna");

    private static final File[] allFiles = {
        simple_1,
        simple_2,
        simple_3,
        schiller_1,
        schiller_2,
    };

    private static final double EPS = 1e-10;
    private static final double EPS_OUT = 1e-5;


    public JnnTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(JnnTest.class);
    }

    /**
     * Initializes the test environment
     */
    protected void setUp() {
    }

    /**
     * Tests that the reader returns the net section
     */
    public void testStructureOfSimple1() throws IOException, JnnException {
        JnnNet net = Jnn.readNna(simple_1);
        assertNotNull(net);

        assertEquals(3, net.getVersionMinor());
        assertEquals(2, net.getVersionMajor());
        assertEquals(2 + "." + 3, net.getVersionString());
        assertEquals(2, net.getNumLayers());
        assertEquals(0, net.getInputLayerIndex());
        assertEquals(1, net.getOutputLayerIndex());
        assertEquals(8, net.getPrecision());

        JnnLayer layer1 = net.getLayerAt(0);
        assertNotNull(layer1);
        assertEquals(1, layer1.getNumUnits());

        JnnLayer layer2 = net.getLayerAt(1);
        assertNotNull(layer2);
        assertEquals(1, layer2.getNumUnits());
    }

    /**
     * Tests that the reader returns the net section
     */
    public void testStructureOfSimple2() throws IOException, JnnException {

        final JnnNet net = Jnn.readNna(simple_2);
        assertNotNull(net);

        assertEquals(6, net.getVersionMinor());
        assertEquals(1, net.getVersionMajor());
        assertEquals(1 + "." + 6, net.getVersionString());
        assertEquals(2, net.getNumLayers());
        assertEquals(0, net.getInputLayerIndex());
        assertEquals(1, net.getOutputLayerIndex());
        assertEquals(4, net.getPrecision());

        final JnnLayer layer1 = net.getLayerAt(0);
        assertNotNull(layer1);
        assertEquals(1, layer1.getNumUnits());
        final JnnUnit unit11 = layer1.getUnitAt(0);
        assertNotNull(unit11);
        assertEquals(0.0, unit11.getInputBias(), EPS);
        assertEquals(1.0, unit11.getInputScale(), EPS);
        assertEquals(-0.02236024845, unit11.getOutputBias(), EPS);
        assertEquals(+0.01242236025, unit11.getOutputScale(), EPS);
        assertEquals(0, unit11.getNumConnections());

        final JnnLayer layer2 = net.getLayerAt(1);
        assertNotNull(layer2);
        assertEquals(2, layer2.getNumUnits());

        final JnnUnit unit21 = layer2.getUnitAt(0);
        assertNotNull(unit21);
        assertEquals(-0.487265, unit21.getInputBias(), EPS);
        assertEquals(1.0, unit21.getInputScale(), EPS);
        assertEquals(0.0, unit21.getOutputBias(), EPS);
        assertEquals(1.0, unit21.getOutputScale(), EPS);
        assertEquals(1, unit21.getNumConnections());
        final JnnConnection conn211 = unit21.getConnectionAt(0);
        assertNotNull(conn211);
        assertEquals(0, conn211.getSourceLayerIndex());
        assertEquals(0, conn211.getSourceUnitIndex());
        assertEquals(-1.841538, conn211.getWeight(), EPS);

        final JnnUnit unit22 = layer2.getUnitAt(1);
        assertNotNull(unit22);
        assertEquals(+0.487265, unit22.getInputBias(), EPS);
        assertEquals(1.0, unit22.getInputScale(), EPS);
        assertEquals(0.0, unit22.getOutputBias(), EPS);
        assertEquals(1.0, unit22.getOutputScale(), EPS);
        assertEquals(1, unit22.getNumConnections());
        final JnnConnection conn221 = unit22.getConnectionAt(0);
        assertNotNull(conn221);
        assertEquals(0, conn221.getSourceLayerIndex());
        assertEquals(0, conn221.getSourceUnitIndex());
        assertEquals(0.56432, conn221.getWeight(), EPS);

        double[] input = new double[1];
        double [] output = new double[2];

        input[0] = 10.0;
        net.process(input, output);
        assertEquals(10.0, input[0], EPS);
        assertEquals(0.3374116, output[0], EPS_OUT);
        assertEquals(0.6329164, output[1], EPS_OUT);

        input[0] = 0.0;
        net.process(input, output);
        assertEquals(0.0, input[0], EPS);
        assertEquals(0.39029134, output[0], EPS_OUT);
        assertEquals(0.61648297, output[1], EPS_OUT);

        input[0] = -10.0;
        net.process(input, output);
        assertEquals(-10.0, input[0], EPS);
        assertEquals(0.44588152, output[0], EPS_OUT);
        assertEquals(0.59977911, output[1], EPS_OUT);
    }

    public void testThatAllTestFilesCanBeReadWithoutErrors() {
        for (int i = 0; i < allFiles.length; i++) {
            File file = allFiles[i];
            try {
                JnnNet net = Jnn.readNna(file);
                assertNotNull(net);
            } catch (IOException e) {
                e.printStackTrace();
                fail("No IOException expected: file = " + file);
            } catch (JnnException e) {
                e.printStackTrace();
                fail("No JnnException expected: file = " + file);
            }
        }
    }
}
