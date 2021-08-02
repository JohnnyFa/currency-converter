# Desafio DQR Tech

Seja bem-vindo! Obrigado por participar do nosso processo seletivo. Este é o primeiro passo do nosso processo de seleção.

#### LEIA TODAS AS INSTRUÇÕES ANTES DE COMEÇAR

Este desafio consiste no desenvolvimento de um app de conversão de moedas. O app deve permitir que o usuário selecione a moeda de origem e a moeda a ser convertida, para então inserir o valor e visualizar o resultado da conversão.

## Requisitos

O app deve counsumir a [API CurrencyLayer](https://currencylayer.com/documentation). Para utilizar a API será necessário fazer um cadastro no plano gratuito para obter uma chave de acesso. Como o plano gratuito da API apresenta apenas as taxas de câmbio em relação ao dólar americano (USD).
Para fazer uma conversão entre quaisquer outras duas moedas, será necessário primeiro converter a moeda de origem para dólar e então de dólar para a moeda a ser convertida.

-   Android: _Kotlin_ | iOS: _Swift_
-   O aplicativo deve ter duas telas principais:
    -   A tela de conversão deve conter:
        -   Dois botões que permitam o usuário a escolher as moedas de origem e de destino.
        -   Um campo de entrada de texto onde o usuário possa inserir o valor a ser convertido.
        -   Um campo de texto para apresentar o valor convertido.
        -   Um botão para converter os valores inseridos nos campos anteriores.
    -   A tela de listagem de moedas deve conter:
        -   Uma lista das moedas disponíves para conversão, mostrando código e nome da moeda.
#### IMPORTANTE: A listagem de moedas deve ser mostrada obrigatoriamente em uma tela diferente da tela de conversão.

## Observações

-   Dê preferência para a não utilização de bibliotecas externas;
-   Caso opte por usar bibliotecas externas, prefira Gradle (Android) ou CocoaPods (iOS) como gerenciadores de dependência;
-   O objetivo deste desafio é avaliar o seu conhecimento técnico, estilo de código, conhecimento de arquiteturas, padrões de programação e boas práticas. Faça disso uma oportunidade pra mostrar todo o seu conhecimento.

## Features

### Obrigatórias:

-   [ ] As taxas de câmbio disponíveis devem ser obtidas da chamada de [API Supported Currencies (/list)](https://currencylayer.com/documentation)
-   [ ] A cotação atual deve ser obtida da chamada de [API Real-time Rates (/live)](https://currencylayer.com/documentation)
-   [ ] É necessário fazer tratamento de erros e dos fluxos de exceção, como busca vazia, carregamento e outros erros que possam ocorrer.
-   [ ] Funcionalidade de busca na lista de moedas por nome ou sigla da moeda ("dólar" ou "USD").
-   [ ] Ordenação da lista de moedas por nome ou código.
#### Estes dois últimos itens são importantíssimos. Precisam estar presentes.

### Opcionais (não necessário, porém contam pontos):

-   [ ] Realizar a persistência local da lista de moedas e taxas para permitir o uso do app no caso de falta de internet.
-   [ ] Desenvolver testes unitários e/ou funcionais.
-   [ ] Desenvolver o app seguindo a arquitetura MVVM.
-   [ ] Pipeline automatizado.

## Processo de submissão

Para submeter o seu desafio, faça um clone deste projeto, desenvolva localmente e, no final, abra um pull request com o formato "[Plataforma] - Nome" para a master até a data limite estabelecida. Um exemplo seria "[iOS] - João da Silva".

Se tiver alguma dificuldade em submeter desta forma, sem problemas. Crie um repositório público e nos envie o link. Baixaremos um zip do repositório e executaremos no Xcode ou Android Studio. É importante que seja possível executar sem dificuldades (se o teste não rodar não será possível avançar para a próxima fase).

### Boa sorte.
