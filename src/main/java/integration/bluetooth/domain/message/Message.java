package integration.bluetooth.domain.message;

import integration.bluetooth.exception.MessageContentException;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Classe responsável por representar os atributos de uma mensagem necessários para os envios das mensagens.
 * @author Paulo Lima (durvaal - GitHub user)
 */
public class Message implements MessageAction {

    private String serviceConnectionURL;
    private String content;

    public String getServiceConnectionURL() {
        return serviceConnectionURL;
    }

    public void setServiceConnectionURL(String serviceConnectionURL) {
        this.serviceConnectionURL = serviceConnectionURL;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public byte[] getBytesFiles(Path path, String content) throws MessageContentException, IOException {
        return new byte[0];
    }

    @Override
    public String getTemplateFile() {
        return null;
    }

}
