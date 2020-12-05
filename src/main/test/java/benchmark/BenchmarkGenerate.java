package benchmark;
import org.openjdk.jmh.annotations.*;
import program.Maze;

import java.util.concurrent.TimeUnit;

public class BenchmarkGenerate {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1, warmups = 4)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 2)
    public void testPrepare() {
        Maze maze = new Maze(10);
        maze.prepare();
    }

}
