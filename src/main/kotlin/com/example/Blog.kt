package com.example

import com.fasterxml.jackson.annotation.Nulls
import com.mongodb.ConnectionString
import com.mongodb.client.MongoClients
import jdk.jfr.Description
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.*

@Serializable
data class Post(
    @BsonId val id: String,
    val title: String,
    val description: String,
    val likes: Int,
    val comments: List<String>
)

@Serializable
data class Id(
    val id: String,
)

class Blog {

    private val client =
        KMongo.createClient(ConnectionString("mongodb+srv://sandrovelhinho:1263@cluster0.iqrnn.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"))
    private val database = client.getDatabase("Blog")
    private val posts = database.getCollection<Post>("Post")

    fun getAllPosts(): List<Post> = posts.find().toList()

    fun getPostById(id: String): Post? {
        return try {
            val objectId = id.let { org.bson.types.ObjectId(it) }
            posts.findOneById(objectId)
        } catch (e: Exception) {
            null
        }
    }

    fun newPost(post: Post) = posts.insertOne(post)

    fun updatePost(id: String, post: Post): Boolean {
        val resultado = posts.updateOneById(id, post)
        return resultado.matchedCount > 0
    }

    fun deletePost(id: String): Boolean {
        val resultado = posts.deleteOneById(id)
        return resultado.deletedCount > 0
    }


}