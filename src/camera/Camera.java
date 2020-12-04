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
public class Camera {

    private final double WIDTH = 800.0;
    private final double HEIGHT = 800.0;
    private final double STEP = 10.0;
    private final double ROTATION_ANGLE = 0.1;
    private double focalLength = 200.0;

    private Construction construction;

    public Camera(String adress) {
        construction = new Construction(adress);
    }

    public void moveRight() {
        for (Line3D l : construction.getLines3D()) {
            l.getPoint1().setX(l.getPoint1().getX() - STEP);
            l.getPoint2().setX(l.getPoint2().getX() - STEP);
        }
    }

    public void moveLeft() {

        for (Line3D l : construction.getLines3D()) {
            l.getPoint1().setX(l.getPoint1().getX() + STEP);
            l.getPoint2().setX(l.getPoint2().getX() + STEP);
        }
    }

    public void moveUp() {

        for (Line3D l : construction.getLines3D()) {
            l.getPoint1().setY(l.getPoint1().getY() + STEP);
            l.getPoint2().setY(l.getPoint2().getY() + STEP);
        }
    }

    public void moveDown() {
        for (Line3D l : construction.getLines3D()) {
            l.getPoint1().setY(l.getPoint1().getY() - STEP);
            l.getPoint2().setY(l.getPoint2().getY() - STEP);
        }
    }

    public void moveForward() {
        for (Line3D l : construction.getLines3D()) {
            l.getPoint1().setZ(l.getPoint1().getZ() - STEP);
            l.getPoint2().setZ(l.getPoint2().getZ() - STEP);
        }
    }

    public void moveBackward() {
        for (Line3D l : construction.getLines3D()) {
            l.getPoint1().setZ(l.getPoint1().getZ() + STEP);
            l.getPoint2().setZ(l.getPoint2().getZ() + STEP);
        }
    }

    public void rotateX(int sign) {
        double y, z;
        for (Line3D l : construction.getLines3D()) {
            y = l.getPoint1().getY();
            z = l.getPoint1().getZ();
            l.getPoint1().setY(y * Math.cos(ROTATION_ANGLE * sign) - z * Math.sin(ROTATION_ANGLE * sign));
            l.getPoint1().setZ(z * Math.cos(ROTATION_ANGLE * sign) + y * Math.sin(ROTATION_ANGLE * sign));
            y = l.getPoint2().getY();
            z = l.getPoint2().getZ();
            l.getPoint2().setY(y * Math.cos(ROTATION_ANGLE * sign) - z * Math.sin(ROTATION_ANGLE * sign));
            l.getPoint2().setZ(z * Math.cos(ROTATION_ANGLE * sign) + y * Math.sin(ROTATION_ANGLE * sign));
        }
    }

    public void rotateY(int sign) {
        double x, z;
        for (Line3D l : construction.getLines3D()) {
            x = l.getPoint1().getX();
            z = l.getPoint1().getZ();
            l.getPoint1().setX(x * Math.cos(ROTATION_ANGLE * sign) + z * Math.sin(ROTATION_ANGLE * sign));
            l.getPoint1().setZ(z * Math.cos(ROTATION_ANGLE * sign) - x * Math.sin(ROTATION_ANGLE * sign));
            x = l.getPoint2().getX();
            z = l.getPoint2().getZ();
            l.getPoint2().setX(x * Math.cos(ROTATION_ANGLE * sign) + z * Math.sin(ROTATION_ANGLE * sign));
            l.getPoint2().setZ(z * Math.cos(ROTATION_ANGLE * sign) - x * Math.sin(ROTATION_ANGLE * sign));
        }
    }

    public void rotateZ(int sign) {
        double x, y;
        for (Line3D l : construction.getLines3D()) {
            x = l.getPoint1().getX();
            y = l.getPoint1().getY();
            l.getPoint1().setX(x * Math.cos(ROTATION_ANGLE * sign) - y * Math.sin(ROTATION_ANGLE * sign));
            l.getPoint1().setY(y * Math.cos(ROTATION_ANGLE * sign) + x * Math.sin(ROTATION_ANGLE * sign));
            x = l.getPoint2().getX();
            y = l.getPoint2().getY();
            l.getPoint2().setX(x * Math.cos(ROTATION_ANGLE * sign) - y * Math.sin(ROTATION_ANGLE * sign));
            l.getPoint2().setY(y * Math.cos(ROTATION_ANGLE * sign) + x * Math.sin(ROTATION_ANGLE * sign));
        }
    }

    public void zoomIn() {
        focalLength += STEP;
        if (focalLength > 1000) {
            focalLength = 1000;
        }
    }

    public void zoomOut() {
        focalLength -= STEP;
        if (focalLength < 50) {
            focalLength = 50;
        }

    }

    public void projectTo2D() {
        construction.clearLines2D();
        for (Line3D l : construction.getLines3D()) {
            double x1, x2, y1, y2;
            double z1 = l.getPoint1().getZ();
            double z2 = l.getPoint2().getZ();
            if (z1 > 1) {
                x1 = l.getPoint1().getX() * focalLength / z1;
                y1 = l.getPoint1().getY() * focalLength / z1;
            } else {
                x1 = l.getPoint1().getX() * focalLength;
                y1 = l.getPoint1().getY() * focalLength;
            }
            if (z2 > 1) {
                x2 = l.getPoint2().getX() * focalLength / z2;
                y2 = l.getPoint2().getY() * focalLength / z2;
            } else {
                x2 = l.getPoint2().getX() * focalLength;
                y2 = l.getPoint2().getY() * focalLength;
            }
            construction.addLine2D(new Line2D(new Point2D(x1, y1), new Point2D(x2, y2)));
        }
    }

    public void moveConstructionToCenter() {
        for (Line2D l : construction.getLines2D()) {
            l.getPoint1().setX(l.getPoint1().getX() + WIDTH / 2);
            l.getPoint1().setY(l.getPoint1().getY() + HEIGHT / 2);
            l.getPoint2().setX(l.getPoint2().getX() + WIDTH / 2);
            l.getPoint2().setY(l.getPoint2().getY() + HEIGHT / 2);
        }
    }

    public Construction getConstruction() {
        return construction;
    }
}
