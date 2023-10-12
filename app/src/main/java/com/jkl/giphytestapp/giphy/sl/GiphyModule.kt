package com.jkl.giphytestapp.giphy.sl

import com.jkl.giphytestapp.giphy.data.BaseGiphyRepository
import com.jkl.giphytestapp.giphy.data.cloud.GiphyCloudDataSource
import com.jkl.giphytestapp.giphy.data.cloud.GiphyService
import com.jkl.giphytestapp.giphy.domain.GiphyInteractor
import com.jkl.giphytestapp.giphy.domain.GiphyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class GiphyModule {

    @Provides
    fun provideGiphyInteractor(giphyRepository: GiphyRepository): GiphyInteractor =
        GiphyInteractor.Base(giphyRepository)

    @Provides
    fun provideGiphyRepository(giphyCloudDataSource: GiphyCloudDataSource): GiphyRepository =
        BaseGiphyRepository(giphyCloudDataSource)

    @Provides
    fun provideGiphyCloudDataSource(giphyService: GiphyService): GiphyCloudDataSource =
        GiphyCloudDataSource.Base(giphyService)
}