/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import camera.Camera;
import camera.Line2D;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

/**
 *
 * @author Paweł
 */
public class Controller {

    private Camera camera;

    @FXML
    private Canvas viewport;

    public void initialize() {
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
                default:
                    break;
            }
            camera.projectTo2D();
            camera.moveConstructionToCenter();
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
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1.8);
        gc.beginPath();
        for (Line2D line : camera.getConstruction().getLines2D()) {
            gc.moveTo(line.getPoint1().getX(), line.getPoint1().getY());
            gc.lineTo(line.getPoint2().getX(), line.getPoint2().getY());
        }
        gc.stroke();
    }

}
