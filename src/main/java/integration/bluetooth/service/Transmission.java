package integration.bluetooth.service;

import integration.bluetooth.domain.device.Device;
import integration.bluetooth.domain.message.MessageType;
import integration.bluetooth.exception.ServiceTypeException;
import integration.bluetooth.infrastructure.RemoteServiceDiscovery;
import integration.bluetooth.infrastructure.ServiceType;

import javax.bluetooth.ServiceRecord;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Transmission implements BulkMessage {

    public void fromBluetoothMacAddresses(MessageType messageType, ServiceType serviceType, String content, String... bluetoothMacAddresses) {
        try {
            List<Device> devicesToSendMessage = retrieveDevicesByMacAddresses(serviceType, bluetoothMacAddresses);
            send(messageType, serviceType, content, devicesToSendMessage);
        } catch (ServiceTypeException e) {
            System.err.println(e.getMessage());
        }
    }

    public void fromBluetoothDeviceDiscovery(MessageType messageType, ServiceType serviceType) {
        try {
            List<Device> devicesToSendMessage = discoverDevicesServices(serviceType);
            send(messageType, serviceType, null, devicesToSendMessage);
        } catch (ServiceTypeException e) {
            System.err.println(e.getMessage());
        }
    }

    private List<Device> retrieveDevicesByMacAddresses(ServiceType serviceType, String... bluetoothMacAddresses) {
        List<Device> devicesFilteredByServiceType = Stream.of(bluetoothMacAddresses)
                .map(bluetoothMacAddress -> new Device(
                        serviceType,
                        bluetoothMacAddress,
                        "btgoep://" + bluetoothMacAddress + ":12;authenticate=false;encrypt=false;master=false"
                )).collect(Collectors.toList());

        return devicesFilteredByServiceType;
    }

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
