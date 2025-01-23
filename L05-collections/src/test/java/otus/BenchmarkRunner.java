package otus;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class BenchmarkRunner {
    private int a;
    private int b;

    @Setup
    public void setup() {
        a = 3;
        b = 7;
    }

    @Benchmark
    public int benchmarkAddition() {
        return a + b;
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
