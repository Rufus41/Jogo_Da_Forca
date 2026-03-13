# Jogo da Forca

Projeto de consola em Java com categorias, dificuldades, pontuação, loja, inventário, histórico de sessão e testes JUnit.

## Estrutura

- `src/tp1/ParteD_Jogo_da_Forca.java`: ponto de entrada
- `src/tp1/ParteD_library/`: classes principais do jogo
- `src/tp1/tests_ParteD/`: testes unitários JUnit

## Funcionalidades

- Escolha de categoria no início de cada ronda
- Escolha de dificuldade: `Fácil`, `Médio`, `Difícil`
- Palavras simples e expressões com espaços
- Tentativa por letra ou por palavra completa
- Pontuação por letras corretas e por acerto da palavra completa
- Loja entre rondas com itens de inventário
- Histórico da sessão e recorde
- Interface de consola com forca ASCII e cores ANSI

## Requisitos

- Java JDK 17 ou superior
- Terminal com `javac` e `java`
- JUnit 5 standalone em `lib/junit-platform-console-standalone-1.10.0.jar`

## Compilar o jogo

```powershell
$files = Get-ChildItem .\src\tp1\ParteD_library -Filter *.java | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d .\out\app .\src\tp1\ParteD_Jogo_da_Forca.java $files
