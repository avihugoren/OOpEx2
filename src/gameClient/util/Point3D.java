/**
 * This class represents a 3D point in space.
 */
package gameClient.util;

import api.geo_location;

import java.io.Serializable;

public class Point3D implements geo_location, Serializable{
	private static final long serialVersionUID = 1L;
	/**
     * Simple set of constants - should be defined in a different class (say class Constants).*/
    public static final double EPS1 = 0.001, EPS2 = Math.pow(EPS1,2), EPS=EPS2;
    /**
     * This field represents the origin point:[0,0,0]
     */
    public static final Point3D ORIGIN = new Point3D(0,0,0);
    private double _x,_y,_z;

    /**
     * constructor
     * @param x,y,z
     */
    public Point3D(double x, double y, double z) {
        _x=x;
        _y=y;
        _z=z;
    }

    /**
     * constructor that get x,y ans set z=0
     * @param x,y
     */
    public Point3D(double x, double y) {
        this(x,y,0);}

    /**
     * get Point3D from string
     * @param s
     */
    public Point3D(String s) { try {
            String[] a = s.split(",");
            _x = Double.parseDouble(a[0]);
            _y = Double.parseDouble(a[1]);
            _z = Double.parseDouble(a[2]);
        }
        catch(IllegalArgumentException e) {
            System.err.println("ERR: got wrong format string for POint3D init, got:"+s+"  should be of format: x,y,x");
            throw(e);
        }
    }

    /**
     * get x
     * @return
     */
    @Override
    public double x() {return _x;}

    /**
     * get y
     * @return
     */
    @Override
    public double y() {return _y;}

    /**
     * get z
     * @return
     */
    @Override
    public double z() {return _z;}


    public String toString() { return _x+","+_y+","+_z; }

    /**
     * return the distance
     * @param p2
     * @return
     */
    @Override
    public double distance(geo_location p2) {
        double dx = this.x() - p2.x();
        double dy = this.y() - p2.y();
        double dz = this.z() - p2.z();
        double t = (dx*dx+dy*dy+dz*dz);
        return Math.sqrt(t);
    }

    /**
     * check if the 2  Point3D are equals
     * @param p
     * @return
     */
    public boolean equals(Object p) {
        if(p==null || !(p instanceof geo_location)) {return false;}
        Point3D p2 = (Point3D)p;
        return ( (_x==p2._x) && (_y==p2._y) && (_z==p2._z) );
    }


     public String toString(boolean all) {
        if(all) return "[" + _x + "," +_y+","+_z+"]";
        else return "[" + (int)_x + "," + (int)_y+","+(int)_z+"]";
    }
}

