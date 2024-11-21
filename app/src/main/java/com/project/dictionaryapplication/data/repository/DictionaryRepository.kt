package com.project.dictionaryapplication.data.repository

import com.project.dictionaryapplication.data.model.WordResponse
import com.project.dictionaryapplication.data.network.DictionaryApi
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DictionaryRepository {

    private val api: DictionaryApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(DictionaryApi::class.java)
    }

    suspend fun getWordDefinition(word: String): Result<List<WordResponse>> {
        return try {
            val response = api.getWordDefinition(word)
            if (response.isEmpty() || response[0].meanings.isEmpty()) {
                Result.failure(WordNotFoundException("No definitions found for this word."))
            } else {
                Result.success(response)
            }
        } catch (e: HttpException) {
            if (e.code() == 404) { // Check for 404 Not Found error
                Result.failure(WordNotFoundException("No definitions found for this word."))
            } else {
                Result.failure(e)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class WordNotFoundException(message: String) : Exception(message)