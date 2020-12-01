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
        return 0;
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
