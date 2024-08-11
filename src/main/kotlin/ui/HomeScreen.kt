package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import components.HomeScreenComponent

@Composable
fun HomeScreen(component: HomeScreenComponent){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray.copy(.05f))
    )
    {

    }
}
