package api;

public class EdgeData implements edge_data
{
    int src;
    int dest;
    double w;
    String info;
    int tag;

    /**
     * copy constructor
     * @param edge
     */
    public EdgeData(edge_data edge)
    {
        this.src=edge.getSrc();
        this.dest=edge.getDest();
        this.w=edge.getWeight();
        this.info=edge.getInfo();
        this.tag=edge.getTag();
    }

    /**
     * constructor
     * @param src
     * @param dest
     * @param w
     */
    public EdgeData(int src,int dest,double w)
    {
        this.src=src;
        this.dest=dest;
        this.w=w;
        this.info="";
        this.tag=0;
    }

    /**
     * return the id of the source node of this edge.
     * @return
     */
    @Override
    public int getSrc() {
        return src;
    }

    /**
     * return the id of the destination node of this edge
     * @return
     */
    @Override
    public int getDest() {
        return dest;
    }

    /**
     * the weight of this edge (positive value).
     * @return
     */
    @Override
    public double getWeight() {
        return w;
    }

    /**
     * Returns the remark (meta data) associated with this edge
     * @return
     */
    @Override
    public String getInfo() {
        return info;
    }

    /**
     *changing the remark (meta data) associated with this edge.
     *@param s
     */
    @Override
    public void setInfo(String s) {
        info=s;

    }

    /**
     * return the tag of the edge
     * @return
     */
    @Override
    public int getTag() {
        return tag;
    }

    /**
     * this method allows setting the "tag" value for temporal marking an edge - common
     * practice for marking by algorithms.
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        tag=t;

    }

    /**
     * check if the edges are equals
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.src == ((EdgeData) o).src&&this.dest == ((EdgeData) o).dest&&this.tag == ((EdgeData) o).tag&& this.w==((EdgeData) o).w
                &&this.info==((EdgeData) o).info;

    }

    /**
     * return string of all the edge information
     * @return
     */
    @Override
    public String toString()
    {
        return "edge src : "+ src+ "\n"+"edge dest :"+dest+"\n"+"edge Weight : "+w;
    }
}
