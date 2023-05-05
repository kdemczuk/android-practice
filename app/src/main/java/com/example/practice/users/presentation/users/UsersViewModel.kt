package com.example.practice.users.presentation.users

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice.users.domain.model.Error
import com.example.practice.users.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _state = mutableStateOf(UsersState())
    val state: State<UsersState> = _state

    init {
        requestUsers()
    }

    fun requestUsers(excludedUserId: Int? = null) {
        // setting loading state
        _state.value = UsersState(isLoading = true)
        viewModelScope.launch {
            // requesting users from use case
            getUsersUseCase.getUsers(excludedUserId).collect { result ->
                result
                    .onSuccess { list ->
                        // setting success state for observers
                        _state.value = UsersState(users = list)
                    }
                    .onFailure {
                        // defining error cases
                        val error = when (it) {
                            is IOException -> {
                                Error.InternetConnection
                            }

                            else -> {
                                Error.UnexpectedError
                            }
                        }
                        // setting error state for state observers
                        _state.value = UsersState(error = error)
                    }
            }
        }
    }
}