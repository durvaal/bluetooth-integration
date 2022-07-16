package integration.bluetooth.exception;

/**
 * Classe responsável por representar a exceção do conteúdo da mensagem não ser nulo para o tipo de mensagem GENERIC.
 * @author Paulo Lima (durvaal - GitHub user)
 */
public class MessageContentException extends RuntimeException {

    public MessageContentException(String message) {
        super(message);
    }

}
