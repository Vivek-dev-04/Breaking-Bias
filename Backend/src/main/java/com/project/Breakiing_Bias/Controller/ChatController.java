package com.project.Breakiing_Bias.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Breakiing_Bias.DTO.ChatRequest;
import com.project.Breakiing_Bias.DTO.ChatResponse;
import com.project.Breakiing_Bias.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChatController {
    @Autowired
    private ChatService chatService;
    @PostMapping("/analyze")
    public ResponseEntity<?> showResponse(@RequestBody ChatRequest request) {
        try {
            String aiResponse = chatService.analyze(request.getRequest());

            System.out.println("AI RESPONSE: " + aiResponse);

            ChatResponse response = new ObjectMapper()
                    .readValue(aiResponse, ChatResponse.class);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }
}
