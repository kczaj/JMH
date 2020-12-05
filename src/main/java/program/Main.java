package program;

import bfs.BFS;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import tremaux.Tremaux;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Main extends Application {
    public static List<String> mazeMap;

    public static void main(String[] args) throws IOException {

        Maze maze1 = new Maze(5);
        maze1.generate();
        System.out.println("Labirynt: 5 pól");
        printMaze(maze1);
        System.out.println();
        Maze maze2 = new Maze(10);
        maze2.generate();
        System.out.println("Labirynt: 10 pól i zapisanie do pliku");
        printMaze(maze2);
        System.out.println();
        maze2.save("src\\main\\resources\\mazeToLoad.txt");
//        Maze maze3 = new Maze("src\\main\\resources\\mazeToLoad.txt");
//        System.out.println("Labirynt wczytany z poprzedniego zapisu");
//        printMaze(maze3);
//        System.out.println();

        Maze mazeToSolve = new Maze(4);
        mazeToSolve.generate();
        BFS bfs = new BFS(mazeToSolve);
        Tremaux tremaux = new Tremaux(mazeToSolve);

        visualMaze(args);
    }

    private static void timesToFindExit() throws FileNotFoundException {
        long bfsTime[] = new long[45];
        long tremauxTime[] = new long[45];

        for(int i = 5; i < 50; i++){
            Maze maze = new Maze(i);
            long time = System.nanoTime();
            new BFS(maze);
            bfsTime[i - 5] = System.nanoTime() - time;
            new Tremaux(maze);
            tremauxTime[i - 5] = System.nanoTime() - (bfsTime[i - 5] + time);
        }

        PrintWriter w1 = new PrintWriter("bfs.txt");
        PrintWriter w2 = new PrintWriter("tremaux.txt");
        for(int i = 0; i< bfsTime.length; i++){
            w1.println(bfsTime[i]);
        }
        w1.close();

        for(int i = 0; i< tremauxTime.length; i++){
            w2.println(tremauxTime[i]);
        }
        w2.close();
    }

    public static void printMaze(Maze maze) {
        int i = 0;
        for (String s : maze.getMaze()) {
            System.out.print(s);
            if ((i + 1) % maze.getSize() == 0)
                System.out.println();
            i++;
        }
    }

    private static void visualMaze(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 800, 800);
        Pane pane = new Pane();
        pane.setMinSize(800, 800);
        pane.setMaxSize(800, 800);
        makeMazeVisible(pane, 0);
        root.getChildren().add(pane);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void makeMazeVisible(Pane pane, int mode) {
        double sizeOfR = 800 / Math.sqrt(mazeMap.size());

        int size = (int) Math.sqrt(mazeMap.size());

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Rectangle r = new Rectangle(j * sizeOfR, i * sizeOfR, sizeOfR, sizeOfR);
                if (mazeMap.get(i * size + j).compareTo("+") == 0) {
                    r.setFill(Color.BLACK);
                }
                if (mazeMap.get(i * size + j).compareTo("0") == 0) {
                    r.setFill(Color.WHITE);
                }
                if (mazeMap.get(i * size + j).compareTo("#") == 0) {
                    r.setFill(Color.LIGHTBLUE);
                }
                if (mazeMap.get(i * size + j).compareTo("*") == 0) {
                    r.setFill(Color.LIGHTGREEN);
                }
                if (mode == 1 || mazeMap.get(i * size + j).compareTo("X") == 0) {
                    r.setFill(Color.WHITE);
                }
                if (mode == 1 || mazeMap.get(i * size + j).compareTo("1") == 0) {
                    r.setFill(Color.RED);
                }
                pane.getChildren().add(r);
            }
        }
    }
}
