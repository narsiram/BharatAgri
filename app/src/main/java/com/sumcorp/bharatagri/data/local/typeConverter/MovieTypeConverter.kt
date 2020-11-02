package com.sumcorp.bharatagri.data.local.typeConverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sumcorp.bharatagri.data.local.entity.model.MovieData
import java.lang.reflect.Type

object MovieTypeConverter {

    @TypeConverter
    @JvmStatic
    fun fromList(value: ArrayList<MovieData>) = Gson().toJson(value)

    @TypeConverter
    @JvmStatic
    fun toList(value: String): ArrayList<MovieData> {
        val listType: Type = object : TypeToken<List<MovieData>>() {}.type
        return Gson().fromJson(value, listType)
    }
}