package com.example.shreebhagavadgita.DataSource.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SavedChapters")
data class SavedChapterEntity(
    val chapter_number: Int,
    val chapter_summary: String,
    val chapter_summary_hindi: String,
    @PrimaryKey
    val id: Int,
    val name: String,
    val name_meaning: String,
    val name_translated: String,
    val name_transliterated: String,
    val slug: String,
    val verses_count: Int,
    //iske return type ko handle karne ke liye type converter ka use karte h
    val verses: List<String>
)
