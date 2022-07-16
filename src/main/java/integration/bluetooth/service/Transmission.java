package integration.bluetooth.service;

import integration.bluetooth.domain.device.Device;
import integration.bluetooth.domain.message.MessageType;
import integration.bluetooth.exception.ServiceTypeException;
import integration.bluetooth.infrastructure.RemoteServiceDiscovery;
import integration.bluetooth.domain.service.ServiceType;

import javax.bluetooth.ServiceRecord;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Classe responsável por realizar a transmissão das mensagens a partir da busca por dispositivos ou do input manual do endereços bluetooth.
 * @author Paulo Lima (durvaal - GitHub user)
 */
public class Transmission implements BulkMessage {

    /**
     * Permite transmitir mensagem diretamente para os dispositivos desejados.
     * @param messageType o tipo do template da mensagem.
     * @param serviceType o tipo de serviço Bluetooth utilizado, atualmente suporta apenas o tipo OBEX_OBJECT_PUSH.
     * @param content o conteúdo da mensagem, apenas o tipo de mensagem GENERIC faz uso desse atributo em seu template.
     * @param bluetoothMacAddresses os endereços bluetooth dos dispositivos, podendo ser um ou mais (varargs).
     */
    public void fromBluetoothMacAddresses(MessageType messageType, ServiceType serviceType, String content, String... bluetoothMacAddresses) {
        try {
            List<Device> devicesToSendMessage = retrieveDevicesByMacAddresses(serviceType, bluetoothMacAddresses);
            send(messageType, serviceType, content, devicesToSendMessage);
        } catch (ServiceTypeException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Permite transmitir mensagem diretamente para os dispositivos descobertos na varredura do Bluetooth.
     * @param messageType o tipo do template da mensagem.
     * @param serviceType o tipo de serviço Bluetooth utilizado, atualmente suporta apenas o tipo OBEX_OBJECT_PUSH.
     */
    public void fromBluetoothDeviceDiscovery(MessageType messageType, ServiceType serviceType) {
        try {
            List<Device> devicesToSendMessage = discoverDevicesServices(serviceType);
            send(messageType, serviceType, null, devicesToSendMessage);
        } catch (ServiceTypeException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Realizer a montagem da lista de dispositivos a partir de seus endereços bluetooth.
     * @param serviceType o tipo de serviço Bluetooth utilizado, atualmente suporta apenas o tipo OBEX_OBJECT_PUSH.
     * @param bluetoothMacAddresses a lista de endereços bluetooth dos dispositivos, que serão serializados para a classe Device.
     * @return List<Device> a lista de dispositivos com a devida configuração aplicada para o tipo de serviço OBEX_OBJECT_PUSH.
     */
    private List<Device> retrieveDevicesByMacAddresses(ServiceType serviceType, String... bluetoothMacAddresses) {
        List<Device> devicesFilteredByServiceType = Stream.of(bluetoothMacAddresses)
                .map(bluetoothMacAddress -> new Device(
                        serviceType,
                        bluetoothMacAddress,
                        "btgoep://" + bluetoothMacAddress + ":12;authenticate=false;encrypt=false;master=false"
                )).collect(Collectors.toList());

        return devicesFilteredByServiceType;
    }

    /**
     * Realizar a descoberta dos dispositivos e posteriormente a descoberta dos serviços que cada dispositivo oferece.
     * Com isso, serão filtrados apenas os dispositivos que tem o serviço OBEX_OBJECT_PUSH disponível e serão serializados para a classe Device.
     * @param serviceType o tipo de serviço Bluetooth utilizado, atualmente suporta apenas o tipo OBEX_OBJECT_PUSH.
     * @return List<Device> a lista de dispositivos descobertos na varredura do bluetooth.
     */
    private List<Device> discoverDevicesServices(ServiceType serviceType) {
        RemoteServiceDiscovery remoteServicesDiscovery = new RemoteServiceDiscovery();
        Map<String, ServiceRecord> serviceRecords = remoteServicesDiscovery.findServices();
        List<Device> devicesFilteredByServiceType = serviceRecords.entrySet()
                .stream()
                .filter(serviceRecordEntry -> serviceRecordEntry.getKey().equals(serviceType.getName()))
                .map(serviceRecordEntry -> new Device(
                        serviceType,
                        serviceRecordEntry.getValue().getHostDevice().getBluetoothAddress(),
                        serviceRecordEntry.getValue().getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false))
                ).collect(Collectors.toList());

        return devicesFilteredByServiceType;
    }

}
