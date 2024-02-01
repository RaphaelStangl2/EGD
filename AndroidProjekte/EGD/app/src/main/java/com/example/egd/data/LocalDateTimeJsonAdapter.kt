package com.example.egd.data

import com.squareup.moshi.*

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class LocalDateJsonAdapter: JsonAdapter<LocalDate>(){
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @ToJson
    override fun toJson(writer: JsonWriter, value: LocalDate?) {
        value?.let { writer.value(it.format(formatter)) }
    }

    @FromJson
    override fun fromJson(reader: JsonReader): LocalDate? {
        return if (reader.peek() != JsonReader.Token.NULL) {
            LocalDate.parse(reader.nextString(), formatter)
        } else {
            reader.nextNull<Any>()
            null
        }
    }
    //private fun fromNonNullString(nextString: String) : LocalDate = LocalDate.parse(nextString, formatter)
}