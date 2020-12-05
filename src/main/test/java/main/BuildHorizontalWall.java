package main;

import org.junit.Test;
import program.Maze;

import static org.junit.Assert.assertEquals;

public class BuildHorizontalWall {

    @Test
    public void test(){
        Maze m = new Maze(10);
        m.prepare();
        m.buildHorizontalWall(2, 20, 0, 20);
        assertEquals(true, true);
    }
}
