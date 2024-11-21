package com.project.dictionaryapplication.data.model

data class WordResponse(
    val word: String,
    val phonetics: List<Phonetic>,
    val meanings: List<Meaning>,
    val license: License,
    val sourceUrls: List<String>
)