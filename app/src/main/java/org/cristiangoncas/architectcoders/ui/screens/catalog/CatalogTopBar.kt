package org.cristiangoncas.architectcoders.ui.screens.catalog

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogTopBar(scrollBehavior: TopAppBarScrollBehavior, region: String) {

    TopAppBar(
        title = {
            Text(
                text = "Movies",
            )
        },
        actions = {
            Text(
                modifier = Modifier
                    .padding(end = 16.dp),
                text = region
            )
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CatalogTopBarPreview() {
    CatalogTopBar(scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(), "US")
}
