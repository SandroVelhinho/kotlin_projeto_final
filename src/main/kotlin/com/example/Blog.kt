package com.example

import com.fasterxml.jackson.annotation.Nulls
import com.mongodb.ConnectionString
import com.mongodb.client.MongoClients
import jdk.jfr.Description
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.*
import org.litote.kmongo.insertOne

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

    fun getAllPosts(): List<Post> {

        val response = posts.find().toList()


        return response
    }

    fun getPostById(id: String): Post? {
        return try {
            // val objectId = converIdInObjectId(id)
            posts.findOneById(id)
        } catch (e: Exception) {
            null
        }
    }

    fun newPost(post: Post): String {
        val response = posts.insertOne(post)
        return response.toString()
    }

    fun updatePost(id: String, post: Post): Boolean {

        // val objectId = converIdInObjectId(id)
        val resultado = posts.updateOneById(id, post)
        return resultado.matchedCount > 0
    }

    fun deletePost(id: String): Boolean {
        //  val objectId = converIdInObjectId(id)
        val resultado = posts.deleteOneById(id)
        return resultado.deletedCount > 0
    }

    fun plusLikes(id: String): Boolean {
        //val objectId = converIdInObjectId(id)


        val result = posts.updateOneById(id, inc(Post::likes, 1))

        return result.matchedCount > 0


    }

    fun minusLikes(id: String): Boolean {
        //  val objectId = converIdInObjectId(id)


        val result = posts.updateOneById(id, inc(Post::likes, -1))

        return result.matchedCount > 0


    }

    fun addComment(id: String, comment: String): Boolean {

        try {
            //  val objectId = converIdInObjectId(id)
            val result = posts.updateOneById(id, push(Post::comments, comment))
            if (result.matchedCount.toInt() == 0) return false
            return true
        } catch (e: Exception) {
            return false
        }


    }


}


// função que foi abandonada devido a problemas com a conversão de ObjectId para kotlin data
fun converIdInObjectId(id: String): org.bson.types.ObjectId {
    val objectId = id.let { org.bson.types.ObjectId(it) }

    return objectId
}


