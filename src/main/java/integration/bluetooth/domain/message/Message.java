package integration.bluetooth.domain.message;

public class Message {

    private String serviceConnectionURL;
    private MessageType messageType;
    private String content;

    public Message(String serviceConnectionURL, MessageType messageType, String content) {
        this.serviceConnectionURL = serviceConnectionURL;
        this.messageType = messageType;
        this.content = content;
    }

    public String getServiceConnectionURL() {
        return serviceConnectionURL;
    }

    public void setServiceConnectionURL(String serviceConnectionURL) {
        this.serviceConnectionURL = serviceConnectionURL;
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