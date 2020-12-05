package tremaux;

import program.Maze;

import java.util.List;

public class Explorer {
    private int x;
    private int y;
    private int direction;

    public Explorer(Maze maze) {
        List<String> mazeMap = maze.getMaze();
        int size = maze.getSize();
        for (int i = 1; i < size; i += 2) {
            if (mazeMap.get(i).compareTo("#") == 0) {
                this.x = i;
            }
        }
        this.y = 1;
        this.direction = -1;
    }


    public int incX() {
        this.x+=2;
        return this.x;
    }

    public int decrX() {
        this.x-=2;
        return this.x;
    }

    public int incY() {
        this.y+=2;
        return this.y;
    }

    public int decrY() {
        this.y-=2;
        return this.y;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
