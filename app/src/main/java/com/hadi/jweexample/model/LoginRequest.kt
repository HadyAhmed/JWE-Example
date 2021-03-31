package com.hadi.jweexample.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("Username")
    val username: String,
    @SerializedName("Password")
    val password: String
)