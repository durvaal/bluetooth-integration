# Bluetooth Integration

O intuito do projeto é permitir a comunicação entre dispositvos por meio do protocolo de comunicação Bluetooth.

A comunicação atualmente ocorre de forma unidirecional, sendo do servidor (aplicação java) para o dispositvos (smartphone). Há também uma restrição quanto ao tipo de serviço Bluetooth suportado, a aplicação suporta até então apenas o serviço OBEX Push, que permiti a transmissão de arquivos.

![Diagrama Geral](./assets/readme/diagrama-geral.png)

É possível transmitir dois tipos de arquivos do tipo HTML:

  - GENERIC: a aplicação pode informar uma mensagem qualquer que será incorporada ao conteúdo HTML e entregue ao dispositvo.
  - GEOLOCATION: a aplicação entrega uma página que calcula a distância do servidor e o dispositvo.

A aplicação teve a sua estrutura baseada em [Layered Architecture](https://www.baeldung.com/cs/layered-architecture), segue abaixo uma explicação superficial de cada camada e suas respectivas classes.

![Diagrama das Camadas](./assets/readme/diagrama-camadas.png)

## 🤝 Colaboradores

<table>
  <tr>
    <td align="center">
      <a href="#">
        <img src="assets/readme/me.jpg" width="100px;"/><br>
        <sub>
          <b>Paulo de Lima Xavier </b>
        </sub>
        <br>
        <sub>
          <b>20190104147</b>
        </sub>
      </a>
    </td>
  </tr>
</table>

#### Obrigado por chegar até aqui! ❤️ <br>