package com.kamilnazar.dynamicform.form.serializer

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Form(
    @Json(name = "form-name") val formName: String = "",
    @Json(name = "fields") val fields: List<FormField> = listOf()
)