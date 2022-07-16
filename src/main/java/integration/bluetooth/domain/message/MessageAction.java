package integration.bluetooth.domain.message;

import integration.bluetooth.exception.MessageContentException;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Interface responsável por definir as ações padrões de uma mensagem.
 * @author Paulo Lima (durvaal - GitHub user)
 */
public interface MessageAction {

    /**
     * Busca os arquivos de template utilizados na transmissão das mensagens.
     * @param path caminha do arquivo de template.
     * @param content o tipo do template da mensagem.
     * @return byte[] o arquivo de template.
     */
    byte[] getBytesFiles(Path path, String content) throws MessageContentException, IOException;

    /**
     * As classes de mensagem que implementam definem o nome do arquivo de template.
     * Isso será usado para buscar o arquivo e enviá-lo via bluetooth.
     * @return o nome do arquivo de template.
     */
    String getTemplateFile();
}
