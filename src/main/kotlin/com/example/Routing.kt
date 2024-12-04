package com.example

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.litote.kmongo.MongoOperator
import java.util.UUID
import kotlin.uuid.Uuid

fun Application.configureRouting() {
    routing {

        route("/blog") {
            val blogDb = Blog()
            val accountDb = Account()

            route("/likes") {
                post("/plus") {
                    try {
                        val receiveJson = call.receive<String>()
                        val id = Json.decodeFromString<Id>(receiveJson).id
                        blogDb.plusLikes(id)
                        call.respond("Likes incremented in ${id}")
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest, "Error: ${e.localizedMessage}")
                    }
                }
                post("/minus") {
                    try {
                        val receiveJson = call.receive<String>()
                        val id = Json.decodeFromString<Id>(receiveJson).id
                        blogDb.minusLikes(id)
                        call.respond("Likes decremented in ${id}")
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest, "Error: ${e.localizedMessage}")
                    }


                }
            }

            route("/account") {
                post("/create") {
                    val receiveJson = call.receive<String>()
                    val body = Json.decodeFromString<CredentialsVerify>(receiveJson)

                    try {
                        val returnString = accountDb.createAccount(body)

                        call.respond(returnString)

                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest, "Error: ${e.localizedMessage}")
                    }


                }
                post("/login") {
                    try {
                        val receiveJson = call.receive<String>()
                        val body = Json.decodeFromString<AccountInfo>(receiveJson)

                        val returnString = accountDb.login(body)

                        call.respond(returnString)

                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest, "Error: ${e.localizedMessage}")
                    }


                }
                post("/newpassword") {
                    try {
                        val receiveJson = call.receive<String>()
                        val body = Json.decodeFromString<CredencialsResetPassword>(receiveJson)

                        val returnString = accountDb.resetPassword(body)

                        call.respond(returnString)

                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest, "Error: ${e.localizedMessage}")
                    }
                }
            }

            get("/") {
                val allPosts = blogDb.getAllPosts()

                call.respond(allPosts.toString())
            }
            post("/new") {
                try {
                    val receiveJson = call.receive<String>()
                    val receivedPost = Json.decodeFromString<Post>(receiveJson)

                    val newPost = receivedPost.copy(id = UUID.randomUUID().toString()) //utilizei UUID por não estar a conseguir funcionar a 100% com o ObjectId do mongoDB

                    blogDb.newPost(newPost)
                    call.respond("Post added! ${newPost}")

                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error: ${e.localizedMessage}")
                }


            }
            post("/updatepost") {
                try {
                    val receiveJson = call.receive<String>()
                    val updatedPost = Json.decodeFromString<Post>(receiveJson)

                    val validation = blogDb.updatePost(updatedPost.id, updatedPost)

                    println("%%%%%%%%%%%%%%%%%%%%%%%%%% " + validation)
                    if (validation) {
                        call.respond("Post updated, ${updatedPost}")
                    } else call.respond("Error")


                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error: ${e.localizedMessage}")
                }
            }
            get("/getapost") {
                try {
                    val receiveJson = call.receive<String>()
                    val id = Json.decodeFromString<Id>(receiveJson).id

                    val postFound = blogDb.getPostById(id)

                    println("%%%%%%%%%%%%%%%%%%%%%%%%%% " + postFound)
                    if (postFound != null) {

                        call.respond(postFound.toString())
                    } else call.respond("Error")

                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error: ${e.localizedMessage}")
                }
            }
            delete("/deletepost") {
                val receiveJson = call.receive<String>()
                val id = Json.decodeFromString<Id>(receiveJson).id
                val result = blogDb.deletePost(id)

                if (result) {
                    call.respond("Post deleteted")
                } else call.respond("Error")
            }
            post("/addcomment") {
                val receiveJson = call.receive<String>()
                val id = Json.decodeFromString<Comment>(receiveJson).id
                val comment = Json.decodeFromString<Comment>(receiveJson).comment

                try {
                    blogDb.addComment(id, comment)
                    call.respond("Comment: ${comment} added to id: ${id}")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error: ${e.localizedMessage}")
                }

            }

        }
        route("/bookstore") {
            get("/") {


            }
        }
    }
}
