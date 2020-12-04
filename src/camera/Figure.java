package camera;

import java.util.ArrayList;
import java.util.List;

public class Figure implements Comparable<Figure>{

    private String color;
    private List<Line3D> lines3D;
    private List<Line2D> lines2D;
    private double[] listOfX;
    private double[] listOfY;
    private Point3D centroid;
    private double distance;

    public Figure(String color, ArrayList<Line3D> lines) {
        this.lines3D = lines;
        this.listOfX = new double[lines.size()];
        this.listOfY = new double[lines.size()];
        this.color = color;
        searchCentroid();
    }

    public List<Line3D> getLines3D() {
        return lines3D;
    }

    public List<Line2D> getLines2D() {
        return lines2D;
    }

    public void addLine3D(Line3D line) {
        lines3D.add(line);
    }

    public void addLine2D(Line2D line) {
        lines2D.add(line);
    }

    public void clearLines2D() {
        lines2D = new ArrayList<>();
    }

    public String getColor() {
        return color;
    }

    public void convertToTables() {
        for (int i = 0; i < lines2D.size(); i++) {
            listOfX[i] = lines2D.get(i).getPoint1().getX();
            listOfY[i] = lines2D.get(i).getPoint1().getY();
        }
    }

    public double[] getListOfX() {
        return listOfX;
    }

    public double[] getListOfY() {
        return listOfY;
    }

    private double searchAverage(int axis) {
        double sum = 0;
        for (Line3D l : lines3D) {
            if (axis == 0) {
                sum = sum + l.getPoint1().getX();
            } else if (axis == 1) {
                sum = sum + l.getPoint1().getY();
            } else if (axis == 2) {
                sum = sum + l.getPoint1().getZ();
            }
        }
        return sum / lines3D.size();
    }

    public void searchCentroid() {
        double x = searchAverage(0);
        double y = searchAverage(1);
        double z = searchAverage(2);
        centroid = new Point3D(x, y, z);
        countDistance();
    }

    private void countDistance() {
        this.distance = Math.sqrt(Math.pow(centroid.getX(), 2) + Math.pow(centroid.getY(), 2) + Math.pow(centroid.getZ(), 2));
    }

    @Override
    public int compareTo(Figure o) {
        if (o == null) {
            return 1;
        }

        if (this.distance > o.distance) {
            return -1;
        } else if (this.distance < o.distance) {
            return 1;
        } else {
            return 0;
        }
    }

}
