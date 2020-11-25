package api;

import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph
{
    private class NodeData implements node_data
    {
        int key;
        double weight;
        String info;
        int tag;

        @Override
        public int getKey()
        {
            return key;
        }

        @Override
        public geo_location getLocation()
        {
            return null;
        }

        @Override
        public void setLocation(geo_location p)
        {

        }

        @Override
        public double getWeight()
        {
            return weight;
        }

        @Override
        public void setWeight(double w)
        {
            weight=w;

        }

        @Override
        public String getInfo() {
            return info;
        }

        @Override
        public void setInfo(String s) {
            info=s;

        }

        @Override
        public int getTag() {
            return tag;
        }

        @Override
        public void setTag(int t)
        {
            tag=t;
        }
    }
    HashMap<Integer,node_data> nodeHash=new HashMap<>();
    HashMap<Integer,HashMap<Integer,edge_data>>edgeHash=new HashMap<>();
    @Override
    public node_data getNode(int key)
    {
        return nodeHash.get(key);
    }

    @Override
    public edge_data getEdge(int src, int dest) {
        return null;
    }

    @Override
    public void addNode(node_data n)
    {
        nodeHash.put(n.getKey(),n);
        edgeHash.put(n.getKey(),new HashMap<Integer,edge_data>());
    }

    @Override
    public void connect(int src, int dest, double w)
    {
        if(w<0)
            return;
        if(!nodeHash.containsKey(src)||!nodeHash.containsKey(dest))
            return;
        

    }

    @Override
    public Collection<node_data> getV() {
        return null;
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        return null;
    }

    @Override
    public node_data removeNode(int key) {
        return null;
    }

    @Override
    public edge_data removeEdge(int src, int dest) {
        return null;
    }

    @Override
    public int nodeSize() {
        return 0;
    }

    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public int getMC() {
        return 0;
    }
}
