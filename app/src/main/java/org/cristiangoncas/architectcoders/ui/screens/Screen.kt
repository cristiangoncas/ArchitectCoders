package org.cristiangoncas.architectcoders.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.cristiangoncas.architectcoders.ui.theme.ArchitectCodersTheme

@Composable
fun Screen(content: @Composable () -> Unit) {
    ArchitectCodersTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            content = content
        )
    }
}
