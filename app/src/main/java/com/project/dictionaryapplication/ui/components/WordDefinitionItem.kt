package com.project.dictionaryapplication.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.project.dictionaryapplication.data.model.Meaning

@Composable
fun WordDefinitionItem(meaning: Meaning) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(8.dp)
        ) {
            Text(
                text = meaning.partOfSpeech,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            meaning.definitions.forEach { definition ->
                Text(
                    text = definition.definition,
                    style = MaterialTheme.typography.bodyMedium
                )
                definition.example?.let {
                    Text(
                        text = "Example: $it",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            // Add synonyms and antonyms if available
            if (meaning.synonyms.isNotEmpty()) {
                Text("Synonyms: ${meaning.synonyms.joinToString(", ")}")
            }
            if (meaning.antonyms.isNotEmpty()) {
                Text("Antonyms: ${meaning.antonyms.joinToString(", ")}")
            }
        }
    }
}