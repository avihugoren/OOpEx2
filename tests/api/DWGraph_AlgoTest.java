package api;

import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {

    @Test
    void init() {
        directed_weighted_graph graph= graphBuilder(4);
        graph.connect(0,1,1);
        graph.connect(1,2,2);
        dw_graph_algorithms g=new DWGraph_Algo();
        g.init(graph);
        graph.connect(0,2,2);
        assertEquals(graph,g.getGraph());
    }

    @Test
    void getGraph() {
        directed_weighted_graph graph1=graphBuilder(4);
        dw_graph_algorithms g1=new DWGraph_Algo(graph1);
        assertEquals(graph1,g1.getGraph());
        directed_weighted_graph graph2=graphBuilder(7);
        g1.init(graph2);
        assertEquals(graph2,g1.getGraph());
    }

    @Test
    void copy() {

        directed_weighted_graph graph=graphBuilder(4);
        graph.connect(0,1,1);
        graph.connect(1,2,2);
        dw_graph_algorithms g1=new DWGraph_Algo(graph);
         directed_weighted_graph g2=g1.copy();
         assertEquals(g1.getGraph(),g2);
        graph.removeNode(0);
        assertEquals(3,graph.nodeSize());
        assertEquals(4,g2.nodeSize());
    }

    @Test
    void isConnected() {
        directed_weighted_graph g = new DWGraph_DS();
        dw_graph_algorithms g1=new DWGraph_Algo(g);
        assertTrue(g1.isConnected());//empty graph is connected
        g.addNode(new NodeData(0));
        assertTrue(g1.isConnected());//graph with 1 node is connected
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
        assertTrue(g1.isConnected());
        g.removeEdge(3,2);
        assertFalse(g1.isConnected());
    }

    @Test
    void shortestPathDist() {
       directed_weighted_graph g=new DWGraph_DS(graphBuilder(5));
       g.connect(0,1,1);
       g.connect(0,2,1);
       g.connect(1,2,2);
       g.connect(2,3,3);
       dw_graph_algorithms g1=new DWGraph_Algo(g);
       assertEquals(4,g1.shortestPathDist(0,3));
       assertEquals(-1,g1.shortestPathDist(0,4));
       assertEquals(-1,g1.shortestPathDist(8,9));
       assertEquals(0,g1.shortestPathDist(0,0));
    }

    @Test
    void shortestPath() {
        directed_weighted_graph g=graphBuilder(5);
        dw_graph_algorithms g1=new DWGraph_Algo(g);
        assertNull(g1.shortestPath(0,9));
        assertNull(g1.shortestPath(10,9));
        g.connect(0,1,1);
        g.connect(0,2,1);
        g.connect(1,2,2);
        g.connect(2,3,3);
        assertNull(g1.shortestPath(3,1));
        List<node_data> help=g1.shortestPath(0,3);
        assertEquals(3,help.size());
        int[] arr={0,2,3};
        int i=0;
        boolean b =true;
        for(node_data node:help) {
            if (node.getKey() != arr[i])
                b = false;
                i++;
        }
        assertTrue(b);
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