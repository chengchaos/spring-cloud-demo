package luxe.chaos.demo.prometheus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class EchoController {

    @GetMapping("/v1/echo")
    public ResponseEntity<String> echo(HttpServletRequest request, HttpServletResponse response) {
        String queryString = request.getQueryString();
        if (queryString != null && queryString.length() > 0) {
            return ResponseEntity.ok(queryString);
        }
        return ResponseEntity.ok("What do I need ?");
    }
}
