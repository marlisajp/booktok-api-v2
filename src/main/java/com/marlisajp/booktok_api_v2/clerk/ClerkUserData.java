package com.marlisajp.booktok_api_v2.clerk;

public class ClerkUserData {
    private String clerkId;
    private String emailAddress;
    private String username;

    public ClerkUserData(String clerkId, String emailAddress, String username) {
        this.clerkId = clerkId;
        this.emailAddress = emailAddress;
        this.username = username;
    }

    public String getClerkId() {
        return clerkId;
    }

    public void setClerkId(String clerkId) {
        this.clerkId = clerkId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String clerkId;
        private String emailAddress;
        private String username;

        public Builder clerkId(String clerkId){
            this.clerkId = clerkId;
            return this;
        }

        public Builder emailAddress(String emailAddress){
            this.emailAddress = emailAddress;
            return this;
        }

        public Builder username(String username){
            this.username = username;
            return this;
        }

        public ClerkUserData build(){
            return new ClerkUserData(clerkId, emailAddress, username);
        }
    }

    @Override
    public String toString() {
        return "ClerkUserData{" +
                "clerkId='" + clerkId + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
