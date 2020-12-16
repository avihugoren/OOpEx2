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
	private double min_dist;
	private int min_ro;
	private double dist=Double.MAX_VALUE;//field that represent that distance from the closest pokemon
	private CL_Agent myAgent;
	 public double getDist()
	{
		return dist;
	}

	public void  setDist(double distance)
	{
		this.dist=distance;
	}

     //function that get agent and its distance from the Pokemon
	 public void setAgent(CL_Agent agent,double dist2)
	{
		this.myAgent= agent;
		this.dist=dist2;
	}
	   public CL_Agent getMyAgent()
	{
		return myAgent;
	}
	public CL_Pokemon(Point3D p, int t, double v, double s, edge_data e) {
		_type = t;
	//	_speed = s;
		_value = v;
		set_edge(e);
		_pos = p;
		min_dist = -1;
		min_ro = -1;
	}
	public static CL_Pokemon init_from_json(String json) {
		CL_Pokemon ans = null;
		try {
			JSONObject p = new JSONObject(json);
			int id = p.getInt("id");

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ans;
	}
	public String toString() {return "F:{v="+_value+", t="+_type+"}";}
	public edge_data get_edge() {
		return _edge;
	}

	public void set_edge(edge_data _edge) {
		this._edge = _edge;
	}

	public Point3D getLocation() {
		return _pos;
	}
	public int getType() {return _type;}
//	public double getSpeed() {return _speed;}
	public double getValue() {return _value;}

	public double getMin_dist() {
		return min_dist;
	}

	public void setMin_dist(double mid_dist) {
		this.min_dist = mid_dist;
	}

	public int getMin_ro() {
		return min_ro;
	}

	public void setMin_ro(int min_ro) {
		this.min_ro = min_ro;
	}

	//function that equal pokemons by there geo location
	@Override
	public boolean equals(Object o) {
//		if (!(o instanceof CL_Pokemon))
//			return false;
		CL_Pokemon g = (CL_Pokemon) o;

		if (!g.getLocation().equals(this.getLocation()))//equal pokemons by location
		{
			return false;
		}

		return true;
	}
	@Override
	public int compareTo( CL_Pokemon o)
	{
		if(this._value>o.getValue())
			return -1;
		else if(this._value==o.getValue())
			return 0;
		return 1;
	}
	//function that gets pokemon and update this pokemon with the other pokemon extra fields
	public void update(CL_Pokemon other)
	{
		this.dist=other.getDist();
		this.myAgent=other.getMyAgent();
	}
}
