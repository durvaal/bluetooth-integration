package integration.bluetooth.service;

import integration.bluetooth.domain.device.Device;
import integration.bluetooth.domain.message.Message;
import integration.bluetooth.domain.message.MessageType;
import integration.bluetooth.exception.ServiceTypeException;
import integration.bluetooth.infrastructure.ClientServer;
import integration.bluetooth.domain.service.ServiceType;

import java.util.List;

/**
 * Interface responsável por implementar o envio de mensagens em paralelo para um ou mais dispositivos.
 * @author Paulo Lima (durvaal - GitHub user)
 */
public interface BulkMessage {

    /**
     * Envia as mensagens em paralelo para os dispositivos informados.
     * @param messageType tipo do template da mensagem.
     * @param serviceType o tipo de serviço Bluetooth utilizado, atualmente suporta apenas o tipo OBEX_OBJECT_PUSH.
     * @param content o conteúdo da mensagem, apenas o tipo de mensagem GENERIC faz uso desse atributo em seu template.
     * @param devicesToSendMessage os dispositivos que receberão as mensagens.
     * @throws ServiceTypeException
     */
    default void send(MessageType messageType, ServiceType serviceType, String content, List<Device> devicesToSendMessage) throws ServiceTypeException {
            if (!serviceType.equals(ServiceType.OBEX_OBJECT_PUSH)) {
                throw new ServiceTypeException("Tipo de serviço não suportado.");
            }

            ClientServer clientServer = new ClientServer();

            System.out.println("\nInit send bulk message to " + devicesToSendMessage.size() + " devices.");

            devicesToSendMessage.parallelStream().forEach(device -> {
                Message message = new Message(device.getServiceConnectionURL(), messageType, content);
                clientServer.sendMessage(message);
            });

            System.out.println("Finish send bulk message to devices.\n");
    }

}
