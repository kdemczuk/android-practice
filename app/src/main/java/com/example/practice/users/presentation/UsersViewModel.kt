package com.example.practice.users.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice.users.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    fun requestUsers() {
        viewModelScope.launch {
            getUsersUseCase.getUsers()
                .collect { result ->
                    result.onSuccess { list ->
                        list.forEach {
                            Timber.d("Users: $it")
                        }
                    }
                        .onFailure {
                            Timber.e("Users failure $it")
                        }
                }
        }

    }
}