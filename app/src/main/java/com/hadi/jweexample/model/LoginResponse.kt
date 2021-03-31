package com.hadi.jweexample.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("Id")
    val id: String,
//    @SerializedName("FirstName")
//    val firstName: String,
//    @SerializedName("LastName")
//    val lastName: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Number")
    val number: String,
    @SerializedName("AuthToken")
    val authToken: String,
    @SerializedName("IsConfirmed")
    val isConfirmed: Boolean,
    @SerializedName("CountryId")
    val countryID: String,
    @SerializedName("BirthDate")
    val birthDate: String,
    @SerializedName("Address")
    val address: String,
    @SerializedName("Avatar")
    var avatar: String? = null,
    @SerializedName("IsActive")
    val isActive: Boolean,
    @SerializedName("UserStatus")
    val userStatus: Int,
    @SerializedName("Email")
    var email: String,
    @SerializedName("NationalNumber")
    val nationalNumber: String? = null,
    @SerializedName("ReferrerId")
    var referrerId: String? = null,
    @SerializedName("Latitude")
    val latitude: String? = null,
    @SerializedName("Longitude")
    val longitude: String? = null,
    @SerializedName("AdditionalAddress")
    var additionalAddress: String? = null,
    // Ouch
    @SerializedName("NameAr")
    val nameAr: String,
    @SerializedName("GenerateWebTokenJTI")
    val generateWebTokenJTI: String? = null
)