package com.example

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun Application.configureRouting() {
    routing {

        route("/blog") {
            val blogDb = Blog()

            get("/") {
                val allPosts = blogDb.getAllPosts()

                call.respond(allPosts.toString())
            }
            post("/new") {
                try {
                    val receiveJson = call.receive<String>()

                    val newPost = Json.decodeFromString<Post>(receiveJson)

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
        }
        route("/bookstore") {
            get("/") {


            }
        }
    }
}
