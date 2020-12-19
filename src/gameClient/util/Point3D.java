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
     * This field represents the origin point:(0,0,0)
     */
    public static final Point3D ORIGIN = new Point3D(0,0,0);
    private double _x,_y,_z;
    public Point3D(double x, double y, double z) {
        _x=x;
        _y=y;
        _z=z;
    }

    public Point3D(Point3D p) {
       this(p.x(), p.y(), p.z());
    }
    public Point3D(double x, double y) {this(x,y,0);}
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
     * This function returns the value of the x point's field.
     * @return returns the value of the x point's field.
     */
    @Override
    public double x() {return _x;}
    /**
     * This function returns the value of the y point's field.
     * @return returns the value of the y point's field.
     */
    @Override
    public double y() {return _y;}
    /**
     * This function returns the value of the z point's field.
     * @return returns the value of the z point's field.
     */
    @Override
    public double z() {return _z;}

    /**
     * This function returns the point's string.
     * @return returns the point's string, which describes the point's fields.
     */
    public String toString() { return _x+","+_y+","+_z; }
    /**
	 * This function calculates the distance between this geographic location to a given geographic location.
	 * @param p2- the other geographic location.
	 * @return returns the distance between this geographic location to a given geographic location.
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
	 * This function checks the given object is equals to this Point3D.
	 * @param p- this is the other object.
	 * @return returns true if this Point3D and the other object are equals, otherwise returns false.
	 */
    public boolean equals(Object p) {
        if(p==null || !(p instanceof geo_location)) {return false;}
        Point3D p2 = (Point3D)p;
        return ( (_x==p2._x) && (_y==p2._y) && (_z==p2._z) );
    }
    /**
   	 * This function checks the given geo_location is almost equals to this Point3D.
   	 * @param p2- this is the other geo_location.
   	 * @return returns true if this Point3D and p2 are almost equals, otherwise returns false.
   	 */
    public boolean close2equals(geo_location p2) {
        return ( this.distance(p2) < EPS ); }
    /**
   	 * This function checks if the fields of  x and y of this Point3D are equals to those fields at p.
   	 * @param p- this is the other Point3D.
   	 * @return returns true if the fields of x and y of this Point3D are equals to those fields at p., otherwise returns false.
   	 */
    public boolean equalsXY (Point3D p)
    {return p._x == _x && p._y == _y;}
    /**
     * This function returns the string of this Point3D.
     * @param all- this is a boolean condition.
     * @return the string of this Point3D.
     * if all is true- the string will be between [], otherwise without any closer.
     */

     public String toString(boolean all) {
        if(all) return "[" + _x + "," +_y+","+_z+"]";
        else return ""+_x+","+_y+","+_z;
    }
}

