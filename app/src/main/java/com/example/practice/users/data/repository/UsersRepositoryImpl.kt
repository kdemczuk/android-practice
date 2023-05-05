package com.example.practice.users.data.repository

import com.example.practice.users.data.mapper.toDomainModel
import com.example.practice.users.data.remote.api.UserApi
import com.example.practice.users.domain.model.User
import com.example.practice.users.domain.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val userApi: UserApi
) : UsersRepository {
    override fun getUsers(): Flow<List<User>> = flow {
        // requesting users from API
        val usersDomainModel = userApi.getUsers()
            .map {
                // mapping to domain model
                it.toDomainModel()
            }
        // emitting mapped domain models of users
        emit(usersDomainModel)
    }.flowOn(Dispatchers.IO)
}