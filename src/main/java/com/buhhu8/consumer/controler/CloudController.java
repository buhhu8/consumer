package com.buhhu8.consumer.controler;

import com.buhhu8.consumer.service.ConsumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CloudController {

    @Value("${userRole}")
    private String role;
    private final ConsumerService consumerService;

    @GetMapping
    public String whoami() {
        return String.format("You're  and you'll become a(n) %s...\n", role);
    }

    @GetMapping("/read")
    public List<generated.KafkaRequest> getAll(){
        return consumerService.getAll();
    }
}
