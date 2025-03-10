package com.marlisajp.booktok_api_v2.clerk;

import com.fasterxml.jackson.databind.JsonNode;
import com.svix.Webhook;
import com.svix.exceptions.WebhookVerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClerkWebhookUtil {
    private static final Logger logger = LoggerFactory.getLogger(ClerkWebhookUtil.class);

    @Value("${CLERK_SIGNING_SECRET:}")
    private String CLERK_SIGNING_SECRET;

    public ClerkUserData extractUserData(JsonNode userData){
        if(userData == null || !userData.has("id")){
            logger.info("userData is null or id property does not exist: {} ", userData);
            throw new IllegalArgumentException("User Data must contain Id field and not be null");
        }

        String clerkId = userData.get("id").asText();
        String emailAddress = "";
        String fullName = "";

        if (userData.has("email_addresses") && userData.get("email_addresses").isArray()
                && !userData.get("email_addresses").isEmpty()) {
            emailAddress = userData.get("email_addresses").get(0).get("email_address").asText();
        }

        if (userData.has("fullName")) {
            fullName = userData.get("fullName").asText();
        } else {
            String firstName = userData.has("first_name") ? userData.get("first_name").asText() : "";
            String lastName = userData.has("last_name") ? userData.get("last_name").asText() : "";
            fullName = (firstName + " " + lastName).trim();
        }

        return ClerkUserData.builder()
                .clerkId(clerkId)
                .emailAddress(emailAddress)
                .fullName(fullName).build();
    }

    public HttpHeaders convertHeaders(org.springframework.http.HttpHeaders springHeaders){
        if(springHeaders == null){
            logger.info("spring headers in converter cant be null: {}", springHeaders);
            throw new IllegalArgumentException("spring headers cannot be null");
        }

        Map<String, List<String>> headerMap = new HashMap<>(springHeaders);
        return HttpHeaders.of(headerMap, (key, value) -> true);
    }

    public ClerkWebhookResponse verifyPayload(String payload, HttpHeaders httpHeaders)
            throws WebhookVerificationException {
        if (CLERK_SIGNING_SECRET == null || CLERK_SIGNING_SECRET.isEmpty()) {
            throw new IllegalArgumentException("CLERK_SIGNING_SECRET must be set and non-empty");
        }

        Webhook webhook = new Webhook(CLERK_SIGNING_SECRET);
        webhook.verify(payload, httpHeaders);
        return ClerkWebhookResponse.WEBHOOK_PAYLOAD_VERIFIED;
    }
}
