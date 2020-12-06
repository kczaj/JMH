package benchmark;

import org.openjdk.jmh.annotations.*;
import program.Maze;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BenchmarkBuildVerticalWall {

    @State(Scope.Benchmark)
    public static class MyState {

        public Maze maze;
        public Maze mazeWithWallVertically;
        public Maze mazeWithHole;

        public int size = 10;
        public Random random = new Random();


        @Setup(Level.Iteration)
        public void doMaze() {
            maze = new Maze(size);
            mazeWithWallVertically = new Maze(size);
            mazeWithHole = new Maze(size);

            maze.prepare();
            mazeWithWallVertically.prepare();
            mazeWithHole.prepare();

            mazeWithWallVertically.addWallVertically(4, 0, 20);
            mazeWithHole.addWallVertically(4, 0, 20);

            mazeWithHole.makeHoleInVertWall(0,20,3, random);
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
    public void testAddWallVertically(MyState state) {
        state.maze.addWallVertically(4,0,20);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1, warmups = 4)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 2)
    public void testMakeHoleInVertWall(MyState state) {
        state.mazeWithWallVertically.makeHoleInVertWall(0,20,3, state.random);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1, warmups = 4)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 2)
    public void testIfRandomEqualsOneVert(MyState state) {
        state.mazeWithHole.ifRandomEqualsOneVert(11, 3, 0, state.random);
    }

}