package benchmark;

import org.openjdk.jmh.annotations.*;
import program.Maze;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BenchmarkHorizontalWall {

    @State(Scope.Benchmark)
    public static class MyStateHor {

        public Maze maze;
        public Maze mazeWithWall;
        public Maze mazeWithHole;
        public int size = 10;
        public Random random;

        @Setup(Level.Iteration)
        public void doMaze() {
            maze = new Maze(size);
            mazeWithWall = new Maze(size);
            mazeWithHole = new Maze(size);
            maze.prepare();
            mazeWithWall.prepare();
            mazeWithWall.addWallHorizonatlly(3, 0, size * 2);
            mazeWithHole.prepare();
            mazeWithHole.addWallHorizonatlly(3, 0, size * 2);
            random = new Random();
            mazeWithHole.makeHoleInHorzWall(0, size * 2, 7, random);
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
    public void testAddWall(MyStateHor state){
        state.maze.addWallHorizonatlly(3, 0, state.size * 2);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1, warmups = 4)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 2)
    public void testMakeHole(MyStateHor state){
        state.mazeWithWall.makeHoleInHorzWall(0, state.size * 2, 7, state.random);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1, warmups = 4)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 2)
    public void testMakeAdditionalHole(MyStateHor state){
        state.mazeWithHole.ifRandomEqualsOneHoriz(9, state.size * 2, 0, state.random);
    }

}
