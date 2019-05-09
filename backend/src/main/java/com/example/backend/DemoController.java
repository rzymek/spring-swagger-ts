package com.example.backend;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@RestController
public class DemoController {

    @GetMapping("/user")
    public Sample getUser() {
        return new Sample();
    }
}

@JsonAutoDetect(fieldVisibility = ANY)
class Sample {
    public UUID id = UUID.randomUUID();
    public long timestamp = System.currentTimeMillis();
}
