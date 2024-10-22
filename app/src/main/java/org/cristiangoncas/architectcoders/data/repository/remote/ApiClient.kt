package org.cristiangoncas.architectcoders.data.repository.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath
import kotlinx.serialization.json.Json
import org.cristiangoncas.architectcoders.data.error.NetworkError
import org.cristiangoncas.architectcoders.data.model.PaginatedRemoteMoviesList
import org.cristiangoncas.architectcoders.data.model.RemoteMovie
import org.cristiangoncas.architectcoders.BuildConfig

class ApiClient {

    private val apiUrl: String = "api.themoviedb.org/3"
    private val client: HttpClient by lazy {
        HttpClient(Android) {
            defaultRequest {
                url {
                    this.host = apiUrl
                }
            }
        }
    }

    @Throws(NetworkError::class)
    suspend fun getMovies(): PaginatedRemoteMoviesList {
        val response = client.get {
            url {
                protocol = URLProtocol.HTTPS
                this.host = apiUrl
                encodedPath = "discover/movie"
                parameters.append("api_key", BuildConfig.TMDB_API_KEY)
                parameters.append("sort_by", "popularity.desc")
            }
        }

        return validateResponse(response)
    }

    @Throws(NetworkError::class)
    suspend fun getMoviesByPage(page: Int): PaginatedRemoteMoviesList {
        val response = client.get {
            url {
                protocol = URLProtocol.HTTPS
                this.host = apiUrl
                encodedPath = "discover/movie"
                parameters.append("api_key", BuildConfig.TMDB_API_KEY)
                parameters.append("sort_by", "popularity.desc")
                parameters.append("page", "$page")
            }
        }

        return validateResponse(response)
    }

    @Throws(NetworkError::class)
    suspend fun getMovieById(id: Int): RemoteMovie {
        val response = client.get {
            url {
                protocol = URLProtocol.HTTPS
                this.host = apiUrl
                encodedPath = "movie/$id"
                parameters.append("api_key", BuildConfig.TMDB_API_KEY)
                parameters.append("sort_by", "popularity.desc")
            }
        }
        return validateResponse(response)
    }

    @Throws(NetworkError::class)
    private suspend inline fun <reified T> validateResponse(response: HttpResponse): T {
        val json = Json {
            ignoreUnknownKeys = true
        }
        if (response.status.value in 200..299) {
            return json.decodeFromString<T>(response.body())
        } else {
            throw NetworkError(
                code = response.status.value,
                message = "Get exercises failed. Message: ${response.body<String>()}"
            )
        }
    }

}
