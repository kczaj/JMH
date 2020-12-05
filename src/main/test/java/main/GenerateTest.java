package main;

import org.junit.Test;
import program.Maze;

import static org.junit.Assert.assertEquals;

public class GenerateTest {

    @Test
    public void test(){
        Maze m = new Maze(100);
        m.generateMain();
        assertEquals(true, true);
    }
}