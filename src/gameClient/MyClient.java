package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class MyClient implements Runnable{
    private static MyFrame3 _win;
    private static Arena _ar;
    private static boolean doubleTactic=false;
    double gameTime;
    double timeToEnd;

    private int id ;
    private int scenario_num;
    ThreadGroup Group=new ThreadGroup("group");
    public void setId(int id)
    {
        this.id=id;
    }
    public void setScenarioNum(int num)
    {
        this. scenario_num=num;
    }


    @Override
    public void run() {
        game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
        game.login(999);
        String g = game.getGraph();
        String pks = game.getPokemons();
        System.out.println(pks);
        //  directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
        directed_weighted_graph gg=buildGraphFromJason(game.getGraph());
        init(game);

        game.startGame();
        panelTimer p=new panelTimer(game);
        _win.myPanel.add(p);
        _win.setVisible(true);
        gameTime=game.timeToEnd();
        timeToEnd=game.timeToEnd();

        _win.setTitle("Ex2 - OOP: (NONE trivial Solution) "+game.toString());
        int ind=0;
        long dt=140;

        while(game.isRunning()) {
            try {
                moveAgants(game, gg);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if(ind%0.5==0) {_win.repaint();}
                if(game.timeToEnd()<gameTime*(2.0/3.5))
                    dt=81;
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
    private static void moveAgants(game_service game, directed_weighted_graph gg) throws JSONException {
        String lg = game.move();
        List<CL_Agent> log = Arena.getAgents(lg, gg);
        _ar.setAgents(log,lg);
        //ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
        String fs =  game.getPokemons();
        List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
        _ar.setPokemons(ffs);
        for (int i = 0; i <_ar.getPokemons().size() ; i++)
        {
            Arena.updateEdge(_ar.getPokemons().get(i),gg );
        }
        for(int i=0;i<_ar.getAgents().size();i++)
        {
            CL_Agent ag = _ar.getAgents().get(i);
//            int id = ag.getID();
            int dest = ag.getNextNode();
            int src = ag.getSrcNode();
//            double v = ag.getValue();
            if(dest==-1)
            {
                ag.setCalculatedNextNode(nextNode(gg, src, ag,game));
//                game.chooseNextEdge(ag.getID(), dest);
//                System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+dest);
            }
        }


        boolean b=true;
        for (int i = 0; i <_ar.getAgents().size()&&b ; i++)
            for (int j = 0; j < _ar.getAgents().size(); j++)
            {
                b = false;
                if (_ar.getAgents().get(i).getMyPokemon() == null && _ar.getAgents().get(i).getNextNode() == -1) {
                    _ar.getAgents().get(i).setCalculatedNextNode(nextNode(gg, 0, _ar.getAgents().get(i), game));
                    b = true;
                }

            }



        for(int i=0;i<_ar.getAgents().size();i++)
        {
            CL_Agent ag = _ar.getAgents().get(i);
            int id = ag.getID();
            int dest = ag.getNextNode();
            int src = ag.getSrcNode();
            double v = ag.getValue();
            if(dest==-1)
            {
//                ag.setCalculatedNextNode(nextNode(gg, src, ag,game));
                if(ag.getCalculatedNextNode()!=-1)
                {
                    game.chooseNextEdge(ag.getID(), ag.getCalculatedNextNode());
                    System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + ag.getCalculatedNextNode());
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
    private static int nextNode(directed_weighted_graph g, int src,CL_Agent Agent,game_service game)
    {

        dw_graph_algorithms algo = new DWGraph_Algo(g);

            double dist;
            double temp = Double.MAX_VALUE;
            for (CL_Pokemon pokemon : _ar.getPokemons())
            {
                {
                    Arena.updateEdge(pokemon, g);
                    dist = (algo.shortestPathDist(Agent.getSrcNode(), pokemon.get_edge().getSrc()))/Agent.getSpeed();
                    if ( dist < temp && dist != -1&&dist<pokemon.getDist())
                    {
                        temp = dist;
                        Agent.setMyPokemon(pokemon);
                    }
                }
            }
            if (Agent.getMyPokemon() != null) {
                if (Agent.old != null &&!Agent.getMyPokemon().equals(Agent.old) ) {
                    Agent.old.setDist(temp+ algo.shortestPathDist(Agent.getMyPokemon().get_edge().getDest(),Agent.old.get_edge().getSrc()));
                    Agent.old = Agent.getMyPokemon();
                }
                if (Agent.getMyPokemon().getMyAgent() != null && Agent.getMyPokemon().getMyAgent() != Agent) {
                    Agent.getMyPokemon().getMyAgent().setMyPokemon(null);
                }
                Agent.getMyPokemon().setAgent(Agent, temp);
            }
            if (Agent.getMyPokemon() != null && Agent.getSrcNode() == Agent.getMyPokemon().get_edge().getSrc())
            {
                int dest=Agent.getMyPokemon().get_edge().getDest();
                return dest;
            }
            if (Agent.getMyPokemon()!=null)
            {
                List<node_data> path = algo.shortestPath(Agent.getSrcNode(), Agent.getMyPokemon().get_edge().getSrc());

                return path.get(1).getKey();
            }
            System.out.println("debug");
            return -1;



    }

    private void init(game_service game) {
        String g = game.getGraph();
        String fs = game.getPokemons();
        this.doubleTactic=false;

        directed_weighted_graph gg = buildGraphFromJason(game.getGraph());
        //  directed_weighted_graph gg=game.getJava_Graph_Not_to_be_used();
        //gg.init(g);
        _ar = new Arena();
        _ar.setGraph(gg);
        _ar.setPokemons(Arena.json2Pokemons(fs));
        _win = new MyFrame3("test Ex2",_ar);
        _win.setSize(1000, 700);
        //  _win.update(_ar);


        _win.show();
        _win.setVisible(true);
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
            for(int a = 0;a<cl_fs.size();a++)
            {
                Arena.updateEdge(cl_fs.get(a),gg);
            }
            List<CL_Pokemon>pokemonList=_ar.getPokemons();
            PriorityQueue<CL_Pokemon>pq=new PriorityQueue<>();
//            if(pokemonList.size()>=rs*2)
//               this.doubleTactic=true;

            for (int i = 0; i <pokemonList.size() ; i++)
            {
                pq.add(pokemonList.get(i));
            }
            for (int i = 0; i <rs ; i++)
            {
                if(pq.peek()!=null&&!pq.isEmpty())
                {
                    Arena.updateEdge(pq.peek(), gg);
                    game.addAgent(pq.poll().get_edge().getSrc());
                }
                else
                    game.addAgent(i);
            }

        }
        catch (JSONException e) {e.printStackTrace();}
    }
    public directed_weighted_graph buildGraphFromJason(String g)
    {
        GsonBuilder builder=new GsonBuilder();
        builder.registerTypeAdapter(directed_weighted_graph.class, new GraphJson());
        Gson json = builder.create();
        directed_weighted_graph graph= json.fromJson(g,directed_weighted_graph.class);
        return graph;
    }
    public static CL_Pokemon returnClosetPokemon(List<CL_Pokemon> list, CL_Pokemon pokemon, dw_graph_algorithms algo)
    {
        double temp= Double.MAX_VALUE;
        double dist;
        CL_Pokemon tempPoke=null;
        for (int i = 0; i <list.size() ; i++) {
            if(list.get(i)!=pokemon&&list.get(i).youShouldSerchMe==null&&list.get(i).getMyAgent()==null)
            {
                dist=algo.shortestPathDist(pokemon.get_edge().getSrc(),list.get(i).get_edge().getSrc());
                if(dist<temp&&dist!=-1) {
                    temp = dist;
                    tempPoke = list.get(i);
                }
            }
        }
        return tempPoke;
    }
}