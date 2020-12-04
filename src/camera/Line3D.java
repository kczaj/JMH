/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camera;

/**
 *
 * @author Paweł
 */
public class Line3D {

    private Point3D point1;
    private Point3D point2;

    public Line3D(Point3D point1, Point3D point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public Point3D getPoint1() {
        return point1;
    }

    public void setPoint1(Point3D point1) {
        this.point1 = point1;
    }

    public Point3D getPoint2() {
        return point2;
    }

    public void setPoint2(Point3D point2) {
        this.point2 = point2;
    }

}
