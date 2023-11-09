package no.vipps.summerinternship

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import no.vipps.summerinternship.data.Country
import no.vipps.summerinternship.ui.main.MainViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType",
    "StateFlowValueCalledInComposition"
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(countryViewModel: MainViewModel) {
    val uiState by countryViewModel.uiState.collectAsState()

    var filteredList by remember { mutableStateOf<List<Country>>(emptyList()) }
    var searchQuery by remember { mutableStateOf("") }

    when (uiState) {
        is UiState.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                color = Color(0xFFff5b24)
            )
        }
        is UiState.Success -> {
            val countryList = (uiState as UiState.Success).countries

            filteredList = countryList.filter { country ->
                country.name.common.contains(searchQuery, ignoreCase = true)
            }

            Scaffold(
                topBar = {
                    SearchBar(onSearch = { query -> searchQuery = query })
                }) {

                Box(modifier = Modifier.padding(top=50.dp)) {
                    CountryList(countryList = filteredList)
                }
            }
        }
        is UiState.Error -> {
            Text(text = "Error fetching countries", modifier = Modifier.padding(16.dp))
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                onSearch(searchQuery) },
            label = { Text("Search") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearch(searchQuery)
                keyboardController?.hide()
                focusManager.clearFocus()

            })
        )
}

@Composable
fun CountryList(countryList: List<Country>) {

    LazyColumn(Modifier.padding(30.dp)) {
        items(countryList.size) { country ->
            CountryItem(country = countryList[country])
        }
    }
}

@Composable
fun CountryItem(country: Country) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(Color(0xFFff5b24))
        ) {
            Column() {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = country.name.common,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = Color.White,
                        modifier = Modifier.padding(10.dp)
                    )
                    Text(
                        text = country.flag,
                        fontSize = 40.sp
                    )
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                ) {
                    val textColor = Color.White
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Alternative Spellings:")
                            }
                            append(" ${country.capital?.joinToString(", ")}")
                        },
                        color = textColor
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Number of alternative spellings:")
                            }
                            append(" ${country.altSpellings.size}")
                        },
                        color = textColor
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Alternative Spellings:")
                            }
                            append(" ${country.altSpellings.joinToString(", ")}")
                        },
                        color = textColor
                    )
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewMainScreen() {
    Scaffold(bottomBar = { }, topBar = { }) {

    }

}
