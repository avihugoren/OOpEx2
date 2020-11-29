package api;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest {
    public directed_weighted_graph graphBuilder(int size) {
        directed_weighted_graph g = new DWGraph_DS();
        for (int i = 0; i < size; i++) {
            g.addNode(new NodeData(i));
        }
        return g;
    }


    @Test
    void getNode() {
        directed_weighted_graph g = graphBuilder(50);
        for (int i = 0; i < g.nodeSize(); i++) {
            assertEquals(new NodeData(i), g.getNode(i));
        }
    }

    @Test
    void getEdge() {
        directed_weighted_graph g = graphBuilder(100);
        for (int i = 0; i < g.nodeSize(); i++) {
            g.connect(0, i, 10);
            assertEquals(g.getEdge(0, i), new EdgeData(0, i, 10));
        }
    }

    @Test
    void addNode() {
        directed_weighted_graph g = graphBuilder(50);
        for (int i = 0; i < g.nodeSize(); i++) {
            assertEquals(new NodeData(i), g.getNode(i));
        }
    }

    @Test
    void connect() {
        directed_weighted_graph g = graphBuilder(100);
        for (int i = 0; i < g.nodeSize(); i++) {
            g.connect(0, i, 10);
            if (i != 0)
                assertNotNull(g.getEdge(0, i));
        }
    }

    @Test
    void getV() {
        directed_weighted_graph g = graphBuilder(100);
        for (int i = 0; i < g.nodeSize(); i++) {
            g.connect(0, i, 50);
        }
        for (int i = 0; i < 100; i++) {
            assertTrue(g.getV().contains(new NodeData(i)));
        }
        g.removeNode(0);
        g.removeNode(5);
        assertFalse(g.getV().contains(0));
        assertFalse(g.getV().contains(5));
    }

    @Test
    void getE() {
        directed_weighted_graph g = graphBuilder(50);
        g.connect(0, 1, 1);
        g.connect(0, 2, 1);
        g.connect(0, 3, 1);
        g.connect(0, 4, 1);
        g.connect(0, 5, 1);
        assertTrue(g.getE(0).contains(new EdgeData(0, 1, 1)));
        assertTrue(g.getE(0).contains(new EdgeData(0, 2, 1)));
        assertTrue(g.getE(0).contains(new EdgeData(0, 3, 1)));
        assertTrue(g.getE(0).contains(new EdgeData(0, 4, 1)));
        assertTrue(g.getE(0).contains(new EdgeData(0, 5, 1)));
        assertFalse(g.getE(0).contains(new EdgeData(0, 50, 1)));
        assertFalse(g.getE(0).contains(new EdgeData(0, 60, 1)));
        g.removeEdge(0, 1);
        assertFalse(g.getE(0).contains(new EdgeData(0, 1, 1)));

    }

//    @Test
//    void removeNode() {
//    }
@Test
void removeNode() {
    directed_weighted_graph g = graphBuilder(3);
    for (int i = 0; i <g.nodeSize() ; i++) {
        g.connect(0,i,i);
    }
    assertEquals(2,g.edgeSize());
    g.removeNode(0);
    assertEquals(0,g.edgeSize());
}

    @Test
    void removeEdge() {
    }

    @Test
    void nodeSize() {
    }

    @Test
    void edgeSize() {
    }

    @Test
    void getMC() {
    }

    void copy() {

        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.connect(0, 1, 2);
        g.connect(0, 2, 2);
        directed_weighted_graph g1 = new DWGraph_DS(g);
        assertEquals(g, g1);

    }
}