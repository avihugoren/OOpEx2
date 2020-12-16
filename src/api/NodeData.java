package api;
public class NodeData implements node_data {
    int key;
    double weight;
    String info;
    int tag;
    geo_location geo;

    /**
     * copy constructor
     * @param n
     */
    public NodeData(node_data n)
    {
        this.key=n.getKey();
        this.weight=n.getWeight();
        this.info=n.getInfo();
        this.tag=n.getTag();
    }

    /**
     * constructor that get key
     * @param key
     */
    public NodeData(int key) {
        this.key = key;
        this.geo=null;
    }

    /**
     * Returns the key (id) associated with this node
     * @return
     */
    @Override
    public int getKey() {
        return key;
    }

    /**
     * Returns the location of this node, if none return null.
     * @return
     */
    @Override
    public geo_location getLocation() {
        return geo;
    }

    /**
     * Allows changing this node's location.
     * @param p - new new location  (position) of this node.
     */
    @Override
    public void setLocation(geo_location p) {
        this.geo=p;
    }

    /**
     * Returns the weight associated with this node.
     * @return
     */
    @Override
    public double getWeight() {
        return weight;
    }

    /**
     * allows changing this node's weight.
     * @param w - the new weight
     */
    @Override
    public void setWeight(double w) {
        weight = w;

    }

    /**
     * Returns the remark (meta data) associated with this node
     * @return
     */

    @Override
    public String getInfo() {
        return info;
    }

    /**
     * Allows changing the remark (meta data) associated with this node
     * @param s
     */
    @Override
    public void setInfo(String s) {
        info = s;

    }

    /**
     * return the tag of the node
     * @return
     */
    @Override
    public int getTag() {
        return tag;
    }

    /**
     * allows setting the "tag" value for temporal marking an node - common practice for marking by algorithms.
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        tag = t;
    }

    /**
     * check if the 2 nodes are equals
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.key == ((NodeData) o).key;

    }

    /**
     * return string with the node key
     * @return
     */
    @Override
    public String toString()
    {
        return "node key : " + key;
    }
}

