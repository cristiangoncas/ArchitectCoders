package org.cristiangoncas.architectcoders.ui.screens.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.cristiangoncas.architectcoders.data.model.Movie

@Composable
fun MovieItem(movie: Movie, onClick: (movieId: Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        onClick = { onClick(movie.id) }) {
        Box {
            AsyncImage(
                model = movie.poster,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .background(shape = RoundedCornerShape(8.dp), color = Color.Transparent),
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                    )
                    .align(Alignment.BottomCenter)
                    .padding(8.dp)
                    .basicMarquee(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                text = movie.title
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 300,
    heightDp = 400
)
@Composable
fun MovieItemPreview() {
    val movie = Movie(
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
    )
    MovieItem(movie) {}
}
