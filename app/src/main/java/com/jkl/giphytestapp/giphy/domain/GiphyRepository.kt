package com.jkl.giphytestapp.giphy.domain

import com.jkl.giphytestapp.giphy.data.cloud.model.Gif
import com.jkl.giphytestapp.giphy.data.cloud.model.Tag

interface GiphyRepository {
    suspend fun searchGifs(query: String, offset: Int): List<Gif>
    suspend fun searchTags(query: String): List<Tag>
}