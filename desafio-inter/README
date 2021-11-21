################ COMPILAR O PROJETO ################

O projeto foi desenvolvido em java 11.

Para compilar o projeto, basta executar o comando mvn clean install na raiz do projeto. O comando ja executará todos
os testes unitários e integrados. Para pular os testes integrados basta adicionar a propriedade
-Dskip.integration.tests=true no mvn clean install.

- mvn clean install -Dskip.integration.tests=true

Link para o download do maven:
- https://maven.apache.org/download.cgi

################ SUBINDO A APLICAÇÃO ################

É possível subir a aplicação em um container Docker. Para isso é necessário:
- possuir o docker na máquina.
- executar o comando "docker image build -t broker ." para gerar a imagem da aplicação.
- executar o comando "docker container run -p 8080:8080 broker".

OBS: A porta 8080 do container está sendo configurada para ser exposta no arquivo Dockerfile na raiz do projeto.

Link para o download do docker:
- https://www.docker.com/products/docker-desktop

Caso não possua o docker instalado, a aplicação pode ser executada via IDE (executando a classe
DesafioInterApplication.java) ou via CMD executando o comando "java -jar target/desafio-inter.jar".

################ ACESSANDO O CONSOLE H2 (BANCO DE DADOS) ################

Para visualizar o console h2 caso esteja executando a aplicação localmente (não em um container docker) basta acessar
a URL:
- http://localhost:8080/broker/h2-console

Dados para acessar o console:
- Driver Class: org.h2.Driver
- JDBC URL: jdbc:h2:mem:brokerdb
- User Name: sa
- Password: password

################ DOCUMENTAÇÃO SWAGGER ################

Para visualizar o swagger e ter acesso aos endpoints da aplicação, basta acessar a URL:
- http://localhost:8080/broker/swagger-ui.html

################ REGRAS DE NEGÓCIO ################

####### EMPRESAS #######

Ao subir a aplicação as empresas serão criadas automaticamente na base de dados.

A partir dos endpoits de empresas é possível:
- Buscar todas as empresas cadastradas ou filtrar empresas por ativas ou inativas.
http://localhost:8080/broker/swagger-ui.html#/Company%20Service/findUsingGET
GET http://localhost:8080/broker/api/v1/companies
GET http://localhost:8080/broker/api/v1/companies?status=ACTIVE
GET http://localhost:8080/broker/api/v1/companies?status=INACTIVE

- Criar novas empresas.
http://localhost:8080/broker/swagger-ui.html#/Company%20Service/createUsingPOST
POST http://localhost:8080/broker/api/v1/companies

- Buscar empresas pelo código
http://localhost:8080/broker/swagger-ui.html#/Company%20Service/findByCodeUsingGET
GET http://localhost:8080/broker/api/v1/companies/{CODIGO_DA_EMPRESA}

- Editar empresas cadastradas
http://localhost:8080/broker/swagger-ui.html#/Company%20Service/updateUsingPUT
PUT http://localhost:8080/broker/api/v1/companies/{ID_DA_EMPRESA}

- Remover empresas cadastradas
http://localhost:8080/broker/swagger-ui.html#/Company%20Service/deleteUsingDELETE
DELETE http://localhost:8080/broker/api/v1/companies/{ID_DA_EMPRESA}

- Editar o status, o preço ou ambos de empresas cadastradas
http://localhost:8080/broker/swagger-ui.html#/Company%20Service/patchUsingPATCH
PATCH http://localhost:8080/broker/api/v1/companies/{ID_DA_EMPRESA}

####### USUÁRIOS #######

O primeiro passo que deve ser feito é a criação de um usuário:
- Criar usuário
http://localhost:8080/broker/swagger-ui.html#/User%20Service/createUsingPOST_3
POST http://localhost:8080/broker/api/v1/users

Ao criar o usuário, o sistema irá criar uma carteira para o usuário automaticamente. A carteira mantera o vinculo do
usuários com suas ações. O CPF do usuário será necessário para a execução de orders de compra e venda de
ações além de ser necessária para a compra de ações em lote e visualização das informações da carteira.

####### ORDEM DE COMPRA, VENDA #######

