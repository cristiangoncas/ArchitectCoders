package org.cristiangoncas.architectcoders.ui.screens.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.cristiangoncas.architectcoders.data.error.NetworkError
import org.cristiangoncas.architectcoders.data.model.Movie
import org.cristiangoncas.architectcoders.data.repository.remote.ApiClient
import org.cristiangoncas.architectcoders.data.repository.remote.Repository
import org.cristiangoncas.architectcoders.data.repository.remote.RepositoryImpl

class CatalogViewModel : ViewModel() {

    private val repository: Repository = RepositoryImpl(ApiClient())
    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()
    private var currentPage = 0
    private var totalPages: Int = 0

    fun onUiReady() {
        viewModelScope.launch {
            fetchMoreMovies()
        }
    }

    fun fetchMoreMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { currentState ->
                try {
                    val paginatedRemoteMoviesList = if (currentPage == 0) {
                        repository.getMovies()
                    } else {
                        repository.getMoviesByPage(currentPage + 1)
                    }
                    totalPages = paginatedRemoteMoviesList.totalPages
                    currentPage = paginatedRemoteMoviesList.page

                    val newMovies = paginatedRemoteMoviesList.results.map { remoteMovie ->
                        Movie.movieFromRemoteMovie(remoteMovie)
                    }
                    currentState
                        .copy(movies = (currentState.movies + newMovies).distinctBy { it.id })
                        .copy(loading = false)

                } catch (e: NetworkError) {
                    currentState
                        .copy(errors = e.message)
                        .copy(loading = false)
                }
            }
        }

    }
}

data class UiState(
    val movies: List<Movie> = listOf(),
    val errors: String? = null,
    val loading: Boolean = true,
    val location: String = "US",
)
