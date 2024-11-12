package ar.edu.unlam.mobile.scaffolding.evolution.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun SearchHero(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    searchEnabled: Boolean,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = query,
        enabled = searchEnabled,
        onValueChange = { onQueryChange(it) },
        placeholder = {
            if (searchEnabled) {
                Text(text = "Search...")
            } else {
                Text(text = "Disable , sin internet...")
            }
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
            )
        },
        keyboardOptions =
            KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search,
            ),
        keyboardActions =
            KeyboardActions(
                onSearch = {
                    onSearch()
                    keyboardController?.hide()
                },
            ),
        singleLine = true,
        modifier =
            Modifier
                .fillMaxWidth(),
    )
}
