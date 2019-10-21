package com.vbazh.words.data.remote.response

import com.google.gson.annotations.SerializedName

data class TranslateResponse(
    @SerializedName("lang") val lang: String,
    @SerializedName("text") val text: List<String>
)