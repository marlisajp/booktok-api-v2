package com.marlisajp.booktok_api_v2.clerk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.svix.exceptions.WebhookVerificationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;

@RestController
@RequestMapping("/api/webhooks")
public class ClerkWebhookController {
    private final ObjectMapper objectMapper;
    private final ClerkWebhookService clerkWebhookService;
    private final ClerkWebhookUtil clerkWebhookUtil;

    public ClerkWebhookController(ObjectMapper objectMapper, ClerkWebhookService clerkWebhookService, ClerkWebhookUtil clerkWebhookUtil) {
        this.objectMapper = objectMapper;
        this.clerkWebhookService = clerkWebhookService;
        this.clerkWebhookUtil = clerkWebhookUtil;
    }

    @PostMapping
    public ResponseEntity<String> handleWebhook(
            @RequestHeader org.springframework.http.HttpHeaders springHeaders,
            @RequestBody String payload) throws WebhookVerificationException, JsonProcessingException {
        HttpHeaders netHttpHeaders = clerkWebhookUtil.convertHeaders(springHeaders);

        ClerkWebhookResponse verificationResult = clerkWebhookUtil.verifyPayload(payload, netHttpHeaders);
        if (verificationResult == ClerkWebhookResponse.WEBHOOK_PAYLOAD_VERIFIED) {
            JsonNode jsonNode = objectMapper.readTree(payload);
            String eventType = jsonNode.get("type").asText();
            JsonNode userData = jsonNode.get("data");

            return switch (eventType) {
                case "user.created" -> {
                    ClerkWebhookResponse createResponse = clerkWebhookService.createUserInDatabase(userData);
                    yield ResponseEntity.ok("Webhook processed: " + createResponse.getMessage());
                }
                case "user.deleted" -> {
                    ClerkWebhookResponse deleteResponse = clerkWebhookService.deleteUserFromDatabase(userData);
                    yield ResponseEntity.ok("Webhook processed: " + deleteResponse.getMessage());
                }
                case "user.updated" -> {
                    ClerkWebhookResponse updateResponse = clerkWebhookService.updateUserInDatabase(userData);
                    yield ResponseEntity.ok("Webhook processed: " + updateResponse.getMessage());
                }
                default -> ResponseEntity.ok("Webhook processed with event: " + eventType);
            };
        }
        return ResponseEntity.ok("Webhook processed");
    }
}


