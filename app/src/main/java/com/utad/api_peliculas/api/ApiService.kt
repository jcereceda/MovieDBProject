package com.utad.api_peliculas.api


import com.utad.api_peliculas.model.PeliculasResponse
import com.utad.api_peliculas.model.Generos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("genre/movie/list")
    fun getGenres(
        @Query("api_key") apikey: String = ApiRest.api_key,
        @Query("language") language: String = ApiRest.language
    ): Call<Generos>

    @GET("discover/movie")
    fun getMovieByGenre(
        @Query("with_genres") genres: Int,
        @Query("api_key") apikey: String = ApiRest.api_key,
        @Query("language") language: String = ApiRest.language

    ) : Call<PeliculasResponse>
}