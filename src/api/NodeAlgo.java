package api;

import api.node_data;

public class NodeAlgo implements Comparable<NodeAlgo> {
    private int key;
    private double weight;
    private node_data parent;

    public NodeAlgo(int key,double weight,node_data parent)
    {
        this.key=key;
        this.weight=weight;
        this.parent=parent;
    }
    public int getKey()
    {
        return this.key;
    }
    public double getWeight()
    {
        return this.weight;
    }
    public node_data getParent()
    {
        return this.parent;
    }

    public double setWeight(double newWeight)
    {
        return this.weight=newWeight;
    }
    public node_data setParent(node_data newParent)
    {
        return this.parent=newParent;
    }

    @Override
    public int compareTo(NodeAlgo o) {
        if(this.weight>o.getWeight())
            return 1;
        else if(this.weight==o.getWeight())
            return 0;
        return -1;
    }
}
