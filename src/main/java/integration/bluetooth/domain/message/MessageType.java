package integration.bluetooth.domain.message;

/**
 * Classe responsável por representar os tipos de mensagens. Sendo GEOLOCATION é enviado uma mensagem em HTML para o dispositivo simulando a distância entre o dispositivo e o servidor.
 * Sendo GENERIC é enviado uma mensagem em HTML para o dispositivo simulando a mensagem que o servidor definir.
 * @author Paulo Lima (durvaal - GitHub user)
 */
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
