package com.jkl.giphytestapp.giphy.domain

import com.jkl.giphytestapp.giphy.data.cloud.model.Gif
import com.jkl.giphytestapp.giphy.data.cloud.model.Tag
import javax.inject.Inject

interface GiphyInteractor {
    suspend fun searchGifs(query: String, offset: Int): List<Gif>
    suspend fun searchTags(query: String): List<Tag>

    class Base @Inject constructor(
        private val repository: GiphyRepository
    ): GiphyInteractor {
        override suspend fun searchGifs(query: String, offset: Int): List<Gif> = repository.searchGifs(query, offset)

        override suspend fun searchTags(query: String): List<Tag> = repository.searchTags(query)

    }
}