package com.marlisajp.booktok_api_v2.clerk;

public class ClerkUserData {
    private String clerkId;
    private String emailAddress;
    private String fullName;

    public ClerkUserData(String clerkId, String emailAddress, String fullName) {
        this.clerkId = clerkId;
        this.emailAddress = emailAddress;
        this.fullName = fullName;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String clerkId;
        private String emailAddress;
        private String fullName;

        public Builder clerkId(String clerkId){
            this.clerkId = clerkId;
            return this;
        }

        public Builder emailAddress(String emailAddress){
            this.emailAddress = emailAddress;
            return this;
        }

        public Builder fullName(String fullName){
            this.fullName = fullName;
            return this;
        }

        public ClerkUserData build(){
            return new ClerkUserData(clerkId, emailAddress, fullName);
        }
    }

    @Override
    public String toString() {
        return "ClerkUserData{" +
                "clerkId='" + clerkId + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
