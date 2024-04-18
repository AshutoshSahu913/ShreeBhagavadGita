package com.example.shreebhagavadgita.DataSource.Room

import androidx.room.TypeConverter
import com.example.shreebhagavadgita.DataSource.Models.Commentary
import com.example.shreebhagavadgita.DataSource.Models.Translation
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

    @TypeConverter
    fun fromTransToString(list: List<Translation>): String {
        //ye Gson ko json format m convert kar dega
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToTrans(string: String): List<Translation> {
        //ye bale string ko Translation m convert kar dega
        return Gson().fromJson(string, object : TypeToken<List<Translation>>() {
        }.type)
    }

    @TypeConverter
    fun fromCommentaryToString(list: List<Commentary>): String {
        //ye Gson ko json format m convert kar dega
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToCommentary(string: String): List<Commentary> {
        //ye bale string ko Translation m convert kar dega
        return Gson().fromJson(string, object : TypeToken<List<Commentary>>() {
        }.type)
    }


}