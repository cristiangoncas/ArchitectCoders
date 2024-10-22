package org.cristiangoncas.architectcoders.ui.screens.catalog

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.cristiangoncas.architectcoders.extensions.DEFAULT_REGION
import org.cristiangoncas.architectcoders.extensions.getRegion
import org.cristiangoncas.architectcoders.ui.common.PermissionRequestEffect
import org.cristiangoncas.architectcoders.ui.screens.Screen
import org.cristiangoncas.architectcoders.ui.screens.shared.Loading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    viewModel: CatalogViewModel,
    onClick: (movieId: Int) -> Unit
) {
    val ctx = LocalContext.current.applicationContext
    val coroutineScope = rememberCoroutineScope()
    var region by remember { mutableStateOf(DEFAULT_REGION) }

    PermissionRequestEffect(permission = Manifest.permission.ACCESS_COARSE_LOCATION) { granted ->
        if (granted) {
            coroutineScope.launch {
                region = ctx.getRegion()
            }
        }
    }

    Screen {
        val state by viewModel.state.collectAsState()

        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        Scaffold(
            topBar = {
                CatalogTopBar(scrollBehavior = scrollBehavior, region = region)
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets.safeDrawing
        ) { innerPadding ->
            if (state.loading) {
                Loading(Modifier.padding(innerPadding))
            } else {
                val gridState = rememberLazyGridState()
                LaunchedEffect(gridState) {
                    snapshotFlow { gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                        .collect { index ->
                            if (index == state.movies.size - 1) {
                                viewModel.fetchMoreMovies()
                            }
                        }
                }
                CatalogScreenContent(innerPadding, state, onClick, gridState)
            }
        }
    }
    viewModel.onUiReady()
}

@Composable
fun CatalogScreenContent(
    padding: PaddingValues,
    state: UiState,
    onClick: (movieId: Int) -> Unit,
    gridState: LazyGridState
) {
    LazyVerticalGrid(
        modifier = Modifier
            .padding(horizontal = 8.dp),
        columns = GridCells.Adaptive(180.dp),
        contentPadding = padding,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = gridState
    ) {
        items(state.movies.size) { index ->
            val movie = state.movies[index]
            MovieItem(movie = movie, onClick = onClick)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun CatalogScreenPreview() {
    Screen {
        Scaffold { _ ->
            CatalogScreen(CatalogViewModel(), {})
        }
    }
}
