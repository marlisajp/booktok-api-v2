package com.marlisajp.booktok_api_v2.clerk;

public enum ClerkWebhookResponse {
    USER_CREATED_SUCCESS("USER_CREATED_SUCCESS"),
    USER_CREATED_FAILURE("USER_CREATED_FAILURE"),
    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS"),
    USER_DOESNT_EXIST("USER_DOESNT_EXIST"),
    USER_DELETED_SUCCESS("USER_DELETED_SUCCESS"),
    USER_DELETED_FAILURE("USER_DELETED_FAILURE"),
    USER_UPDATED_SUCCESS("USER_UPDATED_SUCCESS"),
    USER_UPDATED_FAILURE("USER_UPDATED_FAILURE"),
    WEBHOOK_PAYLOAD_VERIFIED("WEBHOOK_PAYLOAD_VERIFIED"),
    WEBHOOK_PAYLOAD_NOT_VERIFIED("WEBHOOK_PAYLOAD_NOT_VERIFIED"),
    USER_AUTHORIZED("USER_AUTHORIZED"),
    USER_NOT_AUTHORIZED("USER_NOT_AUTHORIZED"),
    POST_DELETED_SUCCESS("POST_DELETED_SUCCESS");

    private final String message;

    ClerkWebhookResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

