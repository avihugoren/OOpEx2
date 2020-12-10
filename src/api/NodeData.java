package api;
public class NodeData implements node_data {
    int key;
    double weight;
    String info;
    int tag;
    geo_location geo=new GeoLocation(0,0,0);
    public NodeData(node_data n)
    {
        this.key=n.getKey();
        this.weight=n.getWeight();
        this.info=n.getInfo();
        this.tag=n.getTag();
    }

    public NodeData(int key) {
        this.key = key;
    }

    @Override
    public int getKey() {
        return key;
    }

    @Override
    public geo_location getLocation() {
        return geo;
    }

    @Override
    public void setLocation(geo_location p) {
        this.geo=p;


    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public void setWeight(double w) {
        weight = w;

    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String s) {
        info = s;

    }

    @Override
    public int getTag() {
        return tag;
    }

    @Override
    public void setTag(int t) {
        tag = t;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.key == ((NodeData) o).key;

    }
    @Override
    public String toString()
    {
        return "node key : " + key;
    }
}

