package api;

import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph
{


    HashMap<Integer,node_data> nodeHash=new HashMap<>();
    HashMap<Integer,HashMap<Integer,edge_data>>edgeHashOut=new HashMap<>();
    HashMap<Integer,HashMap<Integer,Integer>>edgeHashIn=new HashMap<>();
    int edges;
    int modeCount;
    public DWGraph_DS(directed_weighted_graph graph)
    {
        for (node_data node : graph.getV()) {
            nodeHash.put(node.getKey(), new NodeData(node));
            edgeHashOut.put(node.getKey(), new HashMap<Integer, edge_data>());
            edgeHashIn.put(node.getKey(), new HashMap<Integer, Integer>());
        }
        for (node_data node : graph.getV()) {
            for (edge_data edge : graph.getE(node.getKey())) {
                edgeHashOut.get(node.getKey()).put(edge.getDest(), new EdgeData(edge));
                edgeHashIn.get(edge.getDest()).put(node.getKey(), node.getKey());
            }
        }
        edges = graph.edgeSize();
        int modeCount = graph.getMC();
    }
    public DWGraph_DS()
    {

    }
    @Override
    public node_data getNode(int key)
    {
        return nodeHash.get(key);
    }

    @Override
    public edge_data getEdge(int src, int dest)
    {
        if(!edgeHashOut.containsKey(src))
            return null;
        return edgeHashOut.get(src).get(dest);
    }

    @Override
    public void addNode(node_data n)
    {
        if(!nodeHash.containsKey(n.getKey()))
        nodeHash.put(n.getKey(),n);
        edgeHashOut.put(n.getKey(),new HashMap<Integer,edge_data>());
        edgeHashIn.put(n.getKey(),new HashMap<Integer,Integer>());
        modeCount++;
    }

    @Override
    public void connect(int src, int dest, double w)
    {
        if(w<0)
            return;
        if(src == dest)
            return;
        if(!nodeHash.containsKey(src)||!nodeHash.containsKey(dest))
            return;
        edge_data tempEdge=getEdge(src,dest);
        if (tempEdge != null)
        {
            if (w == tempEdge.getWeight())
                return;
            edges--;
        }
        edgeHashOut.get(src).put(dest,new EdgeData(src,dest,w));
        edgeHashIn.get(dest).put(src,src);
        edges++;
        modeCount++;
    }

    @Override
    public Collection<node_data> getV()
    {
       return this.nodeHash.values();
    }

    @Override
    public Collection<edge_data> getE(int node_id)
    {
        if(edgeHashOut.containsKey(node_id))
        return edgeHashOut.get(node_id).values();
        return null;
    }

    @Override
    public node_data removeNode(int key)
    {
        if(this.getNode(key) == null)
            return null;
        for(Integer inte :edgeHashIn.get(key).keySet())
        {
            edgeHashOut.get(inte).remove(key);
            edges--;
            modeCount++;
        }
        for(Integer inte :edgeHashOut.get(key).keySet())
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

    @Override
    public edge_data removeEdge(int src, int dest)
    {
        if(!nodeHash.containsKey(src))
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

    @Override
    public int nodeSize()
    {
        return nodeHash.size();
    }

    @Override
    public int edgeSize()
    {
        return edges;
    }

    @Override
    public int getMC()
    {
        return modeCount;
    }

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
        StringBuilder s=new StringBuilder();
        for (node_data node:nodeHash.values())
            s.append(node+"\n");
        for(Integer Inte:edgeHashOut.keySet())
            for (edge_data edge:getE(Inte))
                s.append(edge+"\n");
            return s.toString();

    }
}
