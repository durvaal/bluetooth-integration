package integration.bluetooth.exception;

/**
 * Classe responsável por representar a exceção cd não existir o tipo de mensagem especificado.
 * @author Paulo Lima (durvaal - GitHub user)
 */
public class MessageTypeException extends RuntimeException {

    public MessageTypeException(String message) {
        super(message);
    }

}
