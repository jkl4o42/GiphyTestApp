package com.jkl.giphytestapp.giphy.data

import com.jkl.giphytestapp.giphy.data.cloud.GiphyCloudDataSource
import com.jkl.giphytestapp.giphy.data.cloud.model.Gif
import com.jkl.giphytestapp.giphy.data.cloud.model.Tag
import com.jkl.giphytestapp.giphy.domain.GiphyRepository
import javax.inject.Inject

class BaseGiphyRepository @Inject constructor(
    private val cloudDataSource: GiphyCloudDataSource
) : GiphyRepository {
    override suspend fun searchGifs(query: String, offset: Int): List<Gif> = cloudDataSource.searchGifs(query, offset)
    override suspend fun searchTags(query: String): List<Tag> = cloudDataSource.searchTags(query)
}