package com.project.dictionaryapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.dictionaryapplication.data.model.WordResponse
import com.project.dictionaryapplication.data.repository.DictionaryRepository
import com.project.dictionaryapplication.data.repository.WordNotFoundException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DictionaryViewModel(private val repository: DictionaryRepository) : ViewModel() {

    private val _wordDefinition = MutableStateFlow<WordDefinitionState>(WordDefinitionState.Loading)
    val wordDefinition: StateFlow<WordDefinitionState> = _wordDefinition

    fun searchWord(word: String) {
        viewModelScope.launch {
            _wordDefinition.value = WordDefinitionState.Loading
            val result = repository.getWordDefinition(word)

            result.fold(
                onSuccess = { data ->
                    _wordDefinition.value = WordDefinitionState.Success(data)
                },
                onFailure = { exception ->
                    val errorMessage = when (exception) {
                        is WordNotFoundException -> exception.message ?: "Word not found" // Handle null message
                        else -> "An error occurred"
                    }
                    _wordDefinition.value = WordDefinitionState.Error(errorMessage)
                }
            )
        }
    }
}

sealed class WordDefinitionState {
    data object Loading : WordDefinitionState() // Converted to data object
    data class Success(val data: List<WordResponse>) : WordDefinitionState()
    data class Error(val message: String) : WordDefinitionState()
}