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

### Ligação ao cienciavitae

Para a aplicação se ligar à réplica do cienciavitae que existe na Lusófona, tem se instalar o certificado ssl respetivo pois é emitido
por uma entidade que não consta da lista de certificados reconhecidos.

    (sudo) keytool -importcert -keystore /Library/Java/JavaVirtualMachines/jdk-17.0.1.jdk/Contents/Home/lib/security/cacerts -storepass changeit -file playdev.ulusofona.pt.cer -alias "playdev-ulusofona-root"

### Ambiente de testes

* Os utilizadores de teste devem ser criados na sandbox do orcid (ver ()[https://orcid.github.io/orcid-api-tutorial/sandbox/]). 
Importante: o email com que se registam deve ser @mailinator.com 
* Caso não consigam criar o utilizador, podem usar este:
  * user: pedroalves@mailinator.com
  * pass: zX9KnsgL
  * orcid: 0000-0003-2187-5116
  * link orcid: https://sandbox.orcid.org/0000-0003-2187-5116
* Para indicar os utilizadores que são admin, editar o ficheiro admin_list_test.txt

### Criar o primeiro utilizador (admin) do site

A partir do momento em que se cria o admin do site, o processo de criação de novos utilizadores fica facilitado.

Mas para criar o primeiro, tem que se fazer o seguinte:
* Adicionar aos ficheiros first_time_user_list_test.txt e admin_list_test.txt, o orcid do primeiro utilizador
* Aceder a http://localhost:8080
* Clicar em login. Irá redireccionar para o orcid e retornar.
* No retorno, deverá aparecer o formulário para preencher os dados pessoais e a hipótese de sincronizar com o ciencia vitae

Uma vez criado o admin, este deverá usar a funcionalidade de gerir investigadores para criar os restantes utilizadores.

### Utilizadores de teste

* user: investigador.ulht@mailinator.com | pass: password123 | orcid: 0000-0001-7290-8155
* user: investigador2.ulht@mailinator.com | pass: password123 | orcid: 0000-0002-5516-1747

### Alguns ciencia vitae

* Pedro Alves - 7B17-D35E-668E
* Elsa Estrela - E919-3A7A-E42C
