package com.example.shreebhagavadgita.DataSource.SharedPrep

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(var context: Context) {
    val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("saved_chapters", Context.MODE_PRIVATE)
    }

    fun getAllSavedChapterKeySP(): Set<String> {
        return sharedPreferences.all.keys
    }

    fun putSavedChapterSP(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun deleteSavedChapterSP(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    val sharedPreferences1: SharedPreferences by lazy {
        context.getSharedPreferences("saved_verses", Context.MODE_PRIVATE)
    }

    fun getAllSavedVersesKeySP(): Set<String> {
        return sharedPreferences.all.keys
    }

    fun putSavedVersesSP(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun deleteSavedVersesSP(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}