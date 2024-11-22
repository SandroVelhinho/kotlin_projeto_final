package com.example

import com.mongodb.ConnectionString
import kotlinx.serialization.Serializable
import org.litote.kmongo.*


@Serializable
data class CredentialsVerify(
    val email: String,
    val password: String,
    val confirmPassword: String
) //Objeto que recebe as credenciais iniciais

@Serializable
data class AccountInfo(
    val email: String,
    val password: String
) //objeto que recebe credenciais defenitivas para inserir na Data Base

@Serializable
data class CredencialsResetPassword(
    val email: String,
    val password: String,
    val newPassword: String,
    val confirmNewPassword: String

)

class Account {
    private val client =
        KMongo.createClient(ConnectionString("mongodb+srv://sandrovelhinho:1263@cluster0.iqrnn.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"))
    private val database = client.getDatabase("Blog")
    private val accountsCollection = database.getCollection<AccountInfo>("Accounts")

    fun createAccount(credentials: CredentialsVerify): String {


        if (credentials.email.isNullOrBlank()) return "Email required"
        if (credentials.password.isNullOrBlank() || credentials.confirmPassword.isNullOrBlank()) return "Password required"
        if (credentials.password != credentials.confirmPassword) return "Passwords doesnt match"
        val emailExistence = accountsCollection.find(AccountInfo::email eq credentials.email)
        if (emailExistence != null) return "Email already exists"

        val personAccount = AccountInfo(email = credentials.email, password = credentials.password)

        accountsCollection.insertOne(personAccount)

        return "Account: $personAccount was successfully created"
    }

    fun login(credentials: AccountInfo): String {
        if (credentials.email.isNullOrBlank()) return "Email required"
        if (credentials.password.isNullOrBlank()) return "Password required"
        val account = accountsCollection.findOne(AccountInfo::email eq credentials.email)
        if (account == null) return "Account do not exist"
        if (account.password != credentials.password) return "Incorrect password"

        return "Login successful"
    }

    fun resetPassword(credentials: CredencialsResetPassword): String {
        if (credentials.email.isNullOrBlank()) return "Email required"
        if (credentials.password.isNullOrBlank()) return "Password required"
        if (credentials.newPassword.isNullOrBlank() || credentials.confirmNewPassword.isNullOrBlank()) return "Pls confirm ur new password"
        if (credentials.newPassword != credentials.confirmNewPassword) return "Confirm new passwords doesnt match"
        val account = accountsCollection.findOne(AccountInfo::email eq credentials.email)
        if (account == null) return "Account do not exist"
        if (account.password != credentials.password) return "Incorrect password"


        val update = accountsCollection.findOneAndUpdate(
            AccountInfo::email eq credentials.email,
            setValue(AccountInfo::password, credentials.newPassword)
        )




        return "Account $update was updated"
    }
}