package api;

public class EdgeData implements edge_data
{
    int src;
    int dest;
    double w;
    String info;
    int tag;
    public EdgeData(edge_data edge)
    {
        this.src=edge.getSrc();
        this.dest=edge.getDest();
        this.w=edge.getWeight();
        this.info=edge.getInfo();
        this.tag=edge.getTag();
    }
    public EdgeData(int src,int dest,double w)
    {
        this.src=src;
        this.dest=dest;
        this.w=w;
    }

    @Override
    public int getSrc() {
        return src;
    }

    @Override
    public int getDest() {
        return dest;
    }

    @Override
    public double getWeight() {
        return w;
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
    public void setTag(int t) {
        tag=t;

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.src == ((EdgeData) o).src&&this.dest == ((EdgeData) o).dest&&this.tag == ((EdgeData) o).tag&& this.w==((EdgeData) o).w
                &&this.info==((EdgeData) o).info;

    }
}
