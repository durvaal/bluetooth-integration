package integration.bluetooth.infrastructure;

import integration.bluetooth.domain.message.Message;
import integration.bluetooth.domain.message.MessageType;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.microedition.io.Connector;
import javax.obex.*;

public class ClientServer {

    public void sendMessage(Message message) {
        try {
            System.out.println("Connecting to service URL " + message.getServiceConnectionURL());

            ClientSession clientSession = (ClientSession) Connector.open(message.getServiceConnectionURL());
            HeaderSet hsConnectReply = clientSession.connect(null);

            if (hsConnectReply.getResponseCode() != ResponseCodes.OBEX_HTTP_OK) {
                System.out.println("Failed to connect");
                return;
            }

            String templateFile = message.getMessageType().equals(MessageType.GEOLOCATION) ? "geolocation-message.html" : "generic-message.html";
            Path path = Paths.get(getClass().getClassLoader().getResource(templateFile).getPath());
            byte data[] = getBytesFiles(path, message.getMessageType(), message.getContent());

            HeaderSet hsOperation = clientSession.createHeaderSet();
            hsOperation.setHeader(HeaderSet.NAME, templateFile);
            hsOperation.setHeader(HeaderSet.TYPE, "text/html");
            hsOperation.setHeader(HeaderSet.LENGTH, Long.valueOf(data.length));

            //Create PUT Operation
            Operation putOperation = clientSession.put(hsOperation);

            // Send some text to server
            OutputStream os = putOperation.openOutputStream();
            os.write(data);
            os.close();

            putOperation.close();

            clientSession.disconnect(null);

            clientSession.close();

            System.out.println("Message sent");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * In the case of the MessageType.GENERIC, the html template has a variable that is changed according to the content of the message informed when sending the bulk messages
     */
    public byte[] getBytesFiles(Path path, MessageType messageType, String content) {
        Charset charset = StandardCharsets.UTF_8;
        byte[] bytesFile = null;

        try {
            if (messageType.equals(MessageType.GENERIC)) {
                String templateContent = new String(Files.readAllBytes(path), charset);
                templateContent = templateContent.replaceAll("template_message", content);
                bytesFile = templateContent.getBytes(charset);
            } else {
                bytesFile = Files.readAllBytes(path);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return bytesFile;
    }

}