package org.cristiangoncas.architectcoders.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.cristiangoncas.architectcoders.data.error.NetworkError
import org.cristiangoncas.architectcoders.data.model.Movie
import org.cristiangoncas.architectcoders.data.repository.remote.ApiClient
import org.cristiangoncas.architectcoders.data.repository.remote.Repository
import org.cristiangoncas.architectcoders.data.repository.remote.RepositoryImpl

class DetailViewModel : ViewModel() {

    private val repository: Repository = RepositoryImpl(ApiClient())
    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    fun fetchMovieById(movieId: Int){
        viewModelScope.launch {
            _state.update { currentState ->
                try {
                    val movie = repository.getMovieById(movieId)
                    currentState
                        .copy(movie = Movie.movieFromRemoteMovie(movie))
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
    val movie: Movie? = null,
    val errors: String? = null,
    val loading: Boolean = true
)
