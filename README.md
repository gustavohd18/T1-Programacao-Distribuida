# T1 Programação distribuida

Authors: Gustavo, Marco, Willian

__Atenção:__ Os comandos abaixo deve ser executados com o prefixo `./gradlew` no 
Linux ou `gradlew.bat` no Windows. Por exemplo: `./gradlew run --args="110100100"`. Na mesma  maquina
cada usuario rodando como cliente necessita passar a porta  de acesso caso nao realize
o procedimento somente havera 1 usuario se comunicando com o server  devido ao ip ser o mesmo. para executar
com uma porta utilize o comando `./gradlew runClient --args="<serverIp> <clientIp> <port>"`

Comandos:
* Rodar o programa modo servidor linux: `./gradlew runServer --args="<serverIp> <numberPlayers>"`
* Rodar o programa modo servidor Windows: `gradlew.bat runServer --args="<serverIp> <numberPlayers>"`
* Rodar o programa modo Cliente linux: `./gradlew runClient --args="<serverIp> <clientIp> <port optional>"`
* Rodar o programa modo Cliente Windows: `gradlew.bat runClient --args="<serverIp> <clientIp> <port optional>"`
* Rodar os testes: `./gradlew test` or `gradlew.bat test`

