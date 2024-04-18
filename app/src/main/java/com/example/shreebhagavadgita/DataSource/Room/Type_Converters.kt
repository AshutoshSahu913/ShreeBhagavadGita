package com.example.shreebhagavadgita.DataSource.Room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

//Room SQL based database jo only primitive type of datatype ko support karta hai
//ye List<String> jaise datatype ko support ni karta hai isliye typeConveter ka use karte hai
//if it is not use type converter app will be crashed
class Type_Converters {

    //    create two for any non primitive datatype
    // list ko string m convert kar dega
    @TypeConverter
    fun fromListToString(list: List<String>): String {
        //ye Gson ko json format m convert kar dega
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToList(string: String): List<String> {
//ye bale string ko list m convert kar dega

        return Gson().fromJson(string, object : TypeToken<List<String>>() {
        }.type)
    }


}