package com.example.practice.users.domain.repository

import com.example.practice.users.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun getUsers(): Flow<List<User>>
}