//package gameClient;
//
//import Server.Game_Server_Ex2;
//import api.*;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.PriorityQueue;
//
//public class MyClient2 implements Runnable{
//    private static MyFrame _win;
//    private static Arena _ar;
//    ThreadGroup Group=new ThreadGroup("group");
//    public static void main(String[] a) {
//        Thread client = new Thread(new MyClient2());
//        client.start();
//
//    }
//
//    @Override
//    public void run() {
//        int scenario_num = 17;
//        game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
//        int id = 999;
//        game.login(id);
//        String g = game.getGraph();
//        String pks = game.getPokemons();
//        directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
//
//        init(game);
//
//        game.startGame();
//        _win.setTitle("Ex2 - OOP: (NONE trivial Solution) "+game.toString());
//        int ind=0;
//        long dt=100;
//
//        while(game.isRunning()) {
//            moveAgants(game, gg);
//            try {
//                if(ind%3==0) {_win.repaint();}
//                Thread.sleep(dt);
//                ind++;
//            }
//            catch(Exception e) {
//                e.printStackTrace();
//            }
//        }
//        String res = game.toString();
//
//        System.out.println(res);
//        System.exit(0);
//    }
//    /**
//     * Moves each of the agents along the edge,
//     * in case the agent is on a node the next destination (next edge) is chosen (randomly).
//     * @param game
//     * @param gg
//     * @param
//     */
//    private static void moveAgants(game_service game, directed_weighted_graph gg) throws JSONException {
//        String lg = game.move();
//        List<CL_Agent> log = Arena.getAgents(lg, gg);
//        _ar.setAgents(log,lg);
//        //ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
//        String fs =  game.getPokemons();
//        List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
//        _ar.setPokemons(ffs);
//        List<CL_Pokemon>pokemonsList=_ar.getPokemons();
//        dw_graph_algorithms algo=new DWGraph_Algo(gg);
//        boolean iKnowWhereTogo=false;
//        double dist;
//        double temp=Double.MAX_VALUE;
//        for(int i=0;i<log.size();i++)
//        {
//            CL_Agent ag = log.get(i);
//            int id = ag.getID();
//            int dest = ag.getNextNode();
//            int src = ag.getSrcNode();
//            double v = ag.getValue();
//            if(dest==-1) {
//                if (_ar.getPokemons().contains(ag.getMyPokemon()))
//                    iKnowWhereTogo = true;
//                if (!iKnowWhereTogo) {
//
//                }
//                for (int j = 0; j < pokemonsList.size(); j++) {
//                    Arena.updateEdge(pokemonsList.get(j), gg);
//                    dist = algo.shortestPathDist(ag.getSrcNode(), pokemonsList.get(j).get_edge().getSrc());
//                    if (dist < temp && dist != -1 && dist<pokemonsList.get(i).getDist())
//                        ag.setMyPokemon(pokemonsList.get(i));
//                }
//                if (ag.getMyPokemon() != null) {
//                    ag.getMyPokemon().setAgent(ag, temp);
//
//                }
//            }
//            if(ag.getMyPokemon()!=null)
//            {
//                dest = nextNode(gg, ag);
//                game.chooseNextEdge(ag.getID(), dest);
//                System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest);
//            }
//
//
//            }
//        }
//
//
//    private static int nextNode(directed_weighted_graph gg,CL_Agent Agent)
//    {
//        return -1;
//
//
//
//    }
//    private void init(game_service game) {
//        String g = game.getGraph();
//        String fs = game.getPokemons();
//        directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
//        //gg.init(g);
//        _ar = new Arena();
//        _ar.setGraph(gg);
//        _ar.setPokemons(Arena.json2Pokemons(fs));
//        _win = new MyFrame("test Ex2");
//        _win.setSize(1000, 700);
//        _win.update(_ar);
//
//
//        _win.show();
//        String info = game.toString();
//        JSONObject line;
//        try {
//            line = new JSONObject(info);
//            JSONObject ttt = line.getJSONObject("GameServer");
//            int rs = ttt.getInt("agents");
//            System.out.println(info);
//            System.out.println(game.getPokemons());
//            int src_node = 0;  // arbitrary node, you should start at one of the pokemon
//            ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
//            for(int a = 0;a<cl_fs.size();a++) { Arena.updateEdge(cl_fs.get(a),gg);}
//            for(int a = 0;a<rs;a++) {
//                int ind = a%cl_fs.size();
//                CL_Pokemon c = cl_fs.get(ind);
//                int nn = c.get_edge().getDest();
//                if(c.getType()<0 ) {nn = c.get_edge().getSrc();}
//
//                game.addAgent(nn);
//            }
//        }
//        catch (JSONException e) {e.printStackTrace();}
//    }
//}