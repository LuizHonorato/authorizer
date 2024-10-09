Sumário
=======

<!--ts-->
   * [Introdução](#Introdução)
   * [Pré-requisitos](#Pré-requisitos)
   * [Build](#Build)
   * [Instalação](#Instalação)
   * [Database](#Database)
   * [Execução](#Execução)
   * [Requisição](#Requisição)
   * [Testes](#Testes)
   * [L4](#L4)
<!--te-->

Introdução
==========

Authorizer - Aplicação responsável por processar transações de cartões de crédito.

Pré-requisitos
==============

- Java 21+
- Docker

Build
=====

Este serviço foi criado com a utilização das seguintes ferramentas:

- SpringBoot Framework 3.3.4
- Kotlin
- JUnit
- Mockito
- PostgreSQL 14
- Flyway
- Arquitetura Hexagonal

Instalação
==========

Para instalar as dependências e buildar o projeto, clone este repositório e abra ele na IDE IntelliJ.


Database
========

 Antes de executar a aplicação, precisamos subir o banco de dados. Execute os comandos abaixo para essa finalidade:

 * `docker build -t postgres-authorizer .`

 * `docker run --name authorizer-db -p 5432:5432 -d postgres-authorizer`

 Este comando irá criar uma imagem e subir um container do PostgreSQL 14 já com a base de dados `authorizer` criada e a extensão `uuid-ossp` habilitada para suporta a colunas do tipo UUIDv4.

 Execução
========

 Para rodar a aplicação, execute a mesma através do IntelliJ. 
 Ao rodar a aplicação o Flyway irá executar as migrations para criar as tabelas no banco de dados e cadastrar alguns dados nelas para podermos testar a aplicação. 
 Não se preocupe em cadastrar nenhum dado manualmente.

 As migrations se encontram no caminho `src/resources/db/migration`.
 
Requisição
========

Segue abaixo payload para teste via Postman ou Insomnia por exemplo:

`POST http://localhost:8080/api/v1/transactions`

```
{
	"accountId": "0b38b235-ec9c-4828-a25f-74a228d80845",
	"totalAmount": 100.00,
	"mcc": "5811",
	"merchant": "Mercado do Zé"
}

```

Testes
======

Devido o prazo não foi possível criar testes unitários, de integração e e2e para todas as classes da aplicação. Porém eu criei um teste para o coração da aplicação que é o usecase `ProcessCreditCardTransactionUseCase`.
O teste se encontra no caminho `src/test/kotlin/com/authorizer/application/core/usecases/ProcessCreditCardTransactionUseCaseTest`. Para testes, eu gosto de seguir basicamente a mesma estrutura utilizada na aplicação
separando também as camadas para fins de organização e isolamento do que está sendo testado.

L4
======

Sobre o desafio L4, a respeito da questão de impedir que mais de uma transação ocorra ao mesmo tempo para o mesmo cartão de forma síncrona, a alternativa que eu testaria é fazer um lock a nível de banco de dados enquanto a tabela `accounts`
estiver sendo utilizada. Quando buscamos essa tabela através do `accountId` vindo da requisição, podemos fazer um `SELECT FOR UPDATE` dentro de uma transaction e bloquear essa linha da tabela da onde o saldo será debitado, assim se outra requisição entrar enquanto uma
ainda não tiver terminando, essa nova requisição receberá um erro. Segue um exemplo:

```
BEGIN;

SELECT * FROM accounts WHERE uuid = '0b38b235-ec9c-4828-a25f-74a228d80845' FOR UPDATE;

UPDATE accounts SET food_balance = food_balance - 100 WHERE id = 1;

COMMIT;
```

