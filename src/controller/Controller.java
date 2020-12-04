package controller;

import camera.Camera;
import camera.Figure;
import camera.Line2D;
import camera.Line3D;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class Controller {

    private Camera camera;
    private boolean wallHack;

    @FXML
    private Canvas viewport;

    public void initialize() {
        this.wallHack = true;
        prepareBackground();
        camera = new Camera("src/resources/picture.txt");
        camera.projectTo2D();
        camera.moveConstructionToCenter();
        draw();
    }

    @FXML
    public void recognizeOperation(KeyEvent k) {
        if (camera != null) {
            String key = k.getCode().toString();
            switch (key) {
                case "UP":
                    camera.moveUp();
                    break;
                case "DOWN":
                    camera.moveDown();
                    break;
                case "RIGHT":
                    camera.moveRight();
                    break;
                case "LEFT":
                    camera.moveLeft();
                    break;
                case "PERIOD":
                    camera.rotateZ(1);
                    break;
                case "COMMA":
                    camera.rotateZ(-1);
                    break;
                case "W":
                    camera.rotateX(-1);
                    break;
                case "S":
                    camera.rotateX(1);
                    break;
                case "A":
                    camera.rotateY(1);
                    break;
                case "D":
                    camera.rotateY(-1);
                    break;
                case "Z":
                    camera.moveForward();
                    break;
                case "X":
                    camera.moveBackward();
                    break;
                case "OPEN_BRACKET":
                    camera.zoomIn();
                    break;
                case "CLOSE_BRACKET":
                    camera.zoomOut();
                    break;
                case "SPACE":
                    if (this.wallHack == true) {
                        this.wallHack = false;
                    } else {
                        this.wallHack = true;
                    }
                    break;
                default:
                    break;
            }
            camera.getConstruction().sortFigures();
            camera.projectTo2D();
            camera.moveConstructionToCenter();
            camera.convertPointsToFill();
            draw();
        }
    }

    public void focus() {
        viewport.requestFocus();
    }

    private void prepareBackground() {
        GraphicsContext gc = viewport.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 800, 800);
    }

    private void draw() {
        prepareBackground();
        GraphicsContext gc = viewport.getGraphicsContext2D();
        if (this.wallHack == false) {
            for (Figure figure : camera.getFigures()) {
                boolean drawFigure = true;
                int count = figure.getLines3D().size();
                for (Line3D line3D : figure.getLines3D()) {
                    if (line3D.getPoint1().getZ() < 0) {
                        count--;
                    }
                    if (count == 0) {
                        drawFigure = false;
                    }
                }
                if (drawFigure == true) {
                    gc.setFill(Color.web(figure.getColor()));
                    gc.fillPolygon(figure.getListOfX(), figure.getListOfY(), figure.getListOfX().length);
                    for (Line2D line : figure.getLines2D()) {
                        gc.setStroke(Color.BLACK);
                        gc.setLineWidth(1.0);
                        gc.beginPath();
                        gc.moveTo(line.getPoint1().getX(), line.getPoint1().getY());
                        gc.lineTo(line.getPoint2().getX(), line.getPoint2().getY());
                        gc.stroke();
                    }
                    gc.setStroke(Color.web(figure.getColor()));
                }
            }
        } else {
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(1.8);
            gc.beginPath();
            for (Figure figure : camera.getFigures()) {
                for (Line2D line : figure.getLines2D()) {
                    gc.moveTo(line.getPoint1().getX(), line.getPoint1().getY());
                    gc.lineTo(line.getPoint2().getX(), line.getPoint2().getY());
                }
            }
            gc.stroke();
        }
    }
}
