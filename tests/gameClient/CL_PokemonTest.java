package gameClient;

import api.*;
import gameClient.util.Point3D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CL_PokemonTest {

    @Test
    void getDistAndSet()
    {
        CL_Pokemon pokemon =new CL_Pokemon(null,0,0,null);
        pokemon.setDist(100);
        assertEquals(100,100);


    }


    @Test
    void setAgentAndGet() {
        directed_weighted_graph graph=new DWGraph_DS();
        graph.addNode(new NodeData(0));
        CL_Agent agent=new CL_Agent(graph,0);
        CL_Pokemon pokemon =new CL_Pokemon(null,0,0,null);
        pokemon.setAgent(agent,50);
        assertEquals(agent,pokemon.getMyAgent());
        assertEquals(50,pokemon.getDist());
    }
    @Test
    void get_edge() {
    }

    @Test
    void set_edge() {
    }

    @Test
    void getLocation()
    {
        Point3D point3D=new Point3D(0,0,0) ;
        CL_Pokemon pokemon = new CL_Pokemon(point3D,0,0,null);
        assertEquals(point3D,pokemon.getLocation());
    }

    @Test
    void getType() {
        CL_Pokemon pokemon=new CL_Pokemon(null,-1,0,null);
        assertEquals(-1,pokemon.getType());
    }

    @Test
    void getValue() {
        CL_Pokemon pokemon=new CL_Pokemon(null,-1,0,null);
        assertEquals(0,pokemon.getValue());
    }

    @Test
    void testEquals()
    {
        Point3D point3D=new Point3D(0,0,0);
        CL_Pokemon A=new CL_Pokemon(point3D,5,5,null);
        CL_Pokemon B=new CL_Pokemon(new Point3D(5,5,5),5,5,null);
        assertNotEquals(A,B);
        CL_Pokemon C=new CL_Pokemon(point3D,5,5,null);
        assertEquals(C,A);


    }

    @Test
    void compareTo()
    {
        CL_Pokemon A =new CL_Pokemon(new Point3D(0,0,0),0,0,null);
        CL_Pokemon B =new CL_Pokemon(new Point3D(0,0,0),0,5,null);
        CL_Pokemon C =new CL_Pokemon(new Point3D(0,0,0),0,-5,null);
        assertEquals(-1,A.compareTo(C));
        assertEquals(0,B.compareTo(B));
        assertEquals(1,C.compareTo(A));
    }

    @Test
    void update() {
        directed_weighted_graph graph=new DWGraph_DS();
        graph.addNode(new NodeData(0));
        CL_Agent agent=new CL_Agent(graph,0);
        CL_Pokemon pokemon =new CL_Pokemon(null,0,0,null);
        pokemon.setAgent(agent,50);
        CL_Pokemon pokemon1 =new CL_Pokemon(null,0,0,null);
        assertNotEquals(pokemon1.getDist(),pokemon.getDist());
        assertNotEquals(pokemon1.getMyAgent(),pokemon.getMyAgent());
        pokemon1.update(pokemon);
        assertEquals(pokemon1.getDist(),pokemon.getDist());
        assertEquals(pokemon1.getMyAgent(),pokemon.getMyAgent());



    }
}