package gameClient;

import api.DWGraph_DS;
import api.edge_data;
import api.node_data;
import gameClient.util.Point3D;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class CL_Pokemon implements Comparable<CL_Pokemon>{
	private edge_data _edge;
	private double _value;
	private int _type;
	private Point3D _pos;
	private double dist=Double.MAX_VALUE;//field that represent that distance from the closest pokemon
	private CL_Agent myAgent;
	/**
	 * return the distance/agentspeed*value(of this pokemon) of the agent that coming towards this pokemon infinity if none
	 * @return
	 */
	 public double getDist()
	{
		return dist;
	}

	/**
	 * set the distance of this pokemon
	 * @param distance
	 */
	public void  setDist(double distance)
	{
		this.dist=distance;
	}

	/**
	 * set the distance of this pokemon for the agent
	 * @param agent
	 * @param dist2
	 */
	 public void setAgent(CL_Agent agent,double dist2)
	{
		this.myAgent= agent;
		this.dist=dist2;
	}

	/**
	 * return the agent that comeing towards this pokemon
	 * @return
	 */
	   public CL_Agent getMyAgent()
	{
		return myAgent;
	}
	/**
	 * constructor for pokemon
	 * @param p
	 * @param t
	 * @param v
	 * @param e
	 */
	public CL_Pokemon(Point3D p, int t, double v,  edge_data e) {
		_type = t;
		_value = v;
		set_edge(e);
		_pos = p;
	}
	/**
	 * return string of the pokemon
	 * @return
	 */
	public String toString() {return "F:{v="+_value+", t="+_type+"}";}
	/**
	 * return the edge of the pokemon
	 * @return
	 */
	public edge_data get_edge() {
		return _edge;
	}

	/**
	 * set the Pokemon on the edge
	 * @param _edge
	 */
	public void set_edge(edge_data _edge) {
		this._edge = _edge;
	}

	/**
	 * return the pokemon geo location
	 * @return
	 */
	public Point3D getLocation() {
		return _pos;
	}
	/**
	 * return the type of the pokemon
	 * @return
	 */
	public int getType() {return _type;}
	/**
	 * return the  value of the pokemon
	 * @return
	 */
	public double getValue() {return _value;}

	/**
	 * return true if 2 pokemon equal check that with their geo location false if otherwise
	 * @param o
	 * @return
	 */
	@Override
	public boolean equals(Object o) {
		CL_Pokemon g = (CL_Pokemon) o;

		if (!g.getLocation().equals(this.getLocation()))//equal pokemons by location
		{
			return false;
		}

		return true;
	}
	/**
	 * compare 2 pokemons according to their value return -1 if this is bigger that o
	 *  return 0 if equal
	 *  1 other wise
	 * @return
	 */
	@Override
	public int compareTo( CL_Pokemon o)
	{
		if(this._value>o.getValue())
			return -1;
		else if(this._value==o.getValue())
			return 0;
		return 1;
	}
	/**
	 * get other pokemon and set this dist and myAgent to other values
	 * @param other
	 */
	public void update(CL_Pokemon other)
	{
		this.dist=other.getDist();
		this.myAgent=other.getMyAgent();
	}
}
