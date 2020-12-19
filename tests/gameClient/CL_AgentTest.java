package gameClient;
import api.*;

import gameClient.util.Point3D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CL_AgentTest {

    @Test
    void setCalculatedNextNodeAndGet() {
        directed_weighted_graph graph=new DWGraph_DS();
        graph.addNode(new NodeData(0));

        CL_Agent agent=new CL_Agent(graph,0);
        agent.setCalculatedNextNode(5);
        assertEquals(5,agent.getCalculatedNextNode());
    }

    @Test
    void setMyPokemonAndGet() {
        directed_weighted_graph graph=new DWGraph_DS();
        graph.addNode(new NodeData(0));
        CL_Agent agent=new CL_Agent(graph,0);
        CL_Pokemon pokemon=new CL_Pokemon(new Point3D(0,0,0),0,0,null);
        agent.setMyPokemon(pokemon);
        assertEquals(pokemon,agent.getMyPokemon());

    }

    @Test
    void update() {

    }

    @Test
    void getSrcNode() {
        directed_weighted_graph graph=new DWGraph_DS();
        graph.addNode(new NodeData(0));
        CL_Agent agent=new CL_Agent(graph,0);
        assertEquals(agent.getSrcNode(),0);
    }

    @Test
    void setNextNode()
    {
        directed_weighted_graph graph=new DWGraph_DS();
        graph.addNode(new NodeData(0));
        graph.addNode(new NodeData(1));
        graph.connect(0,1,1);
        graph.connect(1,0,1);
        CL_Agent agent=new CL_Agent(graph,0);
        assertTrue(agent.setNextNode(1));
    }

    @Test
    void setCurrNode()
    {
        directed_weighted_graph graph=new DWGraph_DS();
        graph.addNode(new NodeData(0));
        graph.addNode(new NodeData(1));
        CL_Agent agent=new CL_Agent(graph,0);
        agent.setCurrNode(1);
        assertEquals(1,agent.getSrcNode());
        assertFalse(0==agent.getSrcNode());
    }


    @Test
    void getLocation() {
        directed_weighted_graph graph=new DWGraph_DS();
        node_data node_data=new NodeData(0);
        graph.addNode(node_data);
        geo_location geo_location=new GeoLocation(0,0,0);
        node_data.setLocation(geo_location);
        CL_Agent agent = new CL_Agent(graph,0);
        assertEquals(agent.getLocation(),geo_location);

    }

    @Test
    void getValue()
    {
        directed_weighted_graph graph=new DWGraph_DS();
        graph.addNode(new NodeData(0));
        CL_Agent agent=new CL_Agent(graph,0);
        assertEquals(0,agent.getValue());
    }

    @Test
    void getNextNode() {
    }

    @Test
    void getSpeed()
    {
        directed_weighted_graph graph=new DWGraph_DS();
        graph.addNode(new NodeData(0));
        CL_Agent agent=new CL_Agent(graph,0);
        assertEquals(0,agent.getSpeed());

    }

    @Test
    void setSpeed() {
        directed_weighted_graph graph=new DWGraph_DS();
        graph.addNode(new NodeData(0));
        CL_Agent agent=new CL_Agent(graph,0);
        agent.setSpeed(50);
        assertEquals(50,agent.getSpeed());
    }
    @Test
    void get_curr_edge()
    {
    }

    @Test
    void getOldAndSet()
    {
        directed_weighted_graph graph=new DWGraph_DS();
        graph.addNode(new NodeData(0));
        CL_Agent agent=new CL_Agent(graph,0);
        CL_Pokemon pokemon=new CL_Pokemon(new Point3D(0,0,0),0,0,null);
        agent.setOld(pokemon);
        assertEquals(pokemon,agent.getOld());
    }
}