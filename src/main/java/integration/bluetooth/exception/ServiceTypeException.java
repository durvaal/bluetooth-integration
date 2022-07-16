package integration.bluetooth.exception;

/**
 * Classe responsável por representar a exceção de ter apenas um ServiceType suportado, o OBEX_OBJECT_PUSH.
 * @author Paulo Lima (durvaal - GitHub user)
 */
public class ServiceTypeException extends RuntimeException {

    public ServiceTypeException(String message) {
        super(message);
    }

}
