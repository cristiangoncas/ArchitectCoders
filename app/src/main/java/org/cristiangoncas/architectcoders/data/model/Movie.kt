package org.cristiangoncas.architectcoders.data.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val poster: String,
    val backdrop: String?,
    val originalTitle: String,
    val originalLanguage: String,
    val popularity: Double,
    val voteAverage: Double,
    val isFavorite: Boolean
) {
    companion object {
        fun movieFromRemoteMovie(remoteMovie: RemoteMovie): Movie {
            return Movie(
                remoteMovie.id,
                remoteMovie.title,
                remoteMovie.overview,
                remoteMovie.releaseDate,
                "https://image.tmdb.org/t/p/w185/${remoteMovie.posterPath}",
                remoteMovie.backdropPath,
                remoteMovie.originalTitle,
                remoteMovie.originalLanguage,
                remoteMovie.popularity,
                remoteMovie.voteAverage,
                false
            )
        }
    }
}
