package com.example.practice.users.presentation.users

import com.example.practice.users.domain.model.Error
import com.example.practice.users.domain.model.User

data class UsersState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val error: Error? = null
)
