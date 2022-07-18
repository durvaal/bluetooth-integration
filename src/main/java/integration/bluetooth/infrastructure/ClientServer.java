package integration.bluetooth.infrastructure;

import integration.bluetooth.domain.message.Message;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.microedition.io.Connector;
import javax.obex.*;

/**
 * Classe responsável por enviar as mensagens aos dispositivos.
 * @author Paulo Lima (durvaal - GitHub user)
 */
public class ClientServer {

    /**
     * A partir das informações de URL de conexão com dispositivo, tipo da mensagem e conteúdo será enviada a mensagem.
     * @param message Mensagem que será enviada ao dispositivo.
     */
    public void sendMessage(Message message) {
        try {
            System.out.println("Connecting to service URL " + message.getServiceConnectionURL());

            ClientSession clientSession = (ClientSession) Connector.open(message.getServiceConnectionURL());
            HeaderSet hsConnectReply = clientSession.connect(null);

            if (hsConnectReply.getResponseCode() != ResponseCodes.OBEX_HTTP_OK) {
                System.out.println("Failed to connect");
                return;
            }

            Path path = Paths.get(getClass().getClassLoader().getResource(message.getTemplateFile()).getPath());
            byte data[] = message.getBytesFiles(path, message.getContent());

            HeaderSet hsOperation = clientSession.createHeaderSet();
            hsOperation.setHeader(HeaderSet.NAME, message.getTemplateFile());
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
            System.err.println(e.getMessage());
        }
    }

}