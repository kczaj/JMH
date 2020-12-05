package benchmark;
import org.openjdk.jmh.annotations.*;
import program.Maze;

import java.util.concurrent.TimeUnit;

public class BenchmarkSolve {

    @State(Scope.Benchmark)
    public static class MyState {

        public Maze maze;
        @Setup(Level.Trial)
        public void doMaze() {
            maze = new Maze(10);
        }
    }


    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1, warmups = 4)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 2)
    public void testMakeGraph(MyState state) {
        state.maze.prepare();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1, warmups = 4)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 2)
    public void testSolveMaze() {
        Maze maze = new Maze(10);
        maze.prepare();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1, warmups = 4)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 2)
    public void testPrintSolution() {
        Maze maze = new Maze(10);
        maze.prepare();
    }
}
