package org.cristiangoncas.architectcoders.data.repository.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.cristiangoncas.architectcoders.data.model.PaginatedRemoteMoviesList
import org.cristiangoncas.architectcoders.data.model.RemoteMovie

interface Repository {

    suspend fun getMovies(): PaginatedRemoteMoviesList

    suspend fun getMoviesByPage(page: Int): PaginatedRemoteMoviesList

    suspend fun getMovieById(id: Int): RemoteMovie
}

class RepositoryImpl(
    private val apiClient: ApiClient
) : Repository {

    override suspend fun getMovies(): PaginatedRemoteMoviesList = withContext(Dispatchers.IO) {
        apiClient.getMovies()
    }

    override suspend fun getMoviesByPage(page: Int): PaginatedRemoteMoviesList =withContext(Dispatchers.IO) {
        apiClient.getMoviesByPage(page)
    }

    override suspend fun getMovieById(id: Int): RemoteMovie = withContext(Dispatchers.IO) {
        apiClient.getMovieById(id)
    }
}
