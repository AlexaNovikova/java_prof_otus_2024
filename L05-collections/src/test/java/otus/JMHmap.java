package otus;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.openjdk.jmh.annotations.Mode.SingleShotTime;

import java.util.HashMap;
import java.util.Map;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import ru.otus.MyMapInt;

@State(Scope.Thread)
@BenchmarkMode(SingleShotTime)
@OutputTimeUnit(MILLISECONDS)
public class JMHmap {
    private final int mapSize = 200_000;
    private final String keyStr = "k";
    private MyMapInt myMap;
    private Map<String, Integer> hashMap;

    public static void main(String[] args) throws RunnerException {
        var opt = new OptionsBuilder()
                .include(JMHmap.class.getSimpleName())
                .forks(3)
                .build();
        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        myMap = new MyMapInt(mapSize * 2);
        hashMap = new HashMap<>(mapSize);
    }

    @Benchmark
    public long myMapTest() {
        for (var idx = 0; idx < mapSize; idx++) {
            myMap.put(keyStr + idx, idx);
        }

        int summ = 0;
        for (var idx = 0; idx < mapSize; idx++) {
            summ += myMap.get(keyStr + idx);
        }
        return summ;
    }

    @Benchmark
    public long hashMapTest() {
        for (var idx = 0; idx < mapSize; idx++) {
            hashMap.put(keyStr + idx, idx);
        }

        int summ = 0;
        for (var idx = 0; idx < mapSize; idx++) {
            summ += hashMap.get(keyStr + idx);
        }
        return summ;
    }
}
