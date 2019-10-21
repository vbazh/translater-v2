package com.vbazh.words.data.remote.response

import com.google.gson.annotations.SerializedName

data class LanguagesResponse(
    @SerializedName("langs")
    val languages: Map<String, String>
)