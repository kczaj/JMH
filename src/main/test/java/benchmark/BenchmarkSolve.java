package benchmark;
import bfs.BFS;
import org.openjdk.jmh.annotations.*;
import program.Maze;

import java.util.concurrent.TimeUnit;

public class BenchmarkSolve {

    @State(Scope.Benchmark)
    public static class MyState {

        public Maze maze;
        public BFS bfsNew;
        public BFS bfsWithGraph;
        public BFS bfsSolved;
        public int size = 10;

        @Setup(Level.Iteration)
        public void doMaze() {
            maze = new Maze(size);
            maze.generateMain();
            bfsNew = new BFS(maze);
            bfsWithGraph = new BFS(maze);
            bfsSolved = new BFS(maze);

            bfsWithGraph.makeGraph();
            bfsSolved.makeGraph();

            bfsSolved.solveMaze();
        }

        @TearDown(Level.Iteration)
        public void doTearDown() {
            System.out.println("Tear down!");
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1, warmups = 4)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 2)
    public void testMakeGraph(MyState state) {
        state.bfsNew.makeGraph();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1, warmups = 4)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 2)
    public void testSolveMaze(MyState state) {
        state.bfsWithGraph.solveMaze();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1, warmups = 4)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 2)
    public void testPrintSolution(MyState state) {
        state.bfsSolved.printSolution();
    }
}
