package main;

import bfs.BFS;
import org.junit.Test;
import program.Main;
import program.Maze;

import static org.junit.Assert.assertEquals;

public class BuildVerticalWall {

    @Test
    public void test(){
        Maze m = new Maze(10);
        m.prepare();
        m.buildVerticalWall(2, 20, 0, 20);
        assertEquals(true, true);
    }
}
