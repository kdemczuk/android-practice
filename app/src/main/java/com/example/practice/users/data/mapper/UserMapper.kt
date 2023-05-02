package com.example.practice.users.data.mapper

import com.example.practice.users.data.remote.model.UserResponse
import com.example.practice.users.domain.model.User

fun UserResponse.toDomainModel() = User(
    id = id,
    name = name,
    email = email,
    city = this.address.city,
    companyName = this.company.name
)