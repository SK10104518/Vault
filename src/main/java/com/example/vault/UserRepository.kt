package com.example.vault

import com.example.vault.data.dao.UserDao
import com.example.vault.data.entities.User

class UserRepository(private val userDao: UserDao) {
    suspend fun insert(user: User) {
        userDao.insertUser(user)
    }

    suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }
}