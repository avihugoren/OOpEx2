package api;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest {

    @Test
    public void equalTest()
    {
        directed_weighted_graph g= graphBuilder(100);
        assertEquals(g,g);
        directed_weighted_graph graph2=graphBuilder(100);
        assertEquals(g,graph2);
        g.connect(0,2,2);
        assertNotEquals(g,graph2);
        graph2.connect(0,2,1);
        assertNotEquals(g,graph2);
        graph2.connect(0,2,2);
        assertEquals(g,graph2);
    }
    @Test
    void copyConstructor(){

        directed_weighted_graph g =graphBuilder(3);
        g.connect(0,1,2);
        g.connect(0,2,2);
        directed_weighted_graph g1 = new DWGraph_DS(g);
        assertEquals(g,g1);
        g1.connect(0,1,3);
        assertNotEquals(g1,g);
    }
    @Test
    void getNode() {
        directed_weighted_graph g = graphBuilder(3);
        assertNull(g.getNode(4));
        assertEquals(g.getNode(2),new NodeData(2));

        }


    @Test
    void getEdge() {
        directed_weighted_graph g = graphBuilder(100);
        assertNull(g.getEdge(0,1));
        for (int i = 0; i < g.nodeSize(); i++) {
            g.connect(0, i, 10);
            if(i!=0)
            assertEquals(g.getEdge(0, i), new EdgeData(0, i, 10));
        }

    }

    @Test
    void addNode() {
        directed_weighted_graph g = graphBuilder(4);
        assertEquals(4,g.nodeSize());
        g.addNode(new NodeData(0));
        assertEquals(4,g.nodeSize());
    }

    @Test
    void connect() {
        directed_weighted_graph g = graphBuilder(5);
        g.connect(0, 1, 2);
        g.connect(0, 2, 2);
        g.connect(0, 3, 2);
        g.connect(3, 4, 2);
        g.connect(3, 4, 6);
        assertEquals(6, g.getEdge(3, 4).getWeight());//check if the weight of the edge Updated
        assertEquals(4, g.edgeSize());//Check that did not add a edge that already exists
        g.removeEdge(3, 4);
        g.removeEdge(3, 4);
        g.removeEdge(3, 19);
        assertEquals(3, g.edgeSize());//Checks that only edge that belong to the graph are deleted

    }

    @Test
    void getV() {
        directed_weighted_graph g = graphBuilder(100);
        for (int i = 0; i < g.nodeSize(); i++) {
            g.connect(0, i, 50);
        }
        for (int i = 0; i < 100; i++) {
            assertTrue(g.getV().contains(g.getNode(i)));
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
        assertTrue(g.getE(0).contains(g.getEdge(0,1)));
        assertTrue(g.getE(0).contains(g.getEdge(0,2)));
        assertTrue(g.getE(0).contains(g.getEdge(0,3)));
        assertTrue(g.getE(0).contains(g.getEdge(0,4)));
        assertTrue(g.getE(0).contains(g.getEdge(0,5)));
        assertFalse(g.getE(0).contains(g.getEdge(0,50)));
        assertFalse(g.getE(0).contains(g.getEdge(0,60)));
        g.removeEdge(0, 1);
        assertFalse(g.getE(0).contains(g.getEdge(0,1)));
    }

@Test
void removeNode() {
    directed_weighted_graph g = graphBuilder(3);
    assertEquals(3,g.nodeSize());
    g.removeNode(4);//not in the graph
    g.removeNode(2);
    g.removeNode(2);
    assertEquals(2,g.nodeSize());
}
@Test
    void removeNode2() {
    directed_weighted_graph g = graphBuilder(5);
    g.connect(0,1,2);
    g.connect(1,2,2);
    g.connect(2,3,2);
    g.removeNode(2);
    assertEquals(1,g.edgeSize());
    g.removeNode(4);
    assertEquals(1,g.edgeSize());
}

    @Test
    void removeEdge () {
        directed_weighted_graph g = graphBuilder(5);
        g.connect(0,1,2);
        g.connect(1,0,9);
        g.connect(1,2,2);
        g.connect(2,3,2);
        g.removeEdge(0,9);
        g.removeEdge(0,0);
        g.removeEdge(0,1);
        g.removeEdge(0,1);
        assertEquals(3,g.edgeSize());
    }

    @Test
    void nodeSize () {
        directed_weighted_graph g= graphBuilder(5);
        assertEquals(5,g.nodeSize());
        g.removeNode(0);
        g.removeNode(9);
        assertEquals(4,g.nodeSize());
    }

    @Test
    void edgeSize () {
        directed_weighted_graph g= graphBuilder(5);
        assertEquals(0,g.edgeSize());
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.connect(3,0,3);
        g.connect(3,0,9);
        assertEquals(4,g.edgeSize());
        g.removeEdge(0,1);
        g.removeEdge(0,9);
        assertEquals(3,g.edgeSize());
    }

    @Test
    void getMC () {
        directed_weighted_graph g= graphBuilder(6);
        g.connect(0,1,1);
        g.connect(1,2,3.14);
        g.connect(3,4,1);
        g.connect(1,0,1);
        g.connect(3,8,1);
        g.connect(1,0,5);
        assertEquals(11,g.getMC());
    }

    public directed_weighted_graph graphBuilder(int size) {
        directed_weighted_graph g = new DWGraph_DS();
        for (int i = 0; i < size; i++) {
            g.addNode(new NodeData(i));
        }
        return g;
    }
}