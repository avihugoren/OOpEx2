package gameClient;

import api.DWGraph_DS;
import api.GeoLocation;
import api.NodeData;
import gameClient.util.Point3D;
import org.junit.jupiter.api.Test;
import api.directed_weighted_graph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArenaTest {

    @Test
    void setPokemons() {
    }

    @Test
    void setAgents()
    {

    }

    @Test
    void setGraphAndGet() {
        Arena arena =new Arena();
        directed_weighted_graph graph=new DWGraph_DS();
        graph.addNode(new NodeData(50));
        arena.setGraph(graph);
        assertEquals(arena.getGraph(),graph);

    }

    @Test
    void getAgents()
    {
        directed_weighted_graph graph=new DWGraph_DS();
        String stringJson= "{\"Agents\":[{\"Agent\":{\"id\":0,\"value\":5.0,\"src\":9,\"dest\":8,\"speed\":1.0,\"pos\":\"5,0,0.0\"}}]}";
        graph.addNode(new NodeData(8));
        graph.addNode(new NodeData(9));
        graph.connect(8,9,1);
        graph.connect(9,8,1);
        graph.getNode(9).setLocation(new GeoLocation(5,0,0));
        CL_Agent agent=new CL_Agent(graph,9);

        agent.setNextNode(9);
        List<CL_Agent>agentList=Arena.getAgents(stringJson,graph);

        assertEquals(agent.getID(),agentList.get(0).getID());



    }

    @Test
    void getPokemons() {
    }


    @Test
    void testGetAgents() {
    }

    @Test
    void json2Pokemons() throws FileNotFoundException
    {
        String stringJson= "{\"Pokemons\":[{\"Pokemon\":{\"value\":5.0,\"type\":-1,\"pos\":\"5,5,0.0\"}}]}";
        CL_Pokemon pokemon=new CL_Pokemon(new Point3D(5,5,0),-1,5,null);
        List<CL_Pokemon>pokemonList=Arena.json2Pokemons(stringJson);
        assertEquals(pokemonList.get(0),pokemon);
        assertTrue(pokemon.getDist()==pokemonList.get(0).getDist());
        assertTrue(pokemon.getMyAgent()==pokemonList.get(0).getMyAgent());
        assertTrue(pokemon.getType()==pokemonList.get(0).getType());
        assertTrue(pokemon.getValue()==pokemonList.get(0).getValue());






    }

    @Test
    void updateEdge() {
    }

    @Test
    void w2f() {
    }
}