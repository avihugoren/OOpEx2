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
        //empty graph
        directed_weighted_graph emptyGraph=new DWGraph_DS();
        algo.init(emptyGraph);
        assertTrue(algo.save("emptyGraph"));
        algo.load("emptyGraph");
        assertEquals(emptyGraph,algo.getGraph());


    }

    public directed_weighted_graph graphBuilder(int size) {
        directed_weighted_graph g = new DWGraph_DS();
        for (int i = 0; i < size; i++) {
            g.addNode(new NodeData(i));
        }
        return g;
    }
}