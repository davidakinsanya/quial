package com.quial.app.data.auth

import kotlinx.serialization.Serializable

@Serializable
data class GoogleUserInfo(
    var iss: String = "",
    var azp: String = "",
    var aud: String = "",
    var sub: String = "",
    var email: String = "",
    var email_verified: String = "",
    var name: String = "",
    var picture: String = "",
    var given_name: String = "",
    var family_name: String = "",
    var  iat: String = "",
    var exp: String = "",
    var alg: String = "",
    var kid: String = "",
    var typ: String = ""
)

