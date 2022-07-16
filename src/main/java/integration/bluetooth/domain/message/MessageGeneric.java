package integration.bluetooth.domain.message;

import integration.bluetooth.exception.MessageContentException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Classe responsável por representar os atributos de uma mensagem do tipo GEOLOCATION.
 * @author Paulo Lima (durvaal - GitHub user)
 */
public class MessageGeneric extends Message {

    @Override
    public byte[] getBytesFiles(Path path, String content) throws MessageContentException, IOException {
        Charset charset = StandardCharsets.UTF_8;
        byte[] bytesFile = null;

        try {
            if (content == null) {
                throw new MessageContentException("O conteúdo da mensagem não pode ser nulo para o tipo de mensagem GENERIC.");
            }

            String templateContent = new String(Files.readAllBytes(path), charset);
            templateContent = templateContent.replaceAll("template_message", content);
            bytesFile = templateContent.getBytes(charset);
        } catch (MessageContentException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return bytesFile;
    }

    @Override
    public String getTemplateFile() {
        return "generic-message.html";
    }

}