Para realizar um ordem de compra ou de venda é necessário informar além do CPF do usuário, o código da empresa, o tipo
da operação (BUY ou SELL) e a quantidade de ações.

- Para criar uma ordem:
http://localhost:8080/broker/swagger-ui.html#/Order%20Service/createUsingPOST_2
POST http://localhost:8080/broker/api/v1/orders

No retorno da operação será apresentada além das informações passadas na criação, o preço unitário pago, o preço total,
a data da operação e o status que esterá PENDENTE até que a operação seja concluída (o processamento é assincrono).

- Para visualizar as operações:
http://localhost:8080/broker/swagger-ui.html#/Order%20Service/findUsingGET_1
GET http://localhost:8080/broker/api/v1/orders

- Filtrar as operações pelo código da empresa:
http://localhost:8080/broker/swagger-ui.html#/Order%20Service/findUsingGET_1
GET http://localhost:8080/broker/api/v1/orders?code={CODIGO_DA_EMPRESA}

- Buscar as operações pelo ID da ordem:
http://localhost:8080/broker/swagger-ui.html#/Order%20Service/findByIdUsingGET
GET http://localhost:8080/broker/api/v1/orders/{ID_DA_ORDEM}

####### ORDEM DE COMPRA EM LOTE #######

Também é possível informar apenas o CPF, o valor desejado para investir e a quantidade de ativos e deixar que o sistema
selecione ações para a sua carteira de forma automática. O sistema começará selecionando as ações de menor valor
disponíveis para que sejá possível diversificar mais a carteira e no final da operação mostrará no retorno o total que
foi utilizado, o valor não utilizado (troco) e a lista de ordens abertas para a compra das ações. Cada ação selecionada
irá gerar uma ordem de compra separada.

- Comprar ações em lote:
http://localhost:8080/broker/swagger-ui.html#/Order%20Service/createUsingPOST_1
POST http://localhost:8080/broker/api/v1/orders/random

####### PROCESSAMENTO DAS ORDENS #######

Após criar ordens de compra e venda, as ordens são processadas para serem adicionadas na carteira do usuário. Esse
processamento é realizado de forma assíncrona e após a conclusão é atualizado o status das ordens para OK ou CANCELLED.

Caso o usuário não possua a ação da ordem na carteira, a ação será adicionada a carteira com as informações de valor
total na ação e quantidade de ações.

Caso o usuário já possua a ação na carteira, as informações de quantidade de valor total investido naquela ação serão
atualizados adicionando os valores presentes na ordem.

Quando a ordem é de venda, será realizada a validação se o usuário possui a ação na carteira e se a quantidade que ele
está vendendo não é superior a quantidade de ações que ele possui. Caso alguma das validações falhe, a ordem é
atualizada para o status CANCELLED e a não acontece nada com as ações da carteira do usuário. Se não houver qualquer
problema na ordem de venda, a quantidade de ações será subtraida da sua carteira e o valor total investido também.

####### VISUALIZAÇÃO DA CARTEIRA #######

Após realizar ordens de compra e venda o usuário poderá visualizar as informações de sua carteira. Ao buscar a cartira
do usuário será apresentado o valor total dos ativos da cartira.

- Buscar carteira
http://localhost:8080/broker/swagger-ui.html#/Wallet%20Service/findUsingGET_3
GET http://localhost:8080/broker/api/v1/wallets/{CPF_DU_USUARIO}

O usuário poderá buscar também todas as ordens ja realizadas na carteira. Ao buscar pelas ordens ele poderá verificar se
as ordens foram aprovadas ou não a partir do status presente nas ordens.

- Buscar ordens da carteira
http://localhost:8080/broker/swagger-ui.html#/Wallet%20Service/findOrdersUsingGET
GET http://localhost:8080/broker/api/v1/wallets/{CPF_DU_USUARIO}/orders

Além das informações da cartira e das ordens, o usuário poderá buscar a lista de ações que compõem a sua carteira. Será
apresentado ao usuário o código da ação, a quantidade de ações, o valor total investido na ação e o preço médio pago na
ação.

- Buscar ações da carteira
http://localhost:8080/broker/swagger-ui.html#/Wallet%20Service/findStocksUsingGET
GET http://localhost:8080/broker/api/v1/wallets/{CPF_DU_USUARIO}/stocks
