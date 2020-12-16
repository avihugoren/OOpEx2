package api;

import api.node_data;

public class NodeAlgo implements Comparable<NodeAlgo> {
    private int key;
    private double weight;
    private node_data parent;

    /**
     * constructor
     * @param key,weight,parent
     */
    public NodeAlgo(int key,double weight,node_data parent)
    {
        this.key=key;
        this.weight=weight;
        this.parent=parent;
    }

    /**
     * return the key of this NodeAlgo
     * @return
     */
    public int getKey()
    {
        return this.key;
    }

    /**
     * return the weight of this NodeAlgo
     * @return
     */
    public double getWeight()
    {
        return this.weight;
    }
    /**
     * return the parent of this NodeAlgo
     * @return
     */
    public node_data getParent()
    {
        return this.parent;
    }

    /**
     * set the weight
     * @param newWeight
     * @return
     */
    public void setWeight(double newWeight)
    {
        this.weight=newWeight;
    }

    /**
     * set the parent
     * @param newParent
     * @return
     */
    public void setParent(node_data newParent)
    {
        this.parent=newParent;
    }

    /**
     * comper the 2 NodeAlgo by the weight
     * @param o
     * @return
     */
    @Override
    public int compareTo(NodeAlgo o) {
        if(this.weight>o.getWeight())
            return 1;
        else if(this.weight==o.getWeight())
            return 0;
        return -1;
    }
}
