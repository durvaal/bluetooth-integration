package integration.bluetooth.domain.service;

/**
 * Enum responsável por definir os tipos de serviços para comunicação bluetooth. Atualmente apenas o tipo OBEX_OBJECT_PUSH é suportado.
 * @author Paulo Lima (durvaal - GitHub user)
 */
public enum ServiceType {

    HEADSET_GATEWAY("Headset Gateway"),
    HANDSFREE_GATEWAY("Handsfree Gateway"),
    ANDROID_TV_REMOTE("Android TV Remote"),
    SIM_ACCESS("SIM Access"),
    OBEX_OBJECT_PUSH("OBEX Object Push"),
    SMS_MMS("SMS/MMS"),
    OBEX_PHONEBOOK_ACCESS_SERVER("OBEX Phonebook Access Server");

    public String name;

    ServiceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
