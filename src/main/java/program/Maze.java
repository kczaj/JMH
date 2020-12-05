package program;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Maze{
    private int size;
    private List<String> maze;

    public Maze(int size) {
        this.size = size * 2 + 1;
        this.maze = new ArrayList<String>();
//        generate();
    }

    public Maze(List<String> maze) {
        this.maze = maze;
        this.size = (int) Math.sqrt(maze.size());
    }

    public Maze(String file) throws IOException {
        this.maze = new ArrayList<String>();
        load(file);
        size = (int) Math.sqrt(maze.size());
    }

    private void load(String file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            String[] words = line.split("");

            for (String s : words) {
                maze.add(s);
            }
        }
    }

    public void save(String fileName) throws FileNotFoundException {
        PrintWriter pr = new PrintWriter(fileName);
        for (int i = 0; i < size * size; i++) {
            pr.print(maze.get(i));
            if ((i + 1) % size == 0) {
                pr.println();
            }
        }
        pr.close();
    }

    public void generateMain() {
        prepare();
        generate(0, size - 1, 0, size - 1);
        makeEntranceAndExit();
    }

    public void makeEntranceAndExit() {
        Random random = new Random();
        int entrance = random.nextInt(size - 1);
        while (entrance % 2 == 0) {
            entrance = random.nextInt(size - 1);
        }
        maze.set(entrance, "#");
        int exit = random.nextInt(size - 1);
        while (exit % 2 == 0) {
            exit = random.nextInt(size - 1);
        }
        maze.set((size - 1) * size + exit, "*");
    }

    public void prepare() {
        for (int i = 0; i < size; i++) {
            maze.add("+");
        }
        for (int i = 0; i < size * (size - 2); i++) {
            if (i % size == 0 || i % size == size - 1) {
                maze.add("+");
            } else {
                maze.add("0");
            }
        }
        for (int i = 0; i < size; i++) {
            maze.add("+");
        }
    }

    public void generate(int left, int right, int up, int down) {
        int horizontal = right - left;
        int vertical = down - up;
        int done = 0;

        while (horizontal > 3 && vertical > 3 && done == 0) {
            Random random = new Random();
            int index;
            int hOrV = -1;
            if (horizontal == vertical) {
                hOrV = random.nextInt(2);
            }
            if (horizontal > vertical || hOrV == 1) {
                if (horizontal == 4) {
                    index = left + 2;
                } else {
                    //zakres jest pomniejszony o 4 by nie trafić tak, że ściana będzie "przyklejona" do prawej ściany
                    index = left + random.nextInt(horizontal - 4) + 2;
                    while (maze.get(up * size + index) == "0" || maze.get(down * size + index) == "0" || index % 2 != 0) {
                        index = left + random.nextInt(horizontal - 4) + 2;
                    }
                }

                buildVerticalWall(index, vertical, up, down);
                done++;
                generate(left, index, up, down);
                generate(index, right, up, down);
            } else {
                if (vertical == 4) {
                    index = up + 2;
                } else {
                    //zakres jest pomniejszony o 4 by nie trafić tak, że ściana będzie "przyklejona" do dolnej ściany
                    index = up + random.nextInt(vertical - 4) + 2;
                    while (maze.get(index * size + left) == "0" || maze.get(index * size + right) == "0" || index % 2 != 0) {
                        index = up + random.nextInt(vertical - 4) + 2;
                    }
                }
                buildHorizontalWall(index, horizontal, left, right);
                done++;
                generate(left, right, up, index);
                generate(left, right, index, down);
            }
        }

    }

    public void buildVerticalWall(int index, int bound, int up, int down) {
        //dorysowuje ścianę też na początku
        addWallVertically(index, up, down);
        Random random = new Random();
        makeHoleInVertWall(up, bound, index, random);
        ifRandomEqualsOneVert(index, bound, up, random);
    }

    public void addWallVertically(int index, int up, int down) {
        for (int i = up; i < down; i++) {
            maze.set(index + size * i, "+");
        }
    }

    public void ifRandomEqualsOneVert(int index, int bound, int up, Random random) {
        if (random.nextInt(10) == 1) {
            makeHoleInVertWall(up, bound, index, random);
        }
    }

    private void makeHoleInVertWall(int up, int bound, int index, Random random) {
        int holeIndex = up + random.nextInt(bound);
        while (holeIndex % 2 == 0) {
            holeIndex = up + random.nextInt(bound);
        }
        maze.set(index + holeIndex * size, "0");
    }

    public void buildHorizontalWall(int index, int bound, int left, int right) {
        //dorysowuje ścianę też na początku
        addWallHorizonatlly(index, left, right);
        Random random = new Random();
        makeHoleInHorzWall(left, bound, index, random);
        ifRandomEqualsOneHoriz(index, bound, left, random);
    }

    public void addWallHorizonatlly(int index, int left, int right) {
        for (int i = left; i < right; i++) {
            maze.set(index * size + i, "+");
        }
    }

    public void ifRandomEqualsOneHoriz(int index, int bound, int left, Random random) {
        if(random.nextInt(10) == 1){
            makeHoleInHorzWall(left, bound, index, random);
        }
    }



    private void makeHoleInHorzWall(int left, int bound, int index, Random random) {
        int holeIndex = left + random.nextInt(bound);
        while (holeIndex % 2 == 0) {
            holeIndex = left + random.nextInt(bound);
        }
        maze.set(index * size + holeIndex, "0");
    }

    public int getSize() {
        return size;
    }

    public List<String> getMaze() {
        return maze;
    }
}
