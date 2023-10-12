package com.jkl.giphytestapp.giphy.data.cloud

import com.jkl.giphytestapp.giphy.data.cloud.model.GifsResponse
import com.jkl.giphytestapp.giphy.data.cloud.model.TagsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyService {

    @GET("gifs/search/tags")
    suspend fun searchTags(
        @Query("api_key") apiKey: String,
        @Query("q") query: String,
        @Query("limit") limit: Int = 5,
        @Query("offset") offset: Int = 0
    ): Response<TagsResponse>

    @GET("gifs/search")
    suspend fun searchGifs(
        @Query("api_key") apiKey: String,
        @Query("q") query: String,
        @Query("limit") limit: Int = 25,
        @Query("offset") offset: Int = 0,
        @Query("rating") rating: String = "g",
        @Query("lang") lang: String = "en",
        @Query("bundle") bundle: String = "messaging_non_clips"
    ): Response<GifsResponse>

}