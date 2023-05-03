package com.example.practice.users.domain.repository

import com.example.practice.users.domain.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Users repository interface to abstract a source of data. Implementation is to decide on the
 * details, remote, local or else.
 */
interface UsersRepository {
    fun getUsers(): Flow<List<User>>
}