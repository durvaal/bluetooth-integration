package integration;

import integration.bluetooth.ClientServer;
import integration.bluetooth.ServicesSearch;
import integration.message.Message;
import integration.message.MessageType;

import javax.bluetooth.ServiceRecord;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.sendGeolocationMessage();
    }

    public void sendGenericMessage() {
        Message message = new Message("btgoep://38E39F6E4F37:12;authenticate=false;encrypt=false;master=false", MessageType.GENERIC, "Aviso do IMD");
        ClientServer clientServer = new ClientServer();

        clientServer.sendMessage(message);
    }

    public void sendGeolocationMessage() {
        Message message = new Message("btgoep://38E39F6E4F37:12;authenticate=false;encrypt=false;master=false", MessageType.GEOLOCATION, "");
        ClientServer clientServer = new ClientServer();

        clientServer.sendMessage(message);
    }

    public void searchDevices() {
        ServicesSearch servicesSearch = new ServicesSearch();
        Map<String, ServiceRecord> serviceRecords = servicesSearch.findServices();

        for (Map.Entry<String, ServiceRecord> entry : serviceRecords.entrySet()) {
            String serviceName = entry.getKey();
            ServiceRecord serviceRecord = entry.getValue();
            System.out.println("Device bluetooth address '" + serviceRecord.getHostDevice().getBluetoothAddress() + "', available service name '" + serviceName + "'");
        }
    }
}
