package integration.bluetooth.infrastructure;

import java.io.IOException;
import java.util.Vector;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;

/**
 * Classe responsável por realizar a descoberta dos dispositivos.
 * @author Paulo Lima (durvaal - GitHub user)
 */
public class RemoteDeviceDiscovery {

    /**
     * Realiza a busca dos dispositivos a partir do protocolo de comunicação bluetooth.
     * @return Vector lista de dispositivos descobertos.
     */
    public Vector getDevices() {
        /* Create Vector variable */
        final Vector devicesDiscovered = new Vector();

        try {
            final Object inquiryCompletedEvent = new Object();

            /* Clear Vector variable */
            devicesDiscovered.clear();

            /* Create an object of DiscoveryListener */
            DiscoveryListener listener = new DiscoveryListener() {
                public void deviceDiscovered(RemoteDevice remoteDevice, DeviceClass deviceClass) {
                    System.out.println("Device " + remoteDevice.getBluetoothAddress() + " found");

                    try {
                        System.out.println("Device " + remoteDevice.getBluetoothAddress() + " name " + remoteDevice.getFriendlyName(false));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    /* Get devices paired with system or in range(Without Pair) */
                    devicesDiscovered.addElement(remoteDevice);
                }

                public void inquiryCompleted(int discType) {
                    System.out.println("Device Inquiry completed!");

                    /* Notify thread when inquiry completed */
                    synchronized (inquiryCompletedEvent) {
                        inquiryCompletedEvent.notifyAll();
                    }
                }

                /* To find service on bluetooth */
                public void serviceSearchCompleted(int transID, int respCode) {
                }

                /* To find service on bluetooth */
                public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
                }
            };

            synchronized (inquiryCompletedEvent) {
                /* Start device discovery */
                boolean started = LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC, listener);

                if (started) {
                    System.out.println("wait for device inquiry to complete...");
                    inquiryCompletedEvent.wait();
                    System.out.println(devicesDiscovered.size() +  " device(s) found");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        /* Return list of devices */
        return devicesDiscovered;
    }

}