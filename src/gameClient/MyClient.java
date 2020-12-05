package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class MyClient implements Runnable{
    private static MyFrame _win;
    private static Arena _ar;
    ThreadGroup Group=new ThreadGroup("group");
    public static void main(String[] a) {
        Thread client = new Thread(new MyClient());
        client.start();

    }

    @Override
    public void run() {
        int scenario_num = 11;
        game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
        int id = 999;
        game.login(id);
        String g = game.getGraph();
        String pks = game.getPokemons();
        directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
        init(game);

        game.startGame();
        _win.setTitle("Ex2 - OOP: (NONE trivial Solution) "+game.toString());
        int ind=0;
        long dt=100;

        while(game.isRunning()) {
            moveAgants(game, gg);
            try {
                if(ind%3==0) {_win.repaint();}
                Thread.sleep(dt);
                ind++;
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        String res = game.toString();

        System.out.println(res);
        System.exit(0);
    }
    /**
     * Moves each of the agents along the edge,
     * in case the agent is on a node the next destination (next edge) is chosen (randomly).
     * @param game
     * @param gg
     * @param
     */
    private static void moveAgants(game_service game, directed_weighted_graph gg) {
        String lg = game.move();
        List<CL_Agent> log = Arena.getAgents(lg, gg);
        _ar.setAgents(log);
        //ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
        String fs =  game.getPokemons();
        List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
        _ar.setPokemons(ffs);

        for(int i=0;i<log.size();i++) {
            CL_Agent ag = log.get(i);
            int id = ag.getID();
            int dest = ag.getNextNode();
            int src = ag.getSrcNode();
            double v = ag.getValue();
            ThreadGroup Group=new ThreadGroup("group");
            if(dest==-1)
            {
                Runnable findNextNode=new Runnable() {
                    @Override
                    public void run() {

                        int nextNode=nextNode(gg, src);
                        game.chooseNextEdge(ag.getID(), nextNode);
                        System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+nextNode);
                    }
                };
                Thread _id=new Thread(Group,findNextNode);
                _id.start();
//                dest = nextNode(gg, src);
//                game.chooseNextEdge(ag.getID(), dest);
//                System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+dest);
                while (Group.activeCount()!=0)
                {

                }
            }
        }
    }
    /**
     * a very simple random walk implementation!
     * @param g
     * @param src
     * @return
     */
    private static int nextNode(directed_weighted_graph g, int src) {
        CL_Agent Agent = null;
        double x;
        dw_graph_algorithms algo = new DWGraph_Algo(g);
        boolean iKnowWhereTogo = false;
        for (CL_Agent agentIndex : _ar.getAgents())
            if (agentIndex.getSrcNode() == src) {
                Agent = agentIndex;
                break;
            }
        if(Agent.getMyPokemon()!=null)
        for (CL_Pokemon pokemon : _ar.getPokemons()) {
//            Arena.updateEdge(pokemon, g);
            if (pokemon.getMyAgent() == Agent)
                iKnowWhereTogo = true;
//            if (pokemon.getMyAgent() == null) {
//                x = algo.shortestPathDist(Agent.getSrcNode(), pokemon.get_edge().getDest());
//                if (x < Agent.getMyPokemon().getDist()) {
//                    pokemon.getMyAgent().setMyPokemon(null);
//                    Agent.setMyPokemon(pokemon);
//                    pokemon.setAgent(Agent, x);
//                    iKnowWhereTogo = true;
//                }
            }
//        }

            double dist;
            double temp = Double.MAX_VALUE;

            if (!iKnowWhereTogo) {

                for (CL_Pokemon pokemon : _ar.getPokemons()) {
                        Arena.updateEdge(pokemon, g);
                    dist = algo.shortestPathDist(Agent.getSrcNode(), pokemon.get_edge().getDest());

                    if (dist < temp && dist != -1 && dist < pokemon.getDist()) {
                        temp = dist;
                        Agent.setMyPokemon(pokemon);
                    }
                }
//                    }
                if(Agent.getMyPokemon()!=null) {
                    if (Agent.getMyPokemon().getMyAgent() != null)
                        Agent.getMyPokemon().getMyAgent().setMyPokemon(null);
                    Agent.getMyPokemon().setAgent(Agent, temp);
                }

            }
           // if(!ThereIsPokemon)
          //  else
            //    {
            //    Agent.setMyPokemon(queue.poll());
            //    Agent.getMyPokemon().setAgent(Agent);
         //   }
//        if(pokemonEdge==null)
//            return -1;
            if (Agent.getSrcNode() == Agent.getMyPokemon().get_edge().getSrc()) {
                return Agent.getMyPokemon().get_edge().getDest();
            }
////            }
//
            List<node_data> path = algo.shortestPath(Agent.getSrcNode(), Agent.getMyPokemon().get_edge().getSrc());
            if (path != null&&!path.isEmpty())
                return path.get(1).getKey();
            return -1;


        }
    private void init(game_service game) {
        String g = game.getGraph();
        String fs = game.getPokemons();
        directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
        //gg.init(g);
        _ar = new Arena();
        _ar.setGraph(gg);
        _ar.setPokemons(Arena.json2Pokemons(fs));
        _win = new MyFrame("test Ex2");
        _win.setSize(1000, 700);
        _win.update(_ar);


        _win.show();
        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("agents");
            System.out.println(info);
            System.out.println(game.getPokemons());
            int src_node = 0;  // arbitrary node, you should start at one of the pokemon
            ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
            for(int a = 0;a<cl_fs.size();a++) { Arena.updateEdge(cl_fs.get(a),gg);}
            for(int a = 0;a<rs;a++) {
                int ind = a%cl_fs.size();
                CL_Pokemon c = cl_fs.get(ind);
                int nn = c.get_edge().getDest();
                if(c.getType()<0 ) {nn = c.get_edge().getSrc();}

                game.addAgent(nn);
            }
        }
        catch (JSONException e) {e.printStackTrace();}
    }
}
