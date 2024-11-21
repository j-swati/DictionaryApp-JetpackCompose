package com.project.dictionaryapplication.data.network

import com.project.dictionaryapplication.data.model.WordResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {
    @GET("api/v2/entries/en/{word}")
    suspend fun getWordDefinition(@Path("word") word: String): List<WordResponse>
}