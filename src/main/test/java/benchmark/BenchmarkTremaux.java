package benchmark;
import org.openjdk.jmh.annotations.*;
import program.Maze;
import tremaux.Tremaux;

import java.util.concurrent.TimeUnit;

public class BenchmarkTremaux {

    @State(Scope.Benchmark)
    public static class TremauxState {
        public Maze maze;
        public int size = 10;

        public Tremaux tr1;
        public Tremaux tr2;

        @Setup(Level.Iteration)
        public void prepareMaze() {
            maze = new Maze(size);
            maze.generateMain();
            tr1 = new Tremaux(maze);
            tr2 = new Tremaux(maze);
            tr2.performMazeSolve(2 * size + 1);
        }

        @TearDown(Level.Iteration)
        public void confirmTearDown() {
            System.out.println("Tear down!");
        }
    }

    // performMazeSolve
    // startSolving

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1, warmups = 4)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 2)
    public void testPerformMazeSolve(TremauxState trState) {
        trState.tr1.performMazeSolve(2 * trState.size + 1);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1, warmups = 4)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 2)
    public void testStartSolving(TremauxState trState) {
        trState.tr2.drawPath(2 * trState.size + 1);
    }
}
