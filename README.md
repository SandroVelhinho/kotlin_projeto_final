# Projeto CRUD com kotlin
______________________________
# tecnologias

> MongoDb (database connection)
> Ktor (kotlin framework)
> Kmongo (Lib de integração MongoDb para kotlin)



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

# receber todo os Post na base
GET "http://127.0.0.1:8080/blog"


# Criar um Novo Post
POST "http://127.0.0.1:8080/blog/new"

Necessario um Json com email password, newpass e confrimnewpass no body
>body exemple

"{
    "title": "title blabla",
    "description": "blablalbal",
    "likes" : 2,
    "confirmNewPassword" : "blabla" 
}"  








