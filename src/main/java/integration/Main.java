package integration;

import integration.bluetooth.ClientServer;
import integration.bluetooth.ServiceName;
import integration.bluetooth.ServicesSearch;
import integration.device.Device;
import integration.message.Message;
import integration.message.MessageType;

import javax.bluetooth.ServiceRecord;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        // main.sendBulkMessageFromBluetoothSearch(MessageType.GENERIC, ServiceName.OBEX_OBJECT_PUSH, "Oláaaaaaaa");
        main.sendBulkMessageFromBluetoothMacAddresses(MessageType.GEOLOCATION, ServiceName.OBEX_OBJECT_PUSH, "Agora sim");
    }

    public void sendBulkMessageFromBluetoothMacAddresses(MessageType messageType, ServiceName serviceName, String content) {
        ClientServer clientServer = new ClientServer();
        // Moto G, OBEX_OBJECT_PUSH connection URL: btgoep://38E39F6E4F37:12;authenticate=false;encrypt=false;master=false
        // Galaxy A10s, OBEX_OBJECT_PUSH connection URL: btgoep://78232762EE21:12;authenticate=false;encrypt=false;master=false
        List<String> bluetoothMacAddresses = new ArrayList<>() {
            {
                add("38E39F6E4F37");
            }
        };
        List<Device> devicesToSendMessage = bluetoothMacAddresses.stream()
                .map(bluetoothMacAddress -> new Device(
                        serviceName,
                        bluetoothMacAddress,
                        "btgoep://" + bluetoothMacAddress + ":12;authenticate=false;encrypt=false;master=false"
                )).collect(Collectors.toList());

        System.out.println("\nInit send bulk message to " + devicesToSendMessage.size() + " devices.");

        devicesToSendMessage.parallelStream().forEach(device -> {
            System.out.println(device);
            Message message = new Message(device.getConnectionURL(), messageType, content);
            clientServer.sendMessage(message);
        });

        System.out.println("Finish send bulk message to devices.\n");
    }

    public void sendBulkMessageFromBluetoothSearch(MessageType messageType, ServiceName serviceName, String content) {
        List<Device> devicesToSendMessage = searchDevicesServices(serviceName);
        ClientServer clientServer = new ClientServer();

        System.out.println("\nInit send bulk message to " + devicesToSendMessage.size() + " devices.");

        devicesToSendMessage.forEach(device -> {
            System.out.println(device);
            Message message = new Message(device.getConnectionURL(), messageType, content);
            clientServer.sendMessage(message);
        });

        System.out.println("Finish send bulk message to devices.\n");
    }

    public List<Device> searchDevicesServices(ServiceName serviceName) {
        ServicesSearch servicesSearch = new ServicesSearch();
        Map<String, ServiceRecord> serviceRecords = servicesSearch.findServices();
        List<Device> devicesFilteredByServiceName = serviceRecords.entrySet()
                .stream()
                .filter(serviceRecordEntry -> serviceRecordEntry.getKey().equals(serviceName.getName()))
                .map(serviceRecordEntry -> new Device(
                        serviceName,
                        serviceRecordEntry.getValue().getHostDevice().getBluetoothAddress(),
                        serviceRecordEntry.getValue().getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false))
                ).collect(Collectors.toList());

        return devicesFilteredByServiceName;
    }

}
