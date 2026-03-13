# Testes

## Objetivo

Os testes unitários verificam pequenas partes do programa de forma isolada. Neste projeto, ajudam a confirmar que a lógica do jogo continua correta quando o código muda.

## Localização

Os testes estão em:

- `src/tp1/tests_ParteD`

Classes de teste:

- `PalavraTest`
- `ForcaTest`
- `GestorPalavrasTest`
- `LojaTest`
- `JogoPontuacaoTest`

## O que cada teste verifica

### PalavraTest

- letras corretas revelam todas as ocorrências
- letras erradas não alteram o progresso
- expressões com espaços mostram os espaços desde o início
- adivinhar a expressão completa exige o texto inteiro
- `revelarVogais()` revela apenas as vogais pendentes

### ForcaTest

- os erros acumulam até à derrota
- `Congelar Vida` protege apenas o próximo erro

### GestorPalavrasTest

- as categorias principais existem
- há expressões com múltiplas palavras
- o filtro de `Palavra Fácil` só devolve palavras com 6 letras ou menos
- `getPalavrasDaCategoria()` devolve uma cópia segura do array

### LojaTest

- comprar um item desconta saldo e aumenta inventário
- comprar sem saldo suficiente falha
- `Palavra Fácil` é ativada e consumida corretamente
- consumir um item inexistente falha

### JogoPontuacaoTest

- a pontuação base depende da dificuldade
- os multiplicadores mudam consoante o turno
- a pontuação de palavra completa aplica o multiplicador correto

## Compilar os testes

```powershell
$mainFiles = Get-ChildItem .\src\tp1\ParteD_library -Filter *.java | ForEach-Object { $_.FullName }
$testFiles = Get-ChildItem .\src\tp1\tests_ParteD -Filter *.java | ForEach-Object { $_.FullName }

javac -encoding UTF-8 -cp .\lib\junit-platform-console-standalone-1.10.0.jar `
  -d .\out\junit-tests `
  .\src\tp1\ParteD_Jogo_da_Forca.java $mainFiles $testFiles
