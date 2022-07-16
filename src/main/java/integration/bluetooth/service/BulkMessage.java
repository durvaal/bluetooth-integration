package integration.bluetooth.service;

import integration.bluetooth.domain.device.Device;
import integration.bluetooth.domain.message.Message;
import integration.bluetooth.domain.message.MessageType;
import integration.bluetooth.exception.ServiceTypeException;
import integration.bluetooth.infrastructure.ClientServer;
import integration.bluetooth.infrastructure.ServiceType;

import java.util.List;

public interface BulkMessage {

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
