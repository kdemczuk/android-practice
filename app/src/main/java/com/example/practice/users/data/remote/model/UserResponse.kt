package com.example.practice.users.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UserResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("username")
    val username: String,
    @SerialName("email")
    val email: String,
    @SerialName("address")
    val address: Address,
    @SerialName("phone")
    val phone: String,
    @SerialName("website")
    val website: String,
    @SerialName("company")
    val company: Company
)

@Serializable
data class Address(
    @SerialName("street")
    val street: String,
    @SerialName("suite")
    val suite: String,
    @SerialName("city")
    val city: String,
    @SerialName("zipcode")
    val zipcode: String,
    @SerialName("geo")
    val geo: Geo
)

@Serializable
data class Geo(
    @SerialName("lat")
    val lat: String,
    @SerialName("lng")
    val lng: String
)

@Serializable
data class Company(
    @SerialName("name")
    val name: String,
    @SerialName("catchPhrase")
    val catchPhrase: String,
    @SerialName("bs")
    val bs: String,
)