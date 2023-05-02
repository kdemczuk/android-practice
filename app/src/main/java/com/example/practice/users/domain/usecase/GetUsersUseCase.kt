package com.example.practice.users.domain.usecase

import com.example.practice.users.domain.model.User
import com.example.practice.users.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GetUsersUseCase {
    fun getUsers(excludedUserId: Int): Flow<Result<List<User>>>
}

class GetUsersUseCaseImpl @Inject constructor(
    private val usersRepository: UsersRepository
) : GetUsersUseCase {
    override fun getUsers(excludedUserId: Int): Flow<Result<List<User>>> {
        return usersRepository.getUsers().map { users ->
            Result.success(users.filter { it.id != excludedUserId }.reversed())
        }
            .catch {
                emit(Result.failure(it))
            }
    }
}