package tremaux;

import program.Main;
import program.Maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static program.Main.printMaze;



public class Tremaux {
    private List<String> mazeMap;
    private Explorer explorer;

    public Tremaux(Maze maze) {
        mazeMap = new ArrayList<String>();
        copyMaze(maze);
        this.explorer = new Explorer(maze);
//        startSolving(maze);
        Main.mazeMap = mazeMap;
    }

    public void startSolving(Maze maze) {
        int size = maze.getSize();
        performMazeSolve(size);
        drawPath(size);
    }

    public void performMazeSolve(int size) {
        while (mazeMap.get((explorer.getY() + 1) * size + explorer.getX()).compareTo("*") != 0) {
            int x = explorer.getX();
            int y = explorer.getY();

            //tablica oznaczeń wejść na skrzyżowaniu
            String[] signs = {mazeMap.get(y * size + x + 1), mazeMap.get((y + 1) * size + x), mazeMap.get(y * size + x - 1), mazeMap.get((y - 1) * size + x)};

            boolean allNotVisted = true;
            int counter = 0;
            for (int i = 0; i < 4; i++) {

                if (i != explorer.getDirection() && signs[i].compareTo("0") != 0 && signs[i].compareTo("+") != 0 && signs[i].compareTo("#") != 0) {
                    allNotVisted = false;
                }
                if (i != explorer.getDirection() && signs[i].compareTo("+") == 0) {
                    counter++;
                }
            }

            //ślepy zaułek
            if (counter == 3) {
                allNotVisted = false;
            }

            if (allNotVisted) {
                //przypadek gdy na skrzowaniu nigdy wcześniej
                List<Integer> directionList = indexList(signs);
                int index = (new Random()).nextInt(directionList.size());
                moveToDirection(directionList.get(index), 0);
            } else if (signs[explorer.getDirection()].compareTo("X") == 0) {
                //przypadek gdy przychodzimy z korytarza dwukrotnie oznaczonego
                moveWhenSigned(explorer.getDirection(), signs);
            } else {
                moveToDirection(explorer.getDirection(), 0);
            }

        }

    }

