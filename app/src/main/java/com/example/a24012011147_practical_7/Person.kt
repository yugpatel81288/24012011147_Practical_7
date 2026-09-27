package com.example.a24012011147_practical_7

import org.json.JSONObject
import java.io.Serializable

class Person(
    var id: String,
    var name: String,
    var emailId: String,
    var phoneNo: String,
    var address: String,
) : Serializable {

    // !! MODIFIED THIS CONSTRUCTOR TO BE SAFER !!
    constructor(jsonObject: JSONObject) : this(
        id = "",
        name = "",
        emailId = "",
        phoneNo = "",
        address = ""
    ) {
        // optString will return an empty string "" instead of crashing if the key is missing
        id = jsonObject.optString("id")
        emailId = jsonObject.optString("email")
        phoneNo = jsonObject.optString("phone")

        // optJSONObject will return null instead of crashing
        val profileJson = jsonObject.optJSONObject("profile")
        if (profileJson != null) {
            name = profileJson.optString("name")
            address = profileJson.optString("address")
        }
    }

    override fun toString(): String {
        return "$name\n$phoneNo\n$emailId\n$address"
    }
}