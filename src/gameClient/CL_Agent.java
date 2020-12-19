package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class CL_Agent  {
		public static final double EPS = 0.0001;
		private int _id;
		private geo_location _pos;
		private double _speed;
		private edge_data _curr_edge;
		private node_data _curr_node;
		private directed_weighted_graph _gg;
	    private CL_Pokemon oldPokemon=null;
		private CL_Pokemon myPokemon=null;
		private int calculatedNextNode;
	    private double _value;

	public CL_Agent(directed_weighted_graph g, int start_node) {
		_gg = g;
		setMoney(0);
		this._curr_node = _gg.getNode(start_node);
		_pos = _curr_node.getLocation();
		_id = -1;
		setSpeed(0);
	}

	/**
	 * get node key and set it as nextNode of this agent
	 * @param node
	 */
		public void setCalculatedNextNode(int node)
		{
			calculatedNextNode=node;
		}
	/**
	 * return the next node key this agent going to
	 * @return
	 */
		public int getCalculatedNextNode()
		{
			return calculatedNextNode;
		}


	/**
	 * get pokemon and set this as myPokemon
	 * @param pokemon
	 */
	 public void setMyPokemon(CL_Pokemon pokemon)
		{
			myPokemon=pokemon;
		}
	/**
	 * return the myPokemon
	 * @return
	 */
	public CL_Pokemon getMyPokemon()
		{
			return myPokemon;
		}


	/**
	 * update the agent according to the data in the string
	 * @param json
	 */
		public void update(String json)
		{
			JSONObject line;
			try {
				line = new JSONObject(json);
				JSONObject ttt = line.getJSONObject("Agent");
				int id = ttt.getInt("id");
				if(id==this.getID() || this.getID() == -1)
				{
					if(this.getID() == -1)
					{_id = id;}
					double speed = ttt.getDouble("speed");
					String p = ttt.getString("pos");
					Point3D pp = new Point3D(p);
					int src = ttt.getInt("src");
					int dest = ttt.getInt("dest");
					double value = ttt.getDouble("value");
					this._pos = pp;
					this.setCurrNode(src);
					this.setSpeed(speed);
					this.setNextNode(dest);
					this.setMoney(value);
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	/**
	 * return the key of the node that the agent is on
	 * @return
	 */
		public int getSrcNode()
		{
			return this._curr_node.getKey();
		}
	/**
	 * set the agent value
	 * @param v
	 */
		private void setMoney(double v) {_value = v;}

	/**
	 * return true if and only if the agent in not moving on a edge right now
	 * @param dest
	 */
		public boolean setNextNode(int dest)
		{
			boolean ans = false;
			int src = this._curr_node.getKey();
			this._curr_edge = _gg.getEdge(src, dest);
			if(_curr_edge!=null) {
				ans=true;
			}
			else {_curr_edge = null;}
			return ans;
		}
	/**
	 * set the node of the agent
	 * @param src
	 */
		public void setCurrNode(int src)
		{
			this._curr_node = _gg.getNode(src);
		}

	/**
	 * return the id of the agent
	 * @return
	 */
		public int getID() {

			return this._id;
		}

	/**
	 * return the geo location of the agent
	 * @return
	 */
		public geo_location getLocation()
		{
			return _pos;
		}


	/**
	 * return the value of the agent
	 * @return
	 */
		public double getValue()
		{
			return this._value;
		}
	/**
	 * return the dest of the agent if none return -1
	 * @return
	 */
		public int getNextNode()
		{
			int ans = -2;
			if(this._curr_edge==null) {
				ans = -1;}
			else {
				ans = this._curr_edge.getDest();
			}
			return ans;
		}

	/**
	 * return the speed of the agent
	 * @return
	 */
		public double getSpeed() {
			return this._speed;
		}

	/**
	 * set the speed of the agent
	 * @param v
	 */
		public void setSpeed(double v) {
			this._speed = v;
		}


	/**
	 * return the agent old pokemon
	 * @return
	 */
	public CL_Pokemon getOld() {
		return oldPokemon;
	}
	/**
	 * set old pokemon
	 * @param old
	 */
	public void setOld (CL_Pokemon old)
	{
		this.oldPokemon=old;
	}

}
