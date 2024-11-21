package com.project.dictionaryapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.dictionaryapplication.data.repository.DictionaryRepository
import com.project.dictionaryapplication.ui.components.WordDefinitionItem
import com.project.dictionaryapplication.viewmodel.DictionaryViewModel
import com.project.dictionaryapplication.viewmodel.WordDefinitionState

@Composable
fun DictionaryScreen(modifier: Modifier = Modifier) {
    val repository = remember { DictionaryRepository() } // Create repository instance
    val viewModel: DictionaryViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(DictionaryViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return DictionaryViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    )

    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Enter a word") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.searchWord(searchQuery) }) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = viewModel.wordDefinition.collectAsState().value) {
            is WordDefinitionState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is WordDefinitionState.Success -> {
                if (state.data.isNotEmpty()) {
                    LazyColumn {
                        items(state.data[0].meanings) { meaning ->
                            WordDefinitionItem(meaning)
                        }
                    }
                } else {
                    Text("Word not found", color = MaterialTheme.colorScheme.error)
                }
            }
            is WordDefinitionState.Error -> {
                Text(text = state.message, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}