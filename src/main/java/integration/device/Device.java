package integration.device;

import integration.bluetooth.ServiceName;

public class Device {

    private ServiceName serviceName;
    private String bluetoothAddress;
    private String connectionURL;

    public Device(ServiceName serviceName, String bluetoothAddress, String connectionURL) {
        this.serviceName = serviceName;
        this.bluetoothAddress = bluetoothAddress;
        this.connectionURL = connectionURL;
    }

    public ServiceName getServiceName() {
        return serviceName;
    }

    public void setServiceName(ServiceName serviceName) {
        this.serviceName = serviceName;
    }

    public String getBluetoothAddress() {
        return bluetoothAddress;
    }

    public void setBluetoothAddress(String bluetoothAddress) {
        this.bluetoothAddress = bluetoothAddress;
    }

    public String getConnectionURL() {
        return connectionURL;
    }

    public void setConnectionURL(String connectionURL) {
        this.connectionURL = connectionURL;
    }

    @Override
    public String toString() {
        return "Device{" +
                "serviceName=" + serviceName +
                ", bluetoothAddress='" + bluetoothAddress + '\'' +
                ", connectionURL='" + connectionURL + '\'' +
                '}';
    }

}
