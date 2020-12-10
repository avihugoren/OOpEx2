package api;

public class GeoLocation implements geo_location {
    double x;
    double y;
    double z;
    public GeoLocation(double x,double y,double z)
    {
        this.x=x;
        this.y=y;
        this.z=z;
    }
    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double z() {
        return z;
    }

    @Override
    public double distance(geo_location g) {
        double xd=Math.pow(g.x()-x,2);
        double yd=Math.pow(g.y()-y,2);
        double zd=Math.pow(g.z()-z,2);
        return Math.pow(xd+yd+zd,1/2.0);
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GeoLocation))
            return false;
        GeoLocation g = (GeoLocation) o;
        if (g.x() != this.x() || g.y() != this.y()||g.z()!=this.z())
            return false;
        return true;
    }
    @Override
    public String toString()
    {
        String s="";
        s=s+x;
        s=s+","+y+","+z;
        return s;
    }
}
