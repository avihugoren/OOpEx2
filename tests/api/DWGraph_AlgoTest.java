package api;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {

    @Test
    void init() {
    }

    @Test
    void getGraph() {
    }

    @Test
    void copy() {
    }

    @Test
    void isConnected() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.connect(0,1,2);
        g.connect(0,2,2);
        g.connect(1,2,1);
        g.connect(2,3,4);
        g.connect(0,3,1);
        g.connect(2,1,7);
        g.connect(1,0,2);
        g.connect(3,2,1);
        dw_graph_algorithms g1=new DWGraph_Algo(g);
        assertTrue(g1.isConnected());
        g.removeEdge(3,2);
        assertFalse(g1.isConnected());
    }

    @Test
    void shortestPathDist() {
    }

    @Test
    void shortestPath() {
    }

    @Test
    void save() {
        directed_weighted_graph g =graphBuilder(30);
        dw_graph_algorithms algo=new DWGraph_Algo(g);
        g.connect(0,5,5);
        g.connect(0,6,6);
       assertTrue(algo.save("testSave"));
    }

    @Test
    void load() {
        directed_weighted_graph g= graphBuilder(50);
        dw_graph_algorithms algo=new DWGraph_Algo(g);
        g.connect(0,2,2);
        g.connect(0,1,2);
        System.out.println(g);
        assertTrue(algo.save("testload"));
        algo.load("testload");
        System.out.println(algo.getGraph());
        assertEquals(g,algo.getGraph());
    }

    public directed_weighted_graph graphBuilder(int size) {
        directed_weighted_graph g = new DWGraph_DS();
        for (int i = 0; i < size; i++) {
            g.addNode(new NodeData(i));
        }
        return g;
    }
}