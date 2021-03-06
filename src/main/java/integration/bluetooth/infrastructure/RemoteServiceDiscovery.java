package integration.bluetooth.infrastructure;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DataElement;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import java.util.*;

/**
 * Classe responsável por realizar a descoberta dos serviços de bluetooth disponíveis dos dispositivos encontrados.
 * @author Paulo Lima (durvaal - GitHub user)
 */
public class RemoteServiceDiscovery {
    private final UUID OBEX_FILE_TRANSFER = new UUID(0x0003);

    private final int maximumNumberOfTryToConnect = 10;
    final Object serviceSearchCompletedEvent = new Object();
    private final Map<String, ServiceRecord> serviceRecords = new HashMap<>();

    /**
     * Realizar a busca dos serviços disponíveis a partir dos dispositivos encontrados na classe RemoteDeviceDiscovery.
     * @return Map<String, ServiceRecord> lista dos serviços tendo como chave o tipo do serviço.
     */
    public Map<String, ServiceRecord> findServices() {
        ServicesDiscoveryListener servicesDiscoveryListener = new ServicesDiscoveryListener();
        RemoteDeviceDiscovery remoteDeviceDiscovery = new RemoteDeviceDiscovery();
        int tryRound = 0;

        while (tryRound < maximumNumberOfTryToConnect && serviceRecords.isEmpty()) {
            for (Enumeration enumeration = remoteDeviceDiscovery.getDevices().elements(); enumeration.hasMoreElements(); ) {
                RemoteDevice remoteDevice = (RemoteDevice) enumeration.nextElement();

                synchronized (serviceSearchCompletedEvent) {
                    int[] attrIDs = new int[]{
                            0x0100 // Service name
                    };

                    System.out.println("Trying to get services (try " + tryRound + ")");

                    try {
                        LocalDevice.getLocalDevice().getDiscoveryAgent().searchServices(attrIDs, new UUID[]{ OBEX_FILE_TRANSFER }, remoteDevice, servicesDiscoveryListener);
                    } catch (BluetoothStateException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        serviceSearchCompletedEvent.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            tryRound++;
        }

        return serviceRecords;
    }

    protected class ServicesDiscoveryListener implements DiscoveryListener {

        @Override
        public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
        }

        @Override
        public void inquiryCompleted(int discType) {
        }

        @Override
        public void serviceSearchCompleted(int transID, int respCode) {
            System.out.println("Service search completed!");

            synchronized (serviceSearchCompletedEvent) {
                serviceSearchCompletedEvent.notifyAll();
            }
        }

        @Override
        public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
            for (ServiceRecord serviceRecord : servRecord) {
                String url = serviceRecord.getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);

                if (url == null) {
                    continue;
                }

                String serviceType = getServiceType(serviceRecord);
                serviceRecords.put(serviceType == null ? java.util.UUID.randomUUID().toString() : serviceType, serviceRecord);

                System.out.println("service found " + url + (serviceType == null ? "" : ", name: " + serviceType));
            }
        }
    }

    protected String getServiceType(ServiceRecord serviceRecord) {
        DataElement serviceType = serviceRecord.getAttributeValue(0x0100);
        String result;

        if (serviceType == null) {
            result = null;
        } else {
            result = String.valueOf(serviceType.getValue());
        }

        return result;
    }
}