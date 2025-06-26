package com.example.vault.data

import com.example.vault.data.dao.UserDao
import com.example.vault.data.entities.User
import java.security.MessageDigest

class AuthRepository(private val userDao: UserDao) {

    suspend fun registerUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun loginUser(username: String, passwordAttempt: String): User? {
        val user = userDao.getUserByUsername(username)
        if (user != null && user.passwordHash == hashPassword(passwordAttempt)) {
            return user
        }
        return null
    }

    // Simple and insecure hashing for demonstration.
    // In a real app, use a stronger library like bcrypt.
    fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    suspend fun getUser(userId: Long): User? {
        return userDao.getUserById(userId)
    }
}