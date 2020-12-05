package bfs;

import program.Maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BFS {
    private Maze maze;
    private MyQueue<Node> queue;
    private Node wall;
    private Node first;
    private List<Integer> solvedMaze;


    public BFS(Maze maze) {
        this.maze = maze;
        wall = new Node(-1, null, null, null, null);
        queue = new MyQueue<Node>();
        solvedMaze = new ArrayList<Integer>();
        solveWholeGraph();
    }

    public void solveWholeGraph() {
        makeGraph();
        solveMaze();
        printSolution();
    }

    private void makeGraph() {
        int number = 1;
        int size = maze.getSize();
        List<String> mazeMap = maze.getMaze();
        Map<Integer, Node> map = new HashMap<Integer, Node>();

        //pętla tworząca węzły
        for (int i = size + 1; i < size * (size - 1) - 1; i += 2) {

            //moment gdy indeks jest na pierwszym znaku w  linii i przeskoczyć musi linie "ściany"
            if (i % size == 0) {
                if (i % 2 == 0) {
                    i += size;
                }
                i++;
            }
            map.put(number, new Node(number));
            number++;
        }

        //pętla tworząca graf
        int lineMultiplyer = 0; // zmienna pozwalająca przeskoczyć linie "ściany"
        for (int i = 0; i < map.size(); i++) {
            int index = size + 1 + i * 2 + lineMultiplyer;
            if (index % size == 0 && index % 2 == 0) {
                lineMultiplyer += size + 1;
                index = size + 1 + i * 2 + lineMultiplyer;
            }
            Node node = map.get(i + 1);


            if (mazeMap.get(index - 1).compareTo("0") == 0) {
                node.setLeft(map.get(i));
            } else {
                node.setLeft(wall);
            }

            if (mazeMap.get(index + 1).compareTo("0") == 0) {
                node.setRight(map.get(i + 2));
            } else {
                node.setRight(wall);
            }

            if (mazeMap.get(index - size).compareTo("0") == 0) {
                node.setUp(map.get(i + 1 - (size - 1) / 2));
            } else if (mazeMap.get(index - size).compareTo("#") == 0) {
                node.setEntrance(true);
                node.setUp(wall);
                first = node;
            } else {
                node.setUp(wall);
            }

            if (mazeMap.get(index + size).compareTo("0") == 0) {
                node.setDown(map.get(i + 1 + (size - 1) / 2));
            } else if (mazeMap.get(index + size).compareTo("*") == 0) {
                node.setExit(true);
                node.setDown(wall);
            } else {
                node.setDown(wall);
            }
        }
    }

    private void solveMaze() {
        Node node = first;
        queue.push(first);
        List<Integer> used = new ArrayList<Integer>();

        while (!queue.isEmpty() && !node.isExit()) {
            node = queue.pop();
            used.add(node.getIndex());

            if (node.isExit()) {
                break;
            }

            if (node.getUp().compareTo(wall) != 0 && !used.contains(node.getUp().getIndex())) {
                queue.push(node.getUp());
                node.getUp().setCreator(node);
            }

            if (node.getRight().compareTo(wall) != 0 && !used.contains(node.getRight().getIndex())) {
                queue.push(node.getRight());
                node.getRight().setCreator(node);
            }

            if (node.getDown().compareTo(wall) != 0 && !used.contains(node.getDown().getIndex())) {
                queue.push(node.getDown());
                node.getDown().setCreator(node);
            }

            if (node.getLeft().compareTo(wall) != 0 && !used.contains(node.getLeft().getIndex())) {
                queue.push(node.getLeft());
                node.getLeft().setCreator(node);
            }

        }

        while (!node.isEntrance()) {
            solvedMaze.add(node.getIndex());
            node = node.getCreator();
        }
        solvedMaze.add(node.getIndex());
    }

    private void printSolution() {
        for (Integer i : solvedMaze) {
            System.out.print(i + " ");
            if (solvedMaze.get(solvedMaze.size() - 1).compareTo(i) != 0) {
                System.out.print("<- ");
            }
        }
        System.out.println();
    }

    public List<Integer> getSolvedMaze() {
        return solvedMaze;
    }
}
