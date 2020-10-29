package luxe.chaos.micrometer.prometheus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledDemo {


    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledDemo.class);

    @Autowired
    private CharlesMetrics charlesMetrics;

    private long count1 = 0;
    private long count2 = 0;
    private long count3 = 0;

    @Async("One")
    @Scheduled(fixedDelay = 1000)
    public void increment1() {
        count1 ++;
        charlesMetrics.counter1.increment();
        charlesMetrics.map.put("x", Double.valueOf(count1));
        LOGGER.info("increment1 count : {}", count1);
    }

    @Async("Two")
    @Scheduled(fixedDelay = 10000)
    public void increment2() {
        count2 ++;
        charlesMetrics.counter2.increment();
        LOGGER.info("increment2 count : {}", count2);
    }

    @Async("Three")
    @Scheduled(fixedDelay = 20000)
    public void increment3() {
        count3 ++;
        charlesMetrics.counter3.increment();
        LOGGER.info("increment3 count : {}", count3);
    }
}
