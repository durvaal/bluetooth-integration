package integration.bluetooth;

public enum ServiceName {

    HEADSET_GATEWAY("Headset Gateway"),
    HANDSFREE_GATEWAY("Handsfree Gateway"),
    ANDROID_TV_REMOTE("Android TV Remote"),
    SIM_ACCESS("SIM Access"),
    OBEX_OBJECT_PUSH("OBEX Object Push"),
    SMS_MMS("SMS/MMS"),
    OBEX_PHONEBOOK_ACCESS_SERVER("OBEX Phonebook Access Server");

    public String name;

    ServiceName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
