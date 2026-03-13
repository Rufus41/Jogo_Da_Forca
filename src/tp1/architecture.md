
**architecture.md**
```md
# Arquitetura

## Visão geral

O projeto está organizado à volta de seis classes principais:

- `Palavra`
- `Forca`
- `GestorPalavras`
- `Loja`
- `Jogo`
- `Menu`

Classes auxiliares:

- `HistoricoRonda`
- `ConsolaUtils`
- `ParteD_Jogo_da_Forca`

## Classes principais

### Palavra

Responsabilidade:
- representar a palavra ou expressão da ronda
- controlar o progresso visível ao jogador
- validar tentativas de letra e de palavra completa

Comportamentos principais:
- guarda o texto em maiúsculas
- mostra os espaços desde o início
- conta apenas letras, ignorando espaços
- revela todas as ocorrências de uma letra
- revela todas as vogais
- pode devolver uma letra oculta aleatória para a `Dica`

### Forca

Responsabilidade:
- controlar o número de erros e vidas restantes
- indicar quando o jogador perdeu
- devolver o desenho ASCII correspondente ao estado atual

Comportamentos principais:
- incrementa erros a cada falha
- suporta o efeito `Congelar Vida`
- calcula tentativas restantes

### GestorPalavras

Responsabilidade:
- armazenar as categorias e respetivas palavras
- sortear a palavra da ronda

Comportamentos principais:
- disponibiliza categorias ao `Menu`
- devolve listas de palavras por categoria
- aplica o filtro de `Palavra Fácil`

Categorias atuais:
- `Animais`
- `Paises`
- `Desportos`
- `Tecnologia`
- `Filmes`
- `Comida`

### Loja

Responsabilidade:
- gerir o saldo acumulado entre rondas
- comprar e consumir itens
- manter o inventário da sessão

Itens disponíveis:
- `Dica`
- `Congelar Vida`
- `Revelar Vogais`
- `Palavra Facil`

Comportamentos principais:
- recebe pontos no fim de cada ronda
- desconta saldo ao comprar itens
- decrementa inventário ao usar itens
- mantém `Palavra Facil` ativa para a ronda seguinte

### Jogo

Responsabilidade:
- executar uma ronda completa

Comportamentos principais:
- pede a palavra ao `GestorPalavras`
- cria a `Forca`
- mostra o estado da ronda
- interpreta a ação do jogador
- aplica letras, palavra completa e itens
- calcula a pontuação
- devolve um objeto `HistoricoRonda`

### Menu

Responsabilidade:
- controlar o fluxo principal da aplicação

Comportamentos principais:
- mostra o menu principal
- inicia novas rondas
- abre a loja
- mostra histórico e recorde
- mantém a sessão ativa até o utilizador sair

## Como as classes interagem

Fluxo principal:

1. `ParteD_Jogo_da_Forca` cria um `Menu`.
2. `Menu` cria `GestorPalavras`, `Jogo`, `Loja`, `Scanner` e a lista de histórico.
3. Ao iniciar uma ronda, `Menu` pede categoria e dificuldade.
4. `Jogo` usa `GestorPalavras` para obter uma `Palavra`.
5. `Jogo` cria uma `Forca` com base na dificuldade.
6. Durante a ronda, `Jogo` consulta e atualiza `Palavra`, `Forca` e `Loja`.
7. No fim, `Jogo` devolve `HistoricoRonda`.
8. `Menu` guarda o histórico e adiciona a pontuação à `Loja`.

## Fluxo da loja e do inventário

### Compra

1. O jogador entra na loja pelo menu principal.
2. `Menu` mostra o saldo atual e os itens disponíveis.
3. `Loja.comprarItem(...)` valida o saldo.
4. Se houver pontos suficientes, o saldo desce e o inventário aumenta.

### Uso durante o jogo

Durante cada turno, `Jogo` mostra os atalhos:

- `[1] Dica`
- `[2] Congelar Vida`
- `[3] Revelar Vogais`
- `[4] Palavra Facil`

### Efeito dos itens

`Dica`
- consome uma unidade
- revela uma letra oculta aleatória
- aumenta a pontuação conforme o número de letras reveladas

`Congelar Vida`
- consome uma unidade
- protege do próximo erro
- o erro seguinte não retira vida

`Revelar Vogais`
- consome uma unidade
- revela todas as vogais ainda escondidas
- aumenta a pontuação conforme o número de letras reveladas

`Palavra Facil`
- consome uma unidade
- não altera a ronda atual
- ativa um estado na `Loja`
- na ronda seguinte, `GestorPalavras` tenta sortear uma palavra com 6 letras ou menos

## Pontuação

Pontuação base:

`letras corretas x valor base da dificuldade`

Valores base:
- `Fácil = 10`
- `Médio = 15`
- `Difícil = 20`

Acerto da palavra completa:
- `1.º turno = 3x`
- `2.º turno = 2x`
- `3.º turno = 1.5x`
- `4.º turno em diante = 1x`

Fórmula:

`numero de letras x valor base x multiplicador`
