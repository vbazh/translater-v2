package com.vbazh.words.translate.data

import java.lang.Exception

class LangDidntPickedException : Exception() {
    override val message: String?
        get() = "Language wasn't picked!"
}