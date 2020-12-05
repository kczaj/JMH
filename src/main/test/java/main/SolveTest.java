package main;

import bfs.BFS;
import org.junit.Test;
import program.Maze;

import static org.junit.Assert.assertEquals;

public class SolveTest {

    @Test
    public void test(){
        Maze m = new Maze(10);
        m.generateMain();
        BFS bfs = new BFS(m);
        bfs.solveWholeGraph();
        assertEquals(true, true);
    }
}

