package com.example.practice.users.presentation.users

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice.users.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
        _state.value = UsersState(isLoading = true)
        viewModelScope.launch {
            getUsersUseCase.getUsers(excludedUserId).collect { result ->
                result
                    .onSuccess { list ->
                        _state.value = UsersState(users = list)
                    }
                    .onFailure {
                        UsersState(error = it.localizedMessage ?: "Error")
                    }
            }
        }
    }
}