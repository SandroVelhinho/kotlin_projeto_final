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

@Serializable
data class Comment(
    val id: String,
    val comment: String
)

class Blog {

    private val client =
        KMongo.createClient(ConnectionString("mongodb+srv://sandrovelhinho:1263@cluster0.iqrnn.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"))
    private val database = client.getDatabase("Blog")
    private val posts = database.getCollection<Post>("Post")

    fun getAllPosts(): List<Post> = posts.find().toList()

    fun getPostById(id: String): Post? {
        return try {
            val objectId = converIdInObjectId(id)
            posts.findOneById(objectId)
        } catch (e: Exception) {
            null
        }
    }

    fun newPost(post: Post) = posts.insertOne(post)

    fun updatePost(id: String, post: Post): Boolean {
        val objectId = converIdInObjectId(id)
        val resultado = posts.updateOneById(objectId, post)
        return resultado.matchedCount > 0
    }

    fun deletePost(id: String): Boolean {
        val objectId = converIdInObjectId(id)
        val resultado = posts.deleteOneById(objectId)
        return resultado.deletedCount > 0
    }

    fun plusLikes(id: String): Boolean {
        val objectId = converIdInObjectId(id)


        val result = posts.updateOneById(objectId, inc(Post::likes, 1))

        return result.matchedCount > 0


    }

    fun minusLikes(id: String): Boolean {
        val objectId = converIdInObjectId(id)


        val result = posts.updateOneById(objectId, inc(Post::likes, -1))

        return result.matchedCount > 0


    }

    fun addComment(id: String, comment: String): Boolean {

        try {
            val objectId = converIdInObjectId(id)
            val result = posts.updateOneById(objectId, push(Post::comments, comment))
            return true
        } catch (e: Exception) {
            return false
        }


    }


}

fun converIdInObjectId(id: String): org.bson.types.ObjectId {
    val objectId = id.let { org.bson.types.ObjectId(it) }

    return objectId
}


