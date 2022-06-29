package integration.message;

public enum MessageType {

    GEOLOCATION("geolocation"),
    GENERIC("generic");

    public String messageType;

    MessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageType() {
        return messageType;
    }

}
