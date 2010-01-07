/*
 * $Id: JnnConnectionTests.java,v 1.3 2005-02-02 22:27:03 norman Exp $
 *
 * Copyright (C) 2002 by Brockmann Consult (info@brockmann-consult.de)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation. This program is distributed in the hope it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.bc.jnn;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

public class JnnConnectionTests extends TestCase {

    private JnnConnection _conn;

    public JnnConnectionTests(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(JnnConnectionTests.class);
    }

    /**
     * Initializes the test environment
     */
    protected void setUp() {
        _conn = new JnnConnection();
        assertNotNull(_conn);
    }

    /**
     * Tests the correct functionality of the source layer index accessors
     */
    public void testSetGetSourceLayerIndex() {
        int expSrcLayerIdx_1 = 4;
        int expSrcLayerIdx_2 = 2;

        _conn.setSourceLayerIndex(expSrcLayerIdx_1);
        assertEquals(expSrcLayerIdx_1, _conn.getSourceLayerIndex());

        _conn.setSourceLayerIndex(expSrcLayerIdx_2);
        assertEquals(expSrcLayerIdx_2, _conn.getSourceLayerIndex());
    }

    /**
     * Tests the correct functionality of the source unit index accessors
     */
    public void testSetGetSourceUnitIndex() {
        int expSrcUnitIdx_1 = 5;
        int expSrcUnitIdx_2 = 9;

        _conn.setSourceUnitIndex(expSrcUnitIdx_1);
        assertEquals(expSrcUnitIdx_1, _conn.getSourceUnitIndex());

        _conn.setSourceUnitIndex(expSrcUnitIdx_2);
        assertEquals(expSrcUnitIdx_2, _conn.getSourceUnitIndex());
    }

    /**
     * Tests the correct functionality of the weight accessors
     */
    public void testSetGetWeight() {
        double expWeight_1 = 32.77;
        double expWeight_2 = 22.9;

        _conn.setWeight(expWeight_1);
        assertEquals(expWeight_1, _conn.getWeight(), 1e-6);

        _conn.setWeight(expWeight_2);
        assertEquals(expWeight_2, _conn.getWeight(), 1e-6);
    }

    /**
     * Tests the setgetInput methods for corretness
     */
    public void testSetGetInputUnit() {
        JnnUnit unit_1 = new JnnUnit();
        JnnUnit unit_2 = new JnnUnit();

        _conn.setInputUnit(unit_1);
        assertSame(unit_1, _conn.getInputUnit());

        _conn.setInputUnit(unit_2);
        assertSame(unit_2, _conn.getInputUnit());
    }
}
