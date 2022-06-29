package integration.bluetooth;

import integration.message.Message;
import integration.message.MessageType;

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
            System.out.println("Connecting to server " + message.getClientURL());

            ClientSession clientSession = (ClientSession) Connector.open(message.getClientURL());
            HeaderSet hsConnectReply = clientSession.connect(null);

            if (hsConnectReply.getResponseCode() != ResponseCodes.OBEX_HTTP_OK) {
                System.out.println("Failed to connect");
                return;
            }

            String templateFile = message.getMessageType().equals(MessageType.GEOLOCATION) ? "geolocation-message.html" : "generic-message.html";
            Path path = Paths.get(getClass().getClassLoader().getResource(templateFile).getPath());
            byte data[] = getBytesFiles(path, message.getMessageType(), message.getMessage());

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

            System.out.println("Mensagem enviada");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getBytesFiles(Path path, MessageType messageType, String message) {
        Charset charset = StandardCharsets.UTF_8;
        byte[] bytesFile = null;

        try {
            if (messageType.equals(MessageType.GENERIC)) {
                String content = new String(Files.readAllBytes(path), charset);
                content = content.replaceAll("template_message", message);
                bytesFile = content.getBytes(charset);
            } else {
                bytesFile = Files.readAllBytes(path);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return bytesFile;
    }

}