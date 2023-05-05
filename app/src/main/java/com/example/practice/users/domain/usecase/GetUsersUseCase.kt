package com.example.practice.users.domain.usecase

import com.example.practice.users.domain.model.User
import com.example.practice.users.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Use case for getting users list
 */
interface GetUsersUseCase {
    /**
     * returns a Flow of Result containing a list of users or error state
     *
     * @param excludedUserId id of a user that must be excluded from returned list
     * @param reversed whether or not to reverse returned list
     */
    fun getUsers(excludedUserId: Int? = null, reversed: Boolean = false): Flow<Result<List<User>>>
}

class GetUsersUseCaseImpl @Inject constructor(
    private val usersRepository: UsersRepository
) : GetUsersUseCase {
    override fun getUsers(excludedUserId: Int?, reversed: Boolean): Flow<Result<List<User>>> {
        // requesting users from repository
        return usersRepository.getUsers()
            .map { users ->
                // filtering users with excluded user id
                val finalList = users.filter { it.id != excludedUserId }.let {
                    // defining final order of the list
                    if (reversed) {
                        it.reversed()
                    } else {
                        it
                    }
                }
                Result.success(finalList)
            }
            .catch {
                // catching exceptions that may appear inside the flow and emitting failure result
                emit(Result.failure(it))
            }
    }
}