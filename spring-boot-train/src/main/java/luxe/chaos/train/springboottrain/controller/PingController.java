package luxe.chaos.train.springboottrain.controller;

import com.sun.org.glassfish.gmbal.ParameterNames;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@RestController
public class PingController {

    public static final ConcurrentHashMap<String, DeferredResult<Map<String, String>>> MAP =
            new ConcurrentHashMap<>();

    @GetMapping("/v1/ping")
    public Map<String, String> ping() {
        return Collections.singletonMap("message", "Pong");
    }

    @GetMapping("/v1/async01")
    public Callable<Map<String, String>> async01() {

        Callable<Map<String, String>> callback = new Callable<Map<String, String>>() {

            @Override
            public Map<String, String> call() throws Exception {
                System.out.println("子线程开始 ……"+ Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(3L);
                System.out.println("子线程结束 ……"+ Thread.currentThread());
                return Collections.singletonMap("message", "hello world!");
            }
        };

        System.out.println("主线程结束！");
        return callback;
    }

    @GetMapping("/v1/create-order")
    public DeferredResult<Map<String, String>> createOrder(@RequestParam("id") String id) {
        // 设置超时时间 10 秒
        DeferredResult<Map<String, String>> deferredResult = new DeferredResult<>(30 * 1000L, "命令执行超时");
        MAP.put(id, deferredResult);
        System.out.println("map size =====> "+ MAP.size());
        return deferredResult;
    }

    @GetMapping("/v1/execute-order")
    public Map<String, String> completeOrder(@RequestParam("id") String id) {
        System.out.println("map size =====> "+ MAP.size());
        DeferredResult<Map<String, String>> deferredResult = MAP.remove(id);
        if (deferredResult != null) {
            deferredResult.setResult(Collections.singletonMap("id", id));
            return Collections.singletonMap("message" , "OK");
        }
        System.out.println("map size =====> "+ MAP.size());
        return Collections.singletonMap("message", "ERROR");

    }
}
