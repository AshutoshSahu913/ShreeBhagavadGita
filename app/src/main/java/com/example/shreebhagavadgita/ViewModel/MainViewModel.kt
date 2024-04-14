package com.example.shreebhagavadgita.ViewModel

import androidx.lifecycle.ViewModel
import com.example.shreebhagavadgita.DataSource.Models.Chapters
import com.example.shreebhagavadgita.Repository.AppRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel : ViewModel() {
    val appRepository = AppRepository()
    fun getAllChapter(): Flow<Chapters> = appRepository.getAllChapters()
}