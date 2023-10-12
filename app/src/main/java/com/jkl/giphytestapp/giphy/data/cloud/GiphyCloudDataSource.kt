package com.jkl.giphytestapp.giphy.data.cloud

import com.jkl.giphytestapp.BuildConfig
import com.jkl.giphytestapp.giphy.data.cloud.model.Gif
import com.jkl.giphytestapp.giphy.data.cloud.model.Tag
import javax.inject.Inject

interface GiphyCloudDataSource {

    suspend fun searchTags(query: String): List<Tag>
    suspend fun searchGifs(query: String, offset: Int): List<Gif>

    class Base @Inject constructor(
        private val giphyService: GiphyService
    ) : GiphyCloudDataSource {

        override suspend fun searchTags(query: String): List<Tag> {
            val response = giphyService.searchTags(apiKey = BuildConfig.API_KEY, query = query)
            if (response.isSuccessful) {
                response.body()?.let { tagsResponse ->
                    return tagsResponse.data
                } ?: return emptyList()
            } else return emptyList()
        }

        override suspend fun searchGifs(query: String, offset: Int): List<Gif> {
            val response = giphyService.searchGifs(
                apiKey = BuildConfig.API_KEY,
                query = query,
                offset = offset
            )
            if (response.isSuccessful) {
                response.body()?.let { gifsResponse ->
                    return gifsResponse.data
                } ?: return emptyList()
            } else return emptyList()
        }
    }
}