package integration.bluetooth.domain.device;

import integration.bluetooth.infrastructure.ServiceType;

public class Device {

    private ServiceType serviceName;
    private String bluetoothAddress;
    private String serviceConnectionURL;

    public Device(ServiceType serviceName, String bluetoothAddress, String serviceConnectionURL) {
        this.serviceName = serviceName;
        this.bluetoothAddress = bluetoothAddress;
        this.serviceConnectionURL = serviceConnectionURL;
    }

    public ServiceType getServiceName() {
        return serviceName;
    }

    public void setServiceName(ServiceType serviceName) {
        this.serviceName = serviceName;
    }

    public String getBluetoothAddress() {
        return bluetoothAddress;
    }

    public void setBluetoothAddress(String bluetoothAddress) {
        this.bluetoothAddress = bluetoothAddress;
    }

    public String getServiceConnectionURL() {
        return serviceConnectionURL;
    }

    public void setServiceConnectionURL(String serviceConnectionURL) {
        this.serviceConnectionURL = serviceConnectionURL;
    }

    @Override
    public String toString() {
        return "Device{" +
                "serviceName=" + serviceName +
                ", bluetoothAddress='" + bluetoothAddress + '\'' +
                ", serviceConnectionURL='" + serviceConnectionURL + '\'' +
                '}';
    }

}
