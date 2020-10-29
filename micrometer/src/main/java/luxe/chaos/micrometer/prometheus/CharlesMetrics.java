package luxe.chaos.micrometer.prometheus;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 实现 MeterBinder 接口
 */
@Component
public class CharlesMetrics implements MeterBinder {


    Counter counter1;
    Counter counter2;
    Counter counter3;

    Map<String, Double> map;

    public CharlesMetrics() {
        super();
        this.map = new HashMap<>();
    }


    @Override
    public void bindTo(MeterRegistry meterRegistry) {

        this.counter1 = Counter.builder("charles.demo.counter")
                .tags(new String[]{"name", "count1"})
                .description("This is the first counter")
                .register(meterRegistry);
        this.counter2 = Counter.builder("charles.demo.counter")
                .tags(new String[]{"name", "count2"})
                .description("This is the second counter")
                .register(meterRegistry);
        this.counter3 = Counter.builder("charles.demo.counter")
                .tags(new String[]{"name", "count3"})
                .description("this is the third counter")
                .register(meterRegistry);

        Gauge.builder("charles.demo.gauge", map,
                x -> x.get("x"))
                .tags("name", "gauge1")
                .description("this is Gauge")
                .register(meterRegistry);
    }
}
