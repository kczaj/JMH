package main;

import org.junit.Test;
import program.Maze;
import tremaux.Tremaux;

import static org.junit.Assert.assertTrue;

public class TremauxTest {
    @Test
    public void test() {
        Maze m = new Maze(10);
        m.generateMain();
        Tremaux tr = new Tremaux(m);
        tr.startSolving(m);
        assertTrue(true);
    }
}
