package integration.bluetooth.application;

import integration.bluetooth.domain.message.MessageGeneric;
import integration.bluetooth.domain.message.MessageGeolocation;
import integration.bluetooth.domain.service.ServiceType;
import integration.bluetooth.service.Transmission;

/**
 * Classe responsável por dar inicio a aplicação instanciando a classe Transmission.
 * @author Paulo Lima (durvaal - GitHub user)
 */
public class Main {

    public static void main(String[] args) {
        // Moto G, OBEX_OBJECT_PUSH connection URL: btgoep://38E39F6E4F37:12;authenticate=false;encrypt=false;master=false
        // Galaxy A10s, OBEX_OBJECT_PUSH connection URL: btgoep://78232762EE21:12;authenticate=false;encrypt=false;master=false

        Transmission transmission = new Transmission();
        transmission.fromBluetoothMacAddresses(new MessageGeneric(), ServiceType.OBEX_OBJECT_PUSH, "Atenção", "38E39F6E4F37");
        transmission.fromBluetoothDeviceDiscovery(new MessageGeolocation(), ServiceType.OBEX_OBJECT_PUSH, "Atenção");
    }

}
