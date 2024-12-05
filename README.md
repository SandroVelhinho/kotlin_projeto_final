# Projeto CRUD com kotlin
______________________________
# tecnologias

> MongoDb (database connection)

> Ktor (kotlin framework)

> Kmongo (Lib de integração MongoDb para kotlin)

> UUID (Criador de Id unica)


Ps: em caso de erro na conecção com a DataBase envie mail para mim "sfgvelhinho@gmail.com" a dizer para libertar a database para todos os ip's.
______________________________
# PATHS


# adicionar 1 like de um post
POST "http://127.0.0.1:8080/blog/likes/plus"

Necessario um id de um document da collention Post
>body exemple

"{
    "id": "673e8019899f964900b4f3ef"
}"

# retirar 1 like post
POST "http://127.0.0.1:8080/blog/likes/plus"

Necessario um id de um document da collention Post
>body exemple

"{
    "id": "673e8019899f964900b4f3ef"
}"

# criar uma conta nova
POST "http://127.0.0.1:8080/blog/account/create"

Necessario um Json com email, password e confirmpassword no body
>body exemple

"{
    "email": "mail@testsuper.com",
    "password": "1234324",
    "confirmPassword" : "blabla", 
}"

# Login numa conta existente
POST "http://127.0.0.1:8080/blog/account/login"

Necessario um Json com email password no body
>body exemple

"{
    "email": "mail@testsuper.com",
    "password": "1234324",
}"

# Atualizar password de uma conta existente
POST "http://127.0.0.1:8080/blog/account/newpassword"

Necessario um Json com email password, newpass e confrimnewpass no body
>body exemple

"{
    "email": "mail@testsuper.com",
    "password": "1234324",
    "newPassword" : "blabla",
    "confirmNewPassword" : "blabla" 
}"

# Receber todos os Post na base
GET "http://127.0.0.1:8080/blog"


# Criar um Novo Post
POST "http://127.0.0.1:8080/blog/new"

Necessario um Json com id, title, descripion, likes, comments no body
>body exemple

"{
    "id": ""(o id tem de ter uma string,de preferencia vazia, o UUID trata de criar um id para este post)
    "title": "title blabla",
    "description": "blablalbal",
    "likes" : 2,
    "comments" : [""] (obrigatorio ser uma array de Strings) 
}"  

# Editar um Post existente
POST "http://127.0.0.1:8080/blog/updatepost"

Necessario um Json com id, title, descripion, likes, comments no body


>body exemple

"{
    "id": "1b23b553b65rg",(necessario ser um id de um post existente )
    "title": "title blabla",
    "description": "blablalbal",
    "likes" : 2,
    "comments" : [""] (obrigatorio ser uma array de Strings) 
}"  

# Ler um Post
GET "http://127.0.0.1:8080/blog/getapost"

Necessario um Json com id


>body exemple

"{
    "id": "1b23b553b65rg"(necessario ser um id de um post existente ) 
}"  

# Apagar um Post
GET "http://127.0.0.1:8080/blog/deletepost"

Necessario um Json com id


>body exemple

"{
    "id": "1b23b553b65rg"(necessario ser um id de um post existente ) 
}"  

# Adicionar um Comentario a um Post existente
GET "http://127.0.0.1:8080/blog/addcomment"

Necessario um Json com id e uma String de um comentario


>body exemple

"{
    "id": "1b23b553b65rg",(necessario ser um id de um post existente ) 
    "comment": "novo comentario"
}"  







