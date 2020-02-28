package com.kamilnazar.dynamicform.form.serializer

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FormField(
    @Json(name = "field-name") val fieldName: String,
    @Json(name = "type") val type: String,
    @Json(name = "options") val options: Array<String> = arrayOf(),
    @Json(name = "required") val required: Boolean = false,
    @Json(name = "min") val min: Int?,
    @Json(name = "max") val max: Int?,
    @Json(name = "fields") val fields: List<FormField> = listOf()
) {
    //auto generated
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FormField

        if (fieldName != other.fieldName) return false
        if (type != other.type) return false
        if (!options.contentEquals(other.options)) return false

        return true
    }

    //auto generated
    override fun hashCode(): Int {
        var result = fieldName.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + options.contentHashCode()
        return result
    }
}