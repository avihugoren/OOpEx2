package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a multi Agents Arena which move on a graph - grabs Pokemons .
 * @author boaz.benmoshe
 *
 */
public class Arena
{
	public static final double EPS1 = 0.001, EPS2=EPS1*EPS1, EPS=EPS2;
	private directed_weighted_graph _gg;
	private List<CL_Agent> _agents;
	private List<CL_Pokemon> _pokemons;
	private static Point3D MIN = new Point3D(0, 100,0);
	private static Point3D MAX = new Point3D(0, 100,0);

	/**
	 * empty constructor
	 */
	public Arena()
	{
	}

	/**
	 * get pokemon list if there already inside the arena update them with the extra fields that are not in the server
	 * else just add them to the arena list of pokemons
	 * @param pokemonList
	 */
	public void setPokemons(List<CL_Pokemon> pokemonList)
	{
		//the first set of pokemons
		if(_pokemons==null)
		{
			this._pokemons = pokemonList;
			return;

		}

		//for loop that checks if the pokemons that comes from the server are already on the graph if yes its add them the fields that i created
		for (int i = 0; i <pokemonList.size() ; i++)
			for (int j = 0; j < _pokemons.size(); j++)
				if (pokemonList.get(i).equals(_pokemons.get(j))) //equal the Pokemon's by geo location
				{
					pokemonList.get(i).update(_pokemons.get(j));//if equal update the fields
					break;
				}
		this._pokemons=pokemonList;//update the Pokemon's list to the new list
	}
	/**
	 * get list of agent and json string of the agent from the server
	 * update each agent with the information from the server
	 * @param agentList
	 * @param lg
	 */
	public void setAgents(List<CL_Agent> agentList,String lg) throws JSONException
	{
		//its the first time that the Agents comes from the server
		if(_agents == null)
		{
			this._agents = agentList;
			return;
		}
		JSONObject line;
		line = new JSONObject(lg);//holds all the Agents
		int id;
		JSONObject agent;
		//json array of the Agents
		JSONArray jsonArray=line.getJSONArray("Agents");//hold json array of the Agents
		//for each Agent in the Array update its information by its id
		for (int i = 0; i < jsonArray.length(); i++)
		{
		  	agent= (JSONObject) jsonArray.get(i);
		  	JSONObject tempAgent=agent.getJSONObject("Agent");
		  	tempAgent=agent.getJSONObject("Agent");//hold the information of one of the agents
		  	id=tempAgent.getInt("id");

		  	//for loop that find the agent that the json belong to by id and update its information
			for (int j = 0; j < _agents.size() ; j++)
				if (_agents.get(j).getID() == id) {
					_agents.get(j).update(agent.toString());//update the information of the agent
					break;
				}
		}

	}

	/**
	 * get graph set it in the arena
	 * @param g
	 */
	public void setGraph(directed_weighted_graph g) {this._gg =g;}//init();}
	private void init( )
	{
		MIN=null; MAX=null;
		double x0=0,x1=0,y0=0,y1=0;
		Iterator<node_data> iter = _gg.getV().iterator();
		while(iter.hasNext())
		{
			geo_location c = iter.next().getLocation();
			if(MIN==null) {x0 = c.x(); y0=c.y(); x1=x0;y1=y0;MIN = new Point3D(x0,y0);}
			if(c.x() < x0) {x0=c.x();}
			if(c.y() < y0) {y0=c.y();}
			if(c.x() > x1) {x1=c.x();}
			if(c.y() > y1) {y1=c.y();}
		}
		double dx = x1-x0, dy = y1-y0;
		MIN = new Point3D(x0-dx/10,y0-dy/10);
		MAX = new Point3D(x1+dx/10,y1+dy/10);
		
	}
	/**
	 * return list of the agent in the arena
	 * @return
	 */
	public List<CL_Agent> getAgents()
	{
		return _agents;
	}
	/**
	 * return list of the pokemons in the arena
	 * @return
	 */
	public List<CL_Pokemon> getPokemons()
	{
		return _pokemons;
	}
	/**
	 * return the graph
	 * @return
	 */
	public directed_weighted_graph getGraph()
	{
		return _gg;
	}

	/**
	 * return list of the agent taken from a json string
	 * @param jsonString
	 * @param gg
	 * @return
	 */
	public static List<CL_Agent> getAgents(String jsonString, directed_weighted_graph gg)
	{
		ArrayList<CL_Agent> ans = new ArrayList<CL_Agent>();
		try {
			JSONObject ttt = new JSONObject(jsonString);
			JSONArray ags = ttt.getJSONArray("Agents");
			for(int i = 0; i < ags.length() ; i++)
			{
				CL_Agent c = new CL_Agent(gg,0);
				c.update(ags.get(i).toString());
				ans.add(c);
			}
			//= getJSONArray("Agents");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ans;
	}
	/**
	 * return list of the poemons taken from a json string
	 * @param fs
	 * @return
	 */
	public static ArrayList<CL_Pokemon> json2Pokemons(String fs) {
		ArrayList<CL_Pokemon> ans = new  ArrayList<CL_Pokemon>();
		try {
			JSONObject ttt = new JSONObject(fs);
			JSONArray ags = ttt.getJSONArray("Pokemons");
			for(int i=0;i<ags.length();i++)
			{
				JSONObject pp = ags.getJSONObject(i);
				JSONObject pk = pp.getJSONObject("Pokemon");
				int t = pk.getInt("type");
				double v = pk.getDouble("value");
				String p = pk.getString("pos");
				CL_Pokemon f = new CL_Pokemon(new Point3D(p), t, v,  null);
				ans.add(f);
			}
		}
		catch (JSONException e) {e.printStackTrace();}
		return ans;
	}
	/**
	 * get pokemon and graph and update the edge of the pokemon on the garph
	 * @param pokemon
	 * @param g
	 */
	public static void updateEdge(CL_Pokemon pokemon, directed_weighted_graph g)
	{
		Iterator<node_data> itr = g.getV().iterator();
		while(itr.hasNext())
		{
			node_data v = itr.next();
			Iterator<edge_data> iter = g.getE(v.getKey()).iterator();
			while(iter.hasNext())
			{
				edge_data e = iter.next();
				boolean f = isOnEdge(pokemon.getLocation(), e,pokemon.getType(), g);
				if(f) {pokemon.set_edge(e);}
			}
		}
	}

	/**
	 * get geo location p and get location of src and dest nodes
	 * calculate if  p is on the edge between src and dest return true false otherwise
	 * @param p
	 * @param src
	 * @param dest
	 * @return
	 */
	private static boolean isOnEdge(geo_location p, geo_location src, geo_location dest )
	{

		boolean ans = false;
		double dist = src.distance(dest);
		double d1 = src.distance(p) + p.distance(dest);
		if(dist>d1-EPS2) {ans = true;}
		return ans;
	}
	/**
	 * get geo location p and 2 keys of nodes and a graph
	 * return true if p is on the edge between s and d false otherwise
	 * @param p
	 * @param s
	 * @param d
	 * @param g
	 * @return
	 */
	private static boolean isOnEdge(geo_location p, int s, int d, directed_weighted_graph g) {
		geo_location src = g.getNode(s).getLocation();
		geo_location dest = g.getNode(d).getLocation();
		return isOnEdge(p,src,dest);
	}
	/**
	 * get geo location p and edge e type and directed graph g
	 *  check if the type of the pokemon suitable for the edge
	 *  if type is -1 dest needs to be smaller then src
	 *  if type is 1 dest needs to be bigger then src
	 * if type is suitable calculate if  p is on the edge between src and dest
	 * @param p
	 * @param e
	 * @param type
	 * @param g
	 * @return
	 */
	private static boolean isOnEdge(geo_location p, edge_data e, int type, directed_weighted_graph g) {
		int src = g.getNode(e.getSrc()).getKey();
		int dest = g.getNode(e.getDest()).getKey();
		if(type<0 && dest>src)
		{return false;}
		if(type>0 && src>dest)
		{return false;}
		return isOnEdge(p,src, dest, g);
	}

	/**
	 * return the range of the graph
	 * @param g
	 * @return
	 */
	private static Range2D GraphRange(directed_weighted_graph g) {
		Iterator<node_data> itr = g.getV().iterator();
		double x0=0,x1=0,y0=0,y1=0;
		boolean first = true;
		//go over all the node and found the min x and y ant the max of x and y
		while(itr.hasNext()) {
			geo_location p = itr.next().getLocation();
			if(first) {
				x0=p.x(); x1=x0;
				y0=p.y(); y1=y0;
				first = false;
			}
			else {
				if(p.x()<x0) {x0=p.x();}
				if(p.x()>x1) {x1=p.x();}
				if(p.y()<y0) {y0=p.y();}
				if(p.y()>y1) {y1=p.y();}
			}
		}
		Range xr = new Range(x0,x1);
		Range yr = new Range(y0,y1);
		return new Range2D(xr,yr);
	}

	/**
	 * Gets graph and frame and adjusts the graph to frame
	 * @param g
	 * @param frame
	 * @return
	 */
	public static Range2Range w2f(directed_weighted_graph g, Range2D frame) {
		Range2D world = GraphRange(g);
		Range2Range ans = new Range2Range(world, frame);
		return ans;
	}

}
