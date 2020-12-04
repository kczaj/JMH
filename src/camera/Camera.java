package camera;

import java.util.List;

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
        for (Figure figure : construction.getFigures()) {
            for (Line3D l : figure.getLines3D()) {
                l.getPoint1().setX(l.getPoint1().getX() - STEP);
                l.getPoint2().setX(l.getPoint2().getX() - STEP);
            }
        }
    }

    public void moveLeft() {
        for (Figure figure : construction.getFigures()) {
            for (Line3D l : figure.getLines3D()) {
                l.getPoint1().setX(l.getPoint1().getX() + STEP);
                l.getPoint2().setX(l.getPoint2().getX() + STEP);
            }
        }
    }

    public void moveUp() {
        for (Figure figure : construction.getFigures()) {
            for (Line3D l : figure.getLines3D()) {
                l.getPoint1().setY(l.getPoint1().getY() + STEP);
                l.getPoint2().setY(l.getPoint2().getY() + STEP);
            }
        }
    }

    public void moveDown() {
        for (Figure figure : construction.getFigures()) {
            for (Line3D l : figure.getLines3D()) {
                l.getPoint1().setY(l.getPoint1().getY() - STEP);
                l.getPoint2().setY(l.getPoint2().getY() - STEP);
            }
        }
    }

    public void moveForward() {
        for (Figure figure : construction.getFigures()) {
            for (Line3D l : figure.getLines3D()) {
                l.getPoint1().setZ(l.getPoint1().getZ() - STEP);
                l.getPoint2().setZ(l.getPoint2().getZ() - STEP);
            }
        }
    }

    public void moveBackward() {
        for (Figure figure : construction.getFigures()) {
            for (Line3D l : figure.getLines3D()) {
                l.getPoint1().setZ(l.getPoint1().getZ() + STEP);
                l.getPoint2().setZ(l.getPoint2().getZ() + STEP);
            }
        }
    }

    public void rotateX(int sign) {
        double y, z;
        for (Figure figure : construction.getFigures()) {
            for (Line3D l : figure.getLines3D()) {
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
    }

    public void rotateY(int sign) {
        double x, z;
        for (Figure figure : construction.getFigures()) {
            for (Line3D l : figure.getLines3D()) {
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
    }

    public void rotateZ(int sign) {
        double x, y;
        for (Figure figure : construction.getFigures()) {
            for (Line3D l : figure.getLines3D()) {
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
    }

    public void zoomIn() {
        focalLength += STEP;
        if (focalLength > 1000) {
            focalLength = 1000;
        }
    }

    public void zoomOut() {
        focalLength -= STEP;
        if (focalLength < 200) {
            focalLength = 200;
        }
    }

    public void projectTo2D() {
        for (Figure figure : construction.getFigures()) {
            figure.clearLines2D();
            for (Line3D l : figure.getLines3D()) {
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
                figure.addLine2D(new Line2D(new Point2D(x1, y1), new Point2D(x2, y2)));
            }
        }
    }

    public void moveConstructionToCenter() {
        for (Figure figure : construction.getFigures()) {
            for (Line2D l : figure.getLines2D()) {
                l.getPoint1().setX(l.getPoint1().getX() + WIDTH / 2);
                l.getPoint1().setY(l.getPoint1().getY() + HEIGHT / 2);
                l.getPoint2().setX(l.getPoint2().getX() + WIDTH / 2);
                l.getPoint2().setY(l.getPoint2().getY() + HEIGHT / 2);
            }
        }
    }

    public List<Figure> getFigures() {
        return construction.getFigures();
    }

    public void convertPointsToFill() {
        for (Figure figure : construction.getFigures()) {
            figure.convertToTables();
        }
    }

    public Construction getConstruction() {
        return construction;
    }
}
