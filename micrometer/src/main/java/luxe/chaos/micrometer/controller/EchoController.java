package luxe.chaos.micrometer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class EchoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EchoController.class);

    @GetMapping(value = "/v1/echo")
    public ResponseEntity<String> echo(HttpServletRequest request) {
        String query = request.getQueryString();

        if (query == null || query.length() == 0) {
            query = "What do we need?";
        }

        return ResponseEntity.ok().body(query);
    }
}
