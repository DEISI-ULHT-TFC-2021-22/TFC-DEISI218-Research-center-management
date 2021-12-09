# Gestor de Centros de Investigação
---
### Setup do projeto

#### bulma-calendar
```
> npm i bulma-calendar
```
#### bulma-switch
```
> npm i bulma-switch
```
#### Gerar o ficheiro CSS
Para compilar o css através do ficheiro sass(é necessário fazer uma alteração no ficheiro sass para ser gerado o css)
```
> npm start
```
---
### Setup da BD

* Instalar MySQL e pôr a correr
* Ligar o Intellij ao MySQL através do separador Database > New Data Source (MySQL)
* Criar um schema XXX com o nome da aplicação (ex: "gestao-tfc") e criar um utilizador XXX com o mesmo nome da aplicação e 
dar-lhe permissões sobre o schema criado no passo anterior

```
create database trabalho;

create user 'trabalho'@'localhost' identified by 'passwordtrabalho';

grant all privileges on trabalho.* to 'trabalho'@'localhost';
```

* Alterar o src/resources/application.properties com o nome da BD, nome e pass do utilizador

### Ambiente de testes

* Os utilizadores de teste devem ser criados na sandbox do orcid (ver ()[https://orcid.github.io/orcid-api-tutorial/sandbox/]). 
Importante: o email com que se registam deve ser @mailinator.com 
* Caso não consigam criar o utilizador, podem usar este:
  * user: pedroalves@mailinator.com
  * pass: zX9KnsgL
  * orcid: 0000-0003-2187-5116
  * link orcid: https://sandbox.orcid.org/0000-0003-2187-5116
* Para indicar os utilizadores que são admin, editar o ficheiro admin_list_test.txt
