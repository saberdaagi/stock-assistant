package com.stockassistant.ai.client.controller;

import com.stockassistant.ai.client.model.PromptRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatClient chatClient;

    @PostMapping("/process")
    public ResponseEntity<String> processQuery(@RequestBody PromptRequest request) {
        log.info("Processing chat query: {}", request.prompt());
        String response = chatClient
                .prompt(request.prompt())
                .call()
                .content();
        log.info("Chat query processed successfully");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
