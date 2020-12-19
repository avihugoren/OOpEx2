package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph
{


    HashMap<Integer,node_data> nodeHash=new HashMap<>();//al the node in the graph
    HashMap<Integer,HashMap<Integer,edge_data>>edgeHashOut=new HashMap<>();//save the edges that go out from each node
    HashMap<Integer,HashMap<Integer,Integer>>edgeHashIn=new HashMap<>();////save the key of edges that each node is the dest of the edges
    int edges;
    int modeCount;

    /**
     * constructor
     */
    public DWGraph_DS()
    {

    }
    /**
     * copy constructor
     * @param graph
     */
    public DWGraph_DS(directed_weighted_graph graph)
    {
        //add all the nodes to the nodeHash,edgeHashOut,edgeHashIn
        for (node_data node : graph.getV()) {
            nodeHash.put(node.getKey(), new NodeData(node));//do deep copy for each node of graph and add it to the list of nodes of the new graph
            edgeHashOut.put(node.getKey(), new HashMap<Integer, edge_data>());
            edgeHashIn.put(node.getKey(), new HashMap<Integer, Integer>());
        }
        for (node_data node : graph.getV()) {
            for (edge_data edge : graph.getE(node.getKey())) {
                edgeHashOut.get(node.getKey()).put(edge.getDest(), new EdgeData(edge));//do deep copy for each edge and add it to edgeHashOut
                edgeHashIn.get(edge.getDest()).put(node.getKey(), node.getKey());
            }
        }
        edges = graph.edgeSize();//Update the number of edges in the new graph
        int modeCount = graph.getMC();////Update the number of Mc in the new graph
    }

    /**
     * returns the node_data by the node_id
     * @param key - the node_id
     * @return node_data null if none.
     */
    @Override
    public node_data getNode(int key)
    {
        return nodeHash.get(key);
    }

    /**
     * returns the data of the edge (src,dest), null if none.
     * @param src
     * @param dest
     * @return edge_data null if none
     */
    @Override
    public edge_data getEdge(int src, int dest)
    {
        if(!edgeHashOut.containsKey(src))//if the edge does not exist
            return null;
        return edgeHashOut.get(src).get(dest);
    }

    /**
     * adds a new node to the graph with the given node_data.
     * @param n
     */
    @Override
    public void addNode(node_data n)
    {
        if(n==null)
            return;
        if(nodeHash.containsKey(n.getKey()))//if the node is already exists do nothing
            return;
        //add the new node to nodeHash,edgeHashOut and edgeHashIn
        nodeHash.put(n.getKey(),n);
        edgeHashOut.put(n.getKey(),new HashMap<Integer,edge_data>());
        edgeHashIn.put(n.getKey(),new HashMap<Integer,Integer>());
        modeCount++;
    }

    /**
     *
     * @param src - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w)
    {
        if(w<0)//weight can not be negative
            return;
        if(src == dest)//can not make edge between a node and itself
            return;
        if(!nodeHash.containsKey(src)||!nodeHash.containsKey(dest))//if src or dest is not in the graph do nothing
            return;
        edge_data tempEdge=getEdge(src,dest);
        if (tempEdge != null)//if the edge is already exists
        {
            if (w == tempEdge.getWeight())//If the weight we received is the same as the existing weight
                return;
            edges--;//If we need to update the weight of the edge
        }
        edgeHashOut.get(src).put(dest,new EdgeData(src,dest,w));
        edgeHashIn.get(dest).put(src,src);
        edges++;
        modeCount++;
    }

    /**
     * This method returns a pointer (shallow copy) for the collection representing all the nodes in the graph.
     * @return
     */
    @Override
    public Collection<node_data> getV()
    {
       return this.nodeHash.values();
    }

    /**
     * This method returns a pointer (shallow copy) for the collection representing all the edges getting out of
     *the given node (all the edges starting (source) at the given node).
     * @param node_id
     */
    @Override
    public Collection<edge_data> getE(int node_id)
    {
        if(edgeHashOut.containsKey(node_id))
             return edgeHashOut.get(node_id).values();
        return null;//if the node not in the graph
    }

    /**
     * Deletes the node (with the given ID) from the graph and removes all edges which starts or ends at this node.
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_data removeNode(int key)
    {
        if(this.getNode(key) == null)//if the node is not in the graph
            return null;
        for(Integer inte :edgeHashIn.get(key).keySet())//remove all the edges that the node with key is the src of the edge
        {
            edgeHashOut.get(inte).remove(key);
            edges--;
            modeCount++;
        }
        for(Integer inte :edgeHashOut.get(key).keySet())//remove all the edges that the node with key is the dest of the edge
        {
            edgeHashIn.get(inte).remove(key);
            edges--;
            modeCount++;
        }

        edgeHashOut.remove(key);
        edgeHashIn.remove(key);
        modeCount++;
        return nodeHash.remove(key);
    }

    /**
     * Deletes the edge from the graph,
     * @param src,dest
     * @return the data of the removed edge (null if none).
     */
    @Override
    public edge_data removeEdge(int src, int dest)
    {
        if(!nodeHash.containsKey(src))//if the node of the src is not in the graph return null
            return null;
        edge_data tempEdge= edgeHashOut.get(src).remove(dest);
        if(edgeHashIn.containsKey(dest))
            edgeHashIn.get(dest).remove(src);
        if(tempEdge!=null)
        {
            edges--;
            modeCount++;
            return tempEdge;
        }
        return null;
    }

    /**
     * Returns the number of vertices (nodes) in the graph
     */
    @Override
    public int nodeSize()
    {
        return nodeHash.size();
    }

    /**
     * Returns the number of edges (assume directional graph).
     */
    @Override
    public int edgeSize()
    {
        return edges;
    }

    /**
     *  Returns the Mode Count - for testing changes in the graph
     */
    @Override
    public int getMC()
    {
        return modeCount;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DWGraph_DS))
            return false;
        DWGraph_DS g = (DWGraph_DS) o;
        if (g.nodeSize() != this.nodeSize() || g.edgeSize() != this.edgeSize())
            return false;
        for (node_data node : g.getV()) {
            for (edge_data edge : g.getE(node.getKey())) {
                if (!this.getEdge(edge.getSrc(), edge.getDest()).equals(edge))
                    return false;
            }
        }
        return true;
    }

    @Override
    public String toString()
    {
        Gson gson= new GsonBuilder().registerTypeAdapter(DWGraph_DS.class,new GraphJson()).create();
       return gson.toJson(this);

    }
}
