# philosophers

Implementation of dining philosophers problem.

I wanted to use Golang but my Parallel Programming professor likes Java more.

### Abstração do algoritmo

O package `philosophers.logic` contém a implementação da solução para o trabalho proposto, sendo o package `philosophers.ui` apenas uma forma simplória de visualizar o que está acontecendo durante o fluxo de dados do algoritmo.

Analisando o problema, observei que o garfo não precisava ser modelado em uma classe separada, ele poderia ser simplesmente uma propriedade da classe `Philosopher`. Isso acontece porque, dentro do meu raciocínio, a única propriedade da classe Garfo seria uma flag indicando se ele estava ou não sendo usado, e a única classe que iria interagir com ele seria a classe do filósofo, então foi natural transformá-lo em uma propriedade.
pela maneira que enxergo o problema, cada filósofo possui um garfo e deverá pedir ao seu vizinho da para que empresta o garfo. Dessa forma, cada garfo fica intrisecamente relacionado a um filósofo, facilitando a modelagem.

A classe que representa o filósofo age de uma maneira bem simples: A cada passo dado, ela libera um evento e aguarda um período de tempo para iniciar o próximo passo. A sequência de passos é resumida no método `tryToEat`:

1. Tenta pegar o seu próprio garfo.
   * Se o garfo estiver em uso pelo seu vizinho, encerra a rotina.
2. Tenta pegar o garfo do seu vizinho
   * Se o garfo estiver em uso, solta o seu garfo e encerra a rotina.
3. Segura os garfos por alguns instantes para simbolizar o ato de jantar.
4. Libera os garfos.

O ato de pegar um garfo emprestado (que ocorre no método `borrowFork`) é simplesmente alterar o status do garfo do seu vizinho. É importante notar que em nenhum momento é armazenada a informação sobre __quem está segurando o garfo__, a única coisa que se sabe é __se o garfo está ou não em uso__. Isto é possível pois apenas o próprio filósofo e o seu vizinho podem usar o garfo.

A parte mais importante do código encontra-se no método síncrono `setForkStatus`, que altera o status do garfo de acordo com a flag enviada por parâmetro.
É __importante__ notar que a verificação do status ocorre dentro deste método, ou seja, dentro dele é verificado se o status do garfo pode ou não ser alterado. Esta verificação deve acontecer dentro de um método síncrono para evitar que duas threads passem pela flag e acabem alterando o status do garfo para "em uso" (tal acontecimento quebraria completamente a lógica do programa).
Perceba que não é feita verificação nenhuma para quando tenta-se marcar o garfo como "não está em uso". Eu pude implementar desse jeito porque o fluxo do método `tryToEat` me garante que __somente o filósofo que possui o garfo__ passará a flag de "não está em uso".

A classe `DinnerRoom` instancia os filósofos, inicia cada um eles em uma thread separada e acopla essas threads à thread atual, para evitar que o programa termine abruptamente antes dos filósofos terminarem de jantar. A classe `Philosopger` implementa a interface Runnable do Java para facilitar a criação da thread.

As demais contruções dentro do package `philosophers.logic` são para a implementação de dois [observer pattern](https://en.wikipedia.org/wiki/Observer_pattern), os quais se fizeram necessários uma vez que utilizei uma abordagem orientada a eventos.