    private List<Integer> indexList(String[] signs) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 4; i++) {
            if (signs[i].compareTo("0") == 0) {
                list.add(i);
            }
        }
        return list;
    }

    public void drawPath(int size) {
        while (mazeMap.get((explorer.getY() - 1) * size + explorer.getX()).compareTo("#") != 0) {
            int x = explorer.getX();
            int y = explorer.getY();
            String[] signs = {mazeMap.get(y * size + x + 1), mazeMap.get((y + 1) * size + x), mazeMap.get(y * size + x - 1), mazeMap.get((y - 1) * size + x)};
            int index = -1;
            for (int i = 0; i < 4; i++) {
                if (signs[i].compareTo("/") == 0) {
                    index = i;
                }
            }
            if (index == -1) {
                throw new ArithmeticException();
            }
            moveToDirection(index, 1);
        }
    }

    private void moveWhenSigned(int direction, String[] signs) {
        int index = direction;
        List<Integer> indexList = new ArrayList<Integer>();

        for (int i = 0; i < 4; i++) {
            if (i == direction) {
                continue;
            }

            if (signCompare(i, index, signs) == 1) {
                if (signs[i].compareTo("0") == 0 && signs[index].compareTo("X") == 0) {
                    index = i;
                    indexList.add(i);
                } else if (signs[i].compareTo("0") == 0 && signs[index].compareTo("0") == 0) {
                    indexList.add(i);
                } else if (signs[i].compareTo("0") == 0 && signs[index].compareTo("/") == 0) {
                    indexList = new ArrayList<Integer>();
                    indexList.add(i);
                    index = i;
                } else {
                    index = i;
                    indexList.add(i);
                }

            }
        }


        int destination = (new Random()).nextInt(indexList.size());

        moveToDirection(indexList.get(destination), 0);
    }

    private int signCompare(int i, int index, String[] signs) {
        if (signs[i].compareTo("X") == 0 && signs[index].compareTo("X") == 0) {
            return 0;
        } else if (signs[i].compareTo("/") == 0 && signs[index].compareTo("X") == 0) {
            return 1;
        } else if (signs[i].compareTo("0") == 0 && signs[index].compareTo("X") == 0) {
            return 1;
        } else if (signs[i].compareTo("0") == 0 && signs[index].compareTo("/") == 0) {
            return 1;
        } else {
            return -1;
        }
    }

    private void moveToDirection(int direction, int mode) {
        if (direction == 0) {
            explorer.setDirection(2);
            moveRight(mode);
        } else if (direction == 1) {
            explorer.setDirection(3);
            moveDown(mode);
        } else if (direction == 2) {
            explorer.setDirection(0);
            moveLeft(mode);
        } else if (direction == 3) {
            explorer.setDirection(1);
            moveUp(mode);
        }
    }

    private void moveUp(int mode) {
        int y = explorer.getY();
        int x = explorer.getX();
        int size = (int) Math.sqrt(mazeMap.size());
        String change = mazeMap.get((y - 1) * size + x).compareTo("0") == 0 ? "/" : (mode == 0 ? "X" : "1");
        mazeMap.set((y - 1) * size + x, change);
        if (mode == 1) {
            mazeMap.set(y * size + x, change);
        }


        while (mazeMap.get((y - 1) * size + x).compareTo("+") != 0
                && mazeMap.get((y - 1) * size + x).compareTo("#") != 0
                && mazeMap.get((y - 2) * size + x - 1).compareTo("0") != 0
                && mazeMap.get((y - 2) * size + x + 1).compareTo("0") != 0
                && mazeMap.get((y - 2) * size + x - 1).compareTo("/") != 0
                && mazeMap.get((y - 2) * size + x + 1).compareTo("/") != 0
                && mazeMap.get((y - 2) * size + x - 1).compareTo("X") != 0
                && mazeMap.get((y - 2) * size + x + 1).compareTo("X") != 0
        ) {
            y = explorer.decrY();
            if (mode == 1) {
                mazeMap.set(y * size + x, change);
                mazeMap.set((y + 1) * size + x, change);
            }
        }
        if (mazeMap.get((y - 1) * size + x).compareTo("+") != 0 && mazeMap.get((y - 1) * size + x).compareTo("#") != 0) {
            y = explorer.decrY();
            if (mode == 1) {
                mazeMap.set(y * size + x, change);
                mazeMap.set((y + 1) * size + x, change);
            }
        }
        mazeMap.set((y + 1) * size + x, change);
    }

    private void moveDown(int mode) {
        int y = explorer.getY();
        int x = explorer.getX();
        int size = (int) Math.sqrt(mazeMap.size());
        String change = mazeMap.get((y + 1) * size + x).compareTo("0") == 0 ? "/" : (mode == 0 ? "X" : "1");
        mazeMap.set((y + 1) * size + x, change);
        if (mode == 1) {
            mazeMap.set(y * size + x, change);
        }


        while (mazeMap.get((y + 1) * size + x).compareTo("+") != 0
                && mazeMap.get((y + 1) * size + x).compareTo("*") != 0
                && mazeMap.get((y + 2) * size + x - 1).compareTo("0") != 0
                && mazeMap.get((y + 2) * size + x + 1).compareTo("0") != 0
                && mazeMap.get((y + 2) * size + x - 1).compareTo("/") != 0
                && mazeMap.get((y + 2) * size + x + 1).compareTo("/") != 0
                && mazeMap.get((y + 2) * size + x - 1).compareTo("X") != 0
                && mazeMap.get((y + 2) * size + x + 1).compareTo("X") != 0
        ) {
            y = explorer.incY();
            if (mode == 1) {
                mazeMap.set(y * size + x, change);
                mazeMap.set((y - 1) * size + x, change);
            }
        }
        if (mazeMap.get((y + 1) * size + x).compareTo("+") != 0 && mazeMap.get((y + 1) * size + x).compareTo("*") != 0) {
            y = explorer.incY();
            if (mode == 1) {
                mazeMap.set(y * size + x, change);
                mazeMap.set((y - 1) * size + x, change);
            }
        }
        mazeMap.set((y - 1) * size + x, change);
    }

    private void moveLeft(int mode) {
        int y = explorer.getY();
        int x = explorer.getX();
        int size = (int) Math.sqrt(mazeMap.size());
        String change = mazeMap.get(y * size + x - 1).compareTo("0") == 0 ? "/" : (mode == 0 ? "X" : "1");
        mazeMap.set(y * size + x - 1, change);
        if (mode == 1) {
            mazeMap.set(y * size + x, change);
        }


        while (mazeMap.get(y * size + x - 1).compareTo("+") != 0
                && mazeMap.get((y - 1) * size + x - 2).compareTo("#") != 0
                && mazeMap.get((y + 1) * size + x - 2).compareTo("*") != 0
                && mazeMap.get((y - 1) * size + x - 2).compareTo("0") != 0
                && mazeMap.get((y + 1) * size + x - 2).compareTo("0") != 0
                && mazeMap.get((y - 1) * size + x - 2).compareTo("/") != 0
                && mazeMap.get((y + 1) * size + x - 2).compareTo("/") != 0
                && mazeMap.get((y - 1) * size + x - 2).compareTo("X") != 0
                && mazeMap.get((y + 1) * size + x - 2).compareTo("X") != 0
                && mazeMap.get((y - 1) * size + x - 2).compareTo("#") != 0
                && mazeMap.get((y + 1) * size + x - 2).compareTo("*") != 0
        ) {
            x = explorer.decrX();
            if (mode == 1) {
                mazeMap.set(y * size + x, change);
                mazeMap.set(y * size + x + 1, change);
            }
        }
        if (mazeMap.get(y * size + x - 1).compareTo("+") != 0) {
            x = explorer.decrX();
            if (mode == 1) {
                mazeMap.set(y * size + x, change);
                mazeMap.set(y * size + x + 1, change);
            }
        }
        mazeMap.set(y * size + x + 1, change);
    }

    private void moveRight(int mode) {
        int y = explorer.getY();
        int x = explorer.getX();
        int size = (int) Math.sqrt(mazeMap.size());
        String change = mazeMap.get(y * size + x + 1).compareTo("0") == 0 ? "/" : (mode == 0 ? "X" : "1");
        mazeMap.set(y * size + x + 1, change);
        if (mode == 1) {
            mazeMap.set(y * size + x, change);
        }


        while (mazeMap.get(y * size + x + 1).compareTo("+") != 0
                && mazeMap.get((y + 1) * size + x + 2).compareTo("*") != 0
                && mazeMap.get((y - 1) * size + x + 2).compareTo("#") != 0
                && mazeMap.get((y - 1) * size + x + 2).compareTo("0") != 0
                && mazeMap.get((y + 1) * size + x + 2).compareTo("0") != 0
                && mazeMap.get((y - 1) * size + x + 2).compareTo("/") != 0
                && mazeMap.get((y + 1) * size + x + 2).compareTo("/") != 0
                && mazeMap.get((y - 1) * size + x + 2).compareTo("X") != 0
                && mazeMap.get((y + 1) * size + x + 2).compareTo("X") != 0


        ) {
            x = explorer.incX();
            if (mode == 1) {
                mazeMap.set(y * size + x, change);
                mazeMap.set(y * size + x - 1, change);
            }
        }

        if (mazeMap.get(y * size + x + 1).compareTo("+") != 0) {
            x = explorer.incX();
            if (mode == 1) {
                mazeMap.set(y * size + x, change);
                mazeMap.set(y * size + x - 1, change);
            }
        }
        mazeMap.set(y * size + x - 1, change);
    }

    private void copyMaze(Maze maze) {
        for (String i : maze.getMaze()) {
            mazeMap.add(i);
        }
    }

    public void printToConsoleSolution(){
        printMaze(new Maze(mazeMap));
    }

    public List<String> getMazeMap() {
        return mazeMap;
    }
}
