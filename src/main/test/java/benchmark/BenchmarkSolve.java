package benchmark;
import bfs.BFS;
import org.openjdk.jmh.annotations.*;
import program.Maze;

import java.util.concurrent.TimeUnit;

public class BenchmarkSolve {

    @State(Scope.Benchmark)
    public static class BfsState {
        public Maze maze;
        public BFS bfsNew;
        public BFS bfsWithGraph;
        public BFS bfsSolved;
        public int size = 10;

        @Setup(Level.Iteration)
        public void prepareMaze() {
            Maze maze = new Maze(size);
            maze.generateMain();
            BFS bfs = new BFS(maze);
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
    public void preparationTime(BfsState bfsState) {
        Maze maze = new Maze(10);
        maze.generateMain();
        BFS bfs = new BFS(maze);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1, warmups = 4)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 2)
    public void testMakeGraph(BfsState bfsState) {
        Maze maze = new Maze(10);
        maze.generateMain();
        BFS bfs = new BFS(maze);
        bfs.makeGraph();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1, warmups = 4)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 2)
    public void testSolve(BfsState bfsState) {
        Maze maze = new Maze(10);
        maze.generateMain();
        BFS bfs = new BFS(maze);
        bfs.makeGraph();
        bfs.solveMaze();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1, warmups = 4)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 2)
    public void testPrint(BfsState bfsState) {
        Maze maze = new Maze(10);
        maze.generateMain();
        BFS bfs = new BFS(maze);
        bfs.makeGraph();
        bfs.solveMaze();
        bfs.printSolution();
    }
}
