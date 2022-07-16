package integration.bluetooth.application;

import integration.bluetooth.infrastructure.ServiceType;
import integration.bluetooth.domain.message.MessageType;
import integration.bluetooth.service.Transmission;

public class Main {

    public static void main(String[] args) {
        // Moto G, OBEX_OBJECT_PUSH connection URL: btgoep://38E39F6E4F37:12;authenticate=false;encrypt=false;master=false
        // Galaxy A10s, OBEX_OBJECT_PUSH connection URL: btgoep://78232762EE21:12;authenticate=false;encrypt=false;master=false

        Transmission transmission = new Transmission();
        transmission.fromBluetoothMacAddresses(MessageType.GEOLOCATION, ServiceType.OBEX_OBJECT_PUSH, "Atenção", "38E39F6E4F37");
        // transmission.fromBluetoothDeviceDiscovery(MessageType.GENERIC, ServiceType.OBEX_OBJECT_PUSH, "Atenção");
    }

}
