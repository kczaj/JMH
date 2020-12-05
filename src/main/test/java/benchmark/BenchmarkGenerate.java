package benchmark;
import org.openjdk.jmh.annotations.*;
import program.Maze;

import java.util.concurrent.TimeUnit;

public class BenchmarkGenerate {

    @State(Scope.Benchmark)
    public static class MyState {

        public Maze maze;
        public Maze mazePrepared;
        public Maze mazeGenerated;
        public int size = 10;

        @Setup(Level.Iteration)
        public void doMaze() {
            maze = new Maze(size);
            mazePrepared = new Maze(size);
            mazeGenerated = new Maze(size);
            mazePrepared.prepare();
            mazeGenerated.prepare();
            mazeGenerated.generate(0, mazeGenerated.getSize() - 1, 0,  mazeGenerated.getSize() - 1);
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
    public void testPrepare(MyState state) {
        state.maze.prepare();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1, warmups = 4)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 2)
    public void testGenerate(MyState state) {
        state.mazePrepared.generate(0, state.mazePrepared.getSize() - 1, 0, state.mazePrepared.getSize() - 1);
    }


    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1, warmups = 4)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 2)
    public void testMakeEntranceAndExit(MyState state){
        state.mazeGenerated.makeEntranceAndExit();
    }

}
