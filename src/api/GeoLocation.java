package api;

public class GeoLocation implements geo_location {
    double x;
    double y;
    double z;

    /**
     * constructor
     * @param x,y,z
     */
    public GeoLocation(double x,double y,double z)
    {
        this.x=x;
        this.y=y;
        this.z=z;
    }

    /**
     * @return x
     */
    @Override
    public double x() {
        return x;
    }

    /**
     * @return y
     */
    @Override
    public double y() {
        return y;
    }

    /**
     * @return z
     */
    @Override
    public double z() {
        return z;
    }

    /**
     * return the distance with the geo_location he get
     * @param g
     * @return
     */
    @Override
    public double distance(geo_location g) {
        double xd=Math.pow(g.x()-x,2);
        double yd=Math.pow(g.y()-y,2);
        double zd=Math.pow(g.z()-z,2);
        return Math.pow(xd+yd+zd,1/2.0);
    }

    /**
     * check if the geo_location is equals
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GeoLocation))
            return false;
        GeoLocation g = (GeoLocation) o;
        if (g.x() != this.x() || g.y() != this.y()||g.z()!=this.z())
            return false;
        return true;
    }

    /**
     * return string of x,y,z
     * @return
     */
    @Override
    public String toString()
    {
        String s="";
        s=s+x;
        s=s+","+y+","+z;
        return s;
    }
}
