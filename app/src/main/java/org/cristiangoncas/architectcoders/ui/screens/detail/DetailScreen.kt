package org.cristiangoncas.architectcoders.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.cristiangoncas.architectcoders.data.model.Movie
import org.cristiangoncas.architectcoders.ui.screens.Screen
import org.cristiangoncas.architectcoders.ui.screens.shared.ArrowBackIcon
import org.cristiangoncas.architectcoders.ui.screens.shared.Loading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(viewModel: DetailViewModel, movieId: Int?, onBack: () -> Unit) {
    Screen {
        val state by viewModel.state.collectAsState()
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        if (movieId == null) return@Screen
        viewModel.fetchMovieById(movieId)
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = { ArrowBackIcon(onBack = onBack) },
                    title = {
                        Text(
                            text = state.movie?.title ?: ""
                        )
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets.safeDrawing
        ) { innerPadding ->
            if (state.loading) {
                Loading(Modifier.padding(innerPadding))
            } else {
                DetailScreenContent(Modifier.padding(innerPadding), state)
            }
        }
    }
}

@Preview
@Composable
fun DetailScreenContentPreview() {
    val state = UiState()
    state.copy(movie = Movie(
        0,
        "Title 1",
        "",
        "",
        "",
        "",
        "",
        "",
        0.0,
        0.0,
        false
    ))
    DetailScreenContent(Modifier.padding(8.dp), state)
}

@Composable
fun DetailScreenContent(modifier: Modifier = Modifier, state: UiState) {
    Column(
        modifier = modifier
    ) {
        AsyncImage(
            model = state.movie?.poster,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
            text = state.movie?.overview ?: ""
        )
    }
}
