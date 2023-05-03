package com.example.practice.users.domain.model

sealed class Error(val message: String) {
    object InternetConnection : Error("An unexpected error occured")

    object UnexpectedError : Error("Couldn't reach server. Check your internet connection.")
}
