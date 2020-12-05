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
        double xd=Math.pow(2,g.x()-x);
        double yd=Math.pow(2,g.y()-y);
        double zd=Math.pow(2,g.z()-z);
        return Math.pow(2,xd+yd+zd);
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
