package integration.message;

public class Message {

    private String clientURL;
    private MessageType messageType;
    private String message;

    public Message(String clientURL, MessageType messageType, String message) {
        this.clientURL = clientURL;
        this.messageType = messageType;
        this.message = message;
    }

    public String getClientURL() {
        return clientURL;
    }

    public void setClientURL(String clientURL) {
        this.clientURL = clientURL;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
