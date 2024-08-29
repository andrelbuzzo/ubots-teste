# Central de relacionamento

## Introdução
A Invext é uma fintech que está estruturando sua central de relacionamento. Essa central de
relacionamento irá atender diversos tipos de solicitações dos clientes. Os principais tipos de
solicitações são: Problemas com cartão e contratação de empréstimo.

A fim de entregar a melhor experiência para seus clientes, a Invext organizou seus atendentes
em 3 times de atendimento: Cartões, Empréstimos e Outros Assuntos. Agora, a Invext irá
desenvolver um software que distribua as solicitações para o time correto de acordo com o seu
tipo. Solicitações com assunto "Problemas com cartão", devem ser enviados para atendentes do
time "Cartões". Solicitações de "contratação de empréstimo" devem ser encaminhadas para
atendentes do time "Empréstimos", enquanto os demais assuntos, devem ir para atendentes do
time "Outros Assuntos".

É uma política da central de relacionamento que cada atendente atenda no máximo 3 pessoas
de forma simultânea, e caso todos os atendentes de um time estejam ocupados, os
atendimentos devem ser enfileirados e distribuídos assim que possível.

# O que eu fiz
> André Buzzo - 29/08/2024

- Banco de dados usado: H2 com persistência em arquivo
- Spring Security com JWT
- Não implementei o endpoint `/logout`, porque o JWT é stateless e o token não é armazenado
- A cada minuto é executado um job do Quartz para atribuir um usuário aos tickets sem atendente, caso o mesmo possua disponibilidade

## Como rodar

### Requisitos
- Java 21 (ou acima)
- Gradle

Então, você pode acessar a API pelo navegador e enviar as requisições via Insomnia/Postman:

- Vá para [localhost:8080/h2-ui](http://localhost:8080/h2-ui) para ver o banco de dados
- É preciso fazer login para acessar os endpoints

## Mais informações
- O banco de dados está persistindo no arquivo dentro da pasta `resources`
- Foi disponibilizado o JSON com as requisições dentro da pasta `resources`

## O que pode melhorar
- Adicionar documentação via Swagger
- Adicionar testes