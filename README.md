# desafio-votacao
### Dependencias para rodar o projeto localmente
- Java 17
- Docker
- Docker compose


### Sobre o desenvolvimento

O desafio foi desenvolvido com Spring boot, Gradle e MongoDB como banco de dados. A escolha de tais
ferramentas se deu pela familiaridade com as tecnologias, visando assim um desenvolvimento mais rápido.

#### Modelagem de dados

![image](modelagem.png)

#### Instruções de uso

A aplicação desenvolvida possui duas dependencias para rodar. Banco de dados MongoDB e o validador de cpf
fake (https://github.com/cassiorp/fake-cpf-validator), serviço que valida de forma randomica se um cpf é valido ou não.
É possivel rodar toda infraestrutura para via docker-compose. Para isso é necessário ter instalado  ````Docker ````
e  ````docker-compose ```` em seu computador.

Clone o repositório e navegue até a pasta docker/app:

`````
cd desafio-votacao/docker/app
````` 

Depois rode o comando:

````
sudo docker-compose up
````

Três containers (desafio-votacao, mongo_db e cpfvalidator) irão ser instanciados, o banco de dados irá criar um volume
dentro do diretório ````desafio-votacao/docker/mongo-data```` assim os dados não irão se perder ao reiniciar a aplicação. E você
pode acessar a documentação da aplicação pela url: http://localhost:5000/swagger-ui/index.html

Também é possivel rodar apenas as dependencias do projeto e rodar a aplicação em sua IDE de preferência, para isso rode
o comando ````sudo docker-compose up```` dentro do diretório ````desafio-votacao/docker/dependencies````

Collection postman: https://drive.google.com/file/d/1EEcqzN9X0l0DXuLMzuWPrSYPUsfok8Tf/view?usp=sharing
## API

| Rota                           | Metodo | Descricao                                                                                                                                                                                                                                                                                                                                       |
|--------------------------------|--------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `/api/v1/pauta`                | POST   | Criar uma pauta com titulo e descrição                                                                                                                                                                                                                                                                                                          |
| `/api/v1/pauta`                | GET    | Busca todas as pautas cadastradas                                                                                                                                                                                                                                                                                                               |
| `/api/v1/pauta/{id}/resultado` | GET    | Busca a situação da votação de um pauta por id. O retorno vem com as datas de abertura de fim da votação, total de votos, votos contra, votos a favor e campo `status` que traz a situação da votação que pode ser `ABERTA, APROVADA, REPROVADA, EMPATADA`.                                                                                     |
| `/api/v1/sessao`               | POST   | Cria uma sessão de votação. Existem dois campos na criação da sessão: ```idPauta: String``` que é obrigatório e precisa ser de uma pauta existente. E ```duracao: Long``` tempo de duração da votação em ***millisegundos***. Não é obrigatório e por default é 60000(1 minuto)                                                                 |
| `/api/v1/voto`                 | POST   | Método para votação. Possui os campos ```voto: Boolean```, ```cpf: String```, ```idPauta: String``` (todos obrigatórios). A validação do cpf é feita de forma aleatória por um serviço externo. Caso o formato do cpf seja invalido, sempre ira retornar UNABLE_TO_VOTE. Caso seja valido será feito random entre ABLE_TO_VOTE e UNBALE_TO_VOTE |
