package com.jkl.giphytestapp.giphy.presentation

import androidx.recyclerview.widget.LinearLayoutManager

class GiphyPaginationScrollListener(
    layoutManager: LinearLayoutManager,
    private val loadMore: (Int) -> Unit
) : PaginationScrollListener(layoutManager) {

    private var isLoading = false
    private var isLastPage = false

    override fun loadMoreItems() {
        isLoading = true
        loadMore.invoke(currentItemCount())
    }

    fun setLoading(loading: Boolean) {
        isLoading = loading
    }

    fun setLastPage(lastPage: Boolean) {
        isLastPage = lastPage
    }

    override fun isLoading(): Boolean = isLoading
    override fun isLastPage(): Boolean = isLastPage
    override fun visibleThreshold(): Int = 5  // or any other value that suits your needs

    private fun currentItemCount() = layoutManager.itemCount
}
