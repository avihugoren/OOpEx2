package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class MyClient implements Runnable{
    private static MyFrame _win;
    private static Arena _ar;
    double gameTime;
    private int id ;
    private int scenario_num;

    /**
     * get id and set the id of the client
     * @param id
     */
    public void setId(int id)
    {
        this.id=id;
    }
    /**
     * get num of level and set the scenario_num of the client
     * @param num
     */
    public void setScenarioNum(int num)
    {
        this. scenario_num=num;
    }


    @Override
    public void run() {
        game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
        game.login(id);//uploaded the data for user with this id
        String g = game.getGraph();
        String pks = game.getPokemons();
        System.out.println(pks);
        //  directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
        directed_weighted_graph gg=buildGraphFromJason(game.getGraph());
        init(game);//Creating the graphical interface

        game.startGame();
        panelTimer p=new panelTimer(game);//creat new panel that add the timer to the frame
        _win.myPanel.add(p);
        _win.setVisible(true);
        gameTime=game.timeToEnd();

        _win.setTitle("(Noa and Avihu solution) "+game.toString());
        int ind=0;
        long dt=150;

        //split the game to 3 parts by time each part have diffrent sleep time of the threads start with high time and goes lower over time
        while(game.isRunning()) {
            try {
                moveAgants(game, gg);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if(ind%1==0) {_win.repaint();}
                if(game.timeToEnd()<gameTime*(2.0/3.0))
                    dt=110;
                if(game.timeToEnd()<gameTime*(1.0/3.0))
                    dt=70;
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
        String gameString = game.move();
        List<CL_Agent> agentList = Arena.getAgents(gameString, gg);
        _ar.setAgents(agentList,gameString);

        String pokemonsString =  game.getPokemons();
        List<CL_Pokemon> pokemonList = Arena.json2Pokemons(pokemonsString);
        _ar.setPokemons(pokemonList);
        //for loop that update all the edges of the pokemons
        for (int i = 0; i <_ar.getPokemons().size() ; i++)
            Arena.updateEdge(_ar.getPokemons().get(i), gg);
        //for loop that calculate the next node for each agent and save it inside the agent
        for(int i=0;i<_ar.getAgents().size();i++)
        {
            CL_Agent agent = _ar.getAgents().get(i);
            int dest = agent.getNextNode();
            if(dest==-1)//if its -1 it means that the agent is not moving right now
                agent.setCalculatedNextNode(nextNode(gg,agent));
        }


        boolean keepChecking=true;
        //for loop that check for each node if it had pokemon that he goes towards if not calculate next node again
        for (int i = 0; i < _ar.getAgents().size()&&keepChecking ; i++)
            for (int j = 0; j < _ar.getAgents().size(); j++)
            {
                keepChecking = false;
                if (_ar.getAgents().get(j).getMyPokemon() == null )//no going towards any pokemon so calculate again this can happen when other agent is closer to the pokemon
                {
                    _ar.getAgents().get(j).setCalculatedNextNode(nextNode(gg, _ar.getAgents().get(j)));
                    keepChecking = true;//need to check again (if this agent steal other agent pokemon)
                }

            }

        for(int i=0;i<_ar.getAgents().size();i++)
        {
            CL_Agent agent = _ar.getAgents().get(i);
            int id = agent.getID();
            int dest = agent.getNextNode();
            double v = agent.getValue();
            if(dest == -1)//this agent can move now
            {
                if(agent.getCalculatedNextNode() != -1)//if its -1 means that this agent dont have pokemon to go towards so no need to move him
                {
                    game.chooseNextEdge(agent.getID(), agent.getCalculatedNextNode());
                    System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + agent.getCalculatedNextNode());
                }
            }
        }
    }
    /**
     *gets agent and graph and return the next node the agent should go to based on distance speed and value
     * @param g
     *@param Agent
     * @return
     */
    private static int nextNode(directed_weighted_graph g,CL_Agent Agent)
    {

        dw_graph_algorithms algo = new DWGraph_Algo(g);


        //if this agent is already on the right edge just need to move to dest to eat the pokemon
        if (Agent.getMyPokemon() != null && Agent.getSrcNode() == Agent.getMyPokemon().get_edge().getSrc())
        {
            int dest=Agent.getMyPokemon().get_edge().getDest();
            Agent.getMyPokemon().setAgent(null,Double.MAX_VALUE);
            return dest;
        }
            double dist;
            double temp = Double.MAX_VALUE;
            //for loop that calculate the distance of this agent from all the pokemons and save inside the agent the closets pokemon that is not taken by other agent
            for (CL_Pokemon pokemon : _ar.getPokemons())
            {
//                    Arena.updateEdge(pokemon, g);
                    dist=dist = (algo.shortestPathDist(Agent.getSrcNode(), pokemon.get_edge().getSrc()))/(Agent.getSpeed()*pokemon.getValue());
                    //dist = (algo.shortestPathDist(Agent.getSrcNode(), pokemon.get_edge().getSrc()))/Agent.getSpeed();
                    //if the current pokemon is closer to the agent and also there no other agent that is closest to the pokemon
                    if ( dist < temp && dist != -1 && dist < pokemon.getDist())
                    {
                        temp = dist;//update the the minimum distance
                        Agent.setMyPokemon(pokemon);//set this pokemon in the agent
                    }
            }
            //if the agent successfully found a pokemon
            if (Agent.getMyPokemon() != null)
            {
                //if this pokemon already have an agent that going towards him set this agent pokemon to null (because this agent is closer)
                if(Agent.getMyPokemon().getMyAgent() != null&&Agent.getMyPokemon().getMyAgent().getID() != Agent.getID())
                {
                    Agent.getMyPokemon().getMyAgent().setOld(null);
                    Agent.getMyPokemon().getMyAgent().setMyPokemon(null);
                }
                //if the old pokemon of this agent is different then the new one i want to put the old pokemon distance to infinity (so agents can go to him)
                if (Agent.getOld() != null &&!Agent.getMyPokemon().equals(Agent.getOld()))
                {
                    Agent.getOld().setAgent(null, Double.MAX_VALUE);
                }

                Agent.getMyPokemon().setAgent(Agent, temp);//tells the pokemon that this agent coming towards him and update the distance
                Agent.setOld(Agent.getMyPokemon());//update old to the new pokemon

            }
            //if this agent is already on this pokemon edge so just need to go towards dest
            if (Agent.getMyPokemon() != null && Agent.getSrcNode() == Agent.getMyPokemon().get_edge().getSrc())
            {
                int dest = Agent.getMyPokemon().get_edge().getDest();
                Agent.getMyPokemon().setAgent(null,Double.MAX_VALUE);//if for some reason the pokemon will not be eaten update its distance
                return dest;
            }
            //if this agent have pokemon so just move towards him on the shortest path
            if (Agent.getMyPokemon() != null)
            {
                List<node_data> path = algo.shortestPath(Agent.getSrcNode(), Agent.getMyPokemon().get_edge().getSrc());

                return path.get(1).getKey();
            }

            //if this agent didnt find any pokemon so return  so check if its inside one of the pokemons
        for (CL_Pokemon pokemon : _ar.getPokemons())
            if(pokemon.getMyAgent().getID() == Agent.getID())
            return algo.shortestPath(Agent.getSrcNode(),pokemon.get_edge().getSrc()).get(1).getKey();
            //didnt find pokemon return -1
            return -1;



    }

    /**
     * A function that creates the graphical interface
     * @param game
     */
    private void init(game_service game) {
        String g = game.getGraph();
        String fs = game.getPokemons();

        directed_weighted_graph gg = buildGraphFromJason(g);
        _ar = new Arena();
        _ar.setGraph(gg);
        _ar.setPokemons(Arena.json2Pokemons(fs));
        _win = new MyFrame("test Ex2",_ar);
        _win.setSize(1000, 700);
        _win.show();
        _win.setVisible(true);
        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int numberOfAgents = ttt.getInt("agents");
            System.out.println(info);
            System.out.println(game.getPokemons());
            ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
            for(int a = 0;a<cl_fs.size();a++)
                Arena.updateEdge(cl_fs.get(a), gg);
            List<CL_Pokemon>pokemonList=_ar.getPokemons();
            PriorityQueue<CL_Pokemon>pq=new PriorityQueue<>();
            //add all the pokemons to a PQ order by value (the first to go out is the highest)
            for (int i = 0; i < pokemonList.size() ; i++)
            {
                pq.add(pokemonList.get(i));
            }
            //put the agents near the pokemons with the high values
            for (int i = 0; i < numberOfAgents ; i++)
            {
                if(pq.peek() != null && !pq.isEmpty())
                {
                    Arena.updateEdge(pq.peek(), gg);
                    game.addAgent(pq.poll().get_edge().getSrc());
                }
                else//if the PQ is empty put the on the index
                    game.addAgent(i);
            }

        }
        catch (JSONException e) {e.printStackTrace();}
    }
    public directed_weighted_graph buildGraphFromJason(String g)//function that get build graph from a json that received from the server
    {
        GsonBuilder builder=new GsonBuilder();
        builder.registerTypeAdapter(directed_weighted_graph.class, new GraphJson());
        Gson json = builder.create();
        directed_weighted_graph graph= json.fromJson(g,directed_weighted_graph.class);
        return graph;
    }
}