package com.marlisajp.booktok_api_v2.clerk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marlisajp.booktok_api_v2.exception.UserCreationException;
import com.svix.exceptions.WebhookVerificationException;
import org.springframework.http.HttpStatus;
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
            @RequestBody String payload) throws WebhookVerificationException {

        HttpHeaders netHttpHeaders = clerkWebhookUtil.convertHeaders(springHeaders);

        try {
            ClerkWebhookResponse verificationResult = clerkWebhookUtil.verifyPayload(payload,netHttpHeaders);

            if(verificationResult == ClerkWebhookResponse.WEBHOOK_PAYLOAD_VERIFIED){
                JsonNode jsonNode = objectMapper.readTree(payload);
                String eventType = jsonNode.get("type").asText();
                JsonNode userData = jsonNode.get("data");

                if ("user.created".equals(eventType)) {
                    ClerkWebhookResponse eventResponse = clerkWebhookService.createUserInDatabase(userData);
                    return ResponseEntity.ok("Webhook processed: " + eventResponse.getMessage());
                }

                if("user.deleted".equals(eventType)){
                    ClerkWebhookResponse eventResponse = clerkWebhookService.deleteUserFromDatabase(userData);
                    return ResponseEntity.ok("Webhook processed: " + eventResponse.getMessage());
                }

                if("user.updated".equals(eventType)){
                    ClerkWebhookResponse eventResponse = clerkWebhookService.updateUserInDatabase(userData);
                    return ResponseEntity.ok("Webhook processed: " + eventResponse.getMessage());
                }

                return ResponseEntity.ok("Webhook processed.");
            }
        } catch (WebhookVerificationException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid signature: " + e.getMessage());
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing JSON: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error performing webhook operation" + e.getMessage());
        }

        return ResponseEntity.ok("Webhook processed");
    }
}


