//package gameClient;
//
//import api.GeoLocation;
//import gameClient.util.Point3D;
//import org.junit.jupiter.api.Test;
//
//import java.util.LinkedList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ArenaTest {
//
//    @Test
//    void setPokemons() {
//    }
//
//    @Test
//    void setAgents() {
//    }
//
//    @Test
//    void setGraph() {
//    }
//
//    @Test
//    void getAgents() {
//    }
//
//    @Test
//    void getPokemons() {
//    }
//
//    @Test
//    void getGraph() {
//    }
//
//    @Test
//    void testGetAgents() {
//    }
//
//    @Test
//    void json2Pokemons()
//    {
//        String testString="{\"Pokemons\":[{\"Pokemon\":{\"value\":1.0,\"type\":-1,\"pos\":\"1,0.0\"}},{\"Pokemon\":{\"value\":2.0,\"type\":-1,\"pos\":\"2,0.0\"}}]}";
//        GeoLocation geoLocation1=new GeoLocation(1,0,0);
//        Point3D point3D1=new Point3D(geoLocation1);
//        CL_Pokemon pokemon1=new CL_Pokemon(point3D1,-1,1.0,null);
//        Point3D point3D2=new Point3D(2,0);
//        CL_Pokemon pokemon2= new CL_Pokemon(point3D2,-1,2.0,null);
//        List<CL_Pokemon>pokemonList=new LinkedList<>();
//        pokemonList.add(pokemon1);
//        pokemonList.add(pokemon2);
//        List<CL_Pokemon>pokemonsFromJson=Arena.json2Pokemons(testString);
//        for (int i = 0; i <pokemonList.size() ; i++)
//        {
//         assertFalse(pokemonsFromJson.contains(pokemonList.get(i)));
//
//        }
//
//    }
//
//    @Test
//    void updateEdge() {
//    }
//
//    @Test
//    void w2f() {
//    }
//}