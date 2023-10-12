package com.jkl.giphytestapp.giphy.presentation

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jkl.giphytestapp.giphy.domain.GiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GiphyListViewModel {

    fun setupTagsAdapter(autoCompleteTextView: AutoCompleteTextView)
    fun setupGiftAdapter(giftRecyclerView: RecyclerView)

    fun searchTags(query: String)
    fun searchGifs(query: String, offset: Int = 0, clear: Boolean)

    fun observeBundle(owner: LifecycleOwner, observer: Observer<Bundle>)

    @HiltViewModel
    class Base @Inject constructor(
        private val giphyRepository: GiphyRepository
    ) : ViewModel(), GiphyListViewModel {

        private lateinit var arrayAdapter: ArrayAdapter<String>

        private val bundleLiveData = MutableLiveData<Bundle>()

        private val gifItemAdapter = GifItemAdapter.Base()

        private lateinit var paginationScrollListener: GiphyPaginationScrollListener
        private var query: String = ""

        override fun setupTagsAdapter(autoCompleteTextView: AutoCompleteTextView) {
            arrayAdapter = ArrayAdapter(
                autoCompleteTextView.context,
                android.R.layout.simple_list_item_1,
                mutableListOf()
            )
            autoCompleteTextView.setAdapter(arrayAdapter)
        }

        override fun setupGiftAdapter(giftRecyclerView: RecyclerView) {
            val layoutManager = GridLayoutManager(giftRecyclerView.context, 2)
            giftRecyclerView.layoutManager = layoutManager
            giftRecyclerView.adapter = gifItemAdapter
            paginationScrollListener = GiphyPaginationScrollListener(layoutManager) {
                paginationScrollListener.setLoading(true)
                searchGifs(query, it, false)
            }
            giftRecyclerView.addOnScrollListener(paginationScrollListener)
        }

        override fun searchTags(query: String) {
            viewModelScope.launch(Dispatchers.IO) {
                val tags = giphyRepository.searchTags(query)
                val list = tags.map { it.name }
                withContext(Dispatchers.Main) {
                    arrayAdapter.clear()
                    arrayAdapter.addAll(list)
                    arrayAdapter.notifyDataSetChanged()
                }
            }
        }

        override fun searchGifs(query: String, offset: Int, clear: Boolean) {
            viewModelScope.launch(Dispatchers.IO) {
                this@Base.query = query
                val gifs = giphyRepository.searchGifs(query, offset)
                withContext(Dispatchers.Main) {
                    if (clear) gifItemAdapter.clearGifs()
                    //  I added this delay(100) because the adapter does not have time to clear
                    //  the list of gifs and load a new one, and a bug occurs when the
                    //  previous gifs remain. If you have a solution to this problem,
                    //  I would be happy to hear your version of the solution.
                    delay(100)
                    gifItemAdapter.addGifs(gifs)
                    paginationScrollListener.setLoading(false)
                }
            }
        }

        override fun observeBundle(owner: LifecycleOwner, observer: Observer<Bundle>) =
            bundleLiveData.observe(owner, observer)
    }
}