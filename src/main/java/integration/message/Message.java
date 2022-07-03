package integration.message;

public class Message {

    private String clientURL;
    private MessageType messageType;
    private String content;

    public Message(String clientURL, MessageType messageType, String content) {
        this.clientURL = clientURL;
        this.messageType = messageType;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
