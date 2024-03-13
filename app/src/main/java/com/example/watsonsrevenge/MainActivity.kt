package com.example.watsonsrevenge

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.example.watsonsrevenge.data.Clue
import com.example.watsonsrevenge.model.MainViewModel
import com.example.watsonsrevenge.model.Screen
import com.example.watsonsrevenge.repository.ClueRepository
import com.example.watsonsrevenge.ui.composables.CluePage
import com.example.watsonsrevenge.ui.composables.PermissionsPage
import com.example.watsonsrevenge.ui.composables.StartPage
import com.example.watsonsrevenge.ui.composables.ClueSolvedPage
import com.example.watsonsrevenge.ui.composables.TreasureHuntCompletedPage
import com.example.watsonsrevenge.ui.theme.WatsonsRevengeTheme
import org.json.JSONArray

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!ClueRepository.areCluesLoaded()) {
            val jsonString = loadJsonFromRaw(applicationContext, R.raw.clues)
            val cluesList = parseCluesFromJson(jsonString)
            ClueRepository.populateClues(cluesList)
        }
        val mainViewModel: MainViewModel by viewModels()
        setContent {
            WatsonsRevengeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent(viewModel = mainViewModel)
                }
            }
        }
    }
}

@Composable
fun AppContent(viewModel: MainViewModel) {
    val currentScreen by viewModel.currentScreen.observeAsState(initial = Screen.PermissionsPage)
    when (currentScreen) {
        Screen.PermissionsPage -> PermissionsPage(viewModel)
        Screen.StartPage -> StartPage(viewModel)
        Screen.CluePage -> CluePage(viewModel)
        Screen.ClueSolvedPage -> ClueSolvedPage(viewModel)
        Screen.TreasureHuntCompletedPage -> TreasureHuntCompletedPage(viewModel)
    }
}

fun loadJsonFromRaw(context: Context, resourceId: Int): String {
    return context.resources.openRawResource(resourceId).bufferedReader().use { it.readText() }
}

fun parseCluesFromJson(jsonString: String): List<Clue> {
    val cluesList = mutableListOf<Clue>()
    val jsonArray = JSONArray(jsonString)

    for (i in 0 until jsonArray.length()) {
        val jsonObject = jsonArray.getJSONObject(i)
        val id = jsonObject.getInt("id")
        val name = jsonObject.getString("name")
        val description = jsonObject.getString("description")
        val hint = jsonObject.getString("hint")
        val info = jsonObject.getString("info")
        val latitude = jsonObject.getDouble("latitude")
        val longitude = jsonObject.getDouble("longitude")
        cluesList.add(Clue(id, name, description, hint, info, latitude, longitude))
    }

    return cluesList
}

