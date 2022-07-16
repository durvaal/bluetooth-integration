package integration.bluetooth.domain.message;

import integration.bluetooth.exception.MessageContentException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Classe responsável por representar os atributos de uma mensagem do tipo GENERIC.
 * @author Paulo Lima (durvaal - GitHub user)
 */
public class MessageGeolocation extends Message {

    @Override
    public byte[] getBytesFiles(Path path, String content) throws MessageContentException, IOException {
        byte[] bytesFile = null;

        try {
            bytesFile = Files.readAllBytes(path);
        } catch (MessageContentException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return bytesFile;
    }

    @Override
    public String getTemplateFile() {
        return "geolocation-message.html";
    }

}
