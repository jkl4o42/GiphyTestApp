package com.jkl.giphytestapp.giphy.presentation

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.jkl.giphytestapp.R
import com.jkl.giphytestapp.main.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GiphyListFragment : BaseFragment() {
    override val layoutId: Int = R.layout.fragment_giphy_list

    private val viewModel: GiphyListViewModel.Base by viewModels()

    private lateinit var textInputLayout: TextInputLayout
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private lateinit var gifRecyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textInputLayout = view.findViewById(R.id.textInputLayout)
        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView)
        gifRecyclerView = view.findViewById(R.id.gifRecyclerView)

        autoCompleteTextView.threshold = 1
        autoCompleteTextView.dropDownHeight = ViewGroup.LayoutParams.WRAP_CONTENT

        viewModel.setupTagsAdapter(autoCompleteTextView)
        viewModel.setupGiftAdapter(gifRecyclerView)

        autoCompleteTextView.doAfterTextChanged {
            it?.let { editable ->
                viewModel.searchTags(editable.toString())
                viewModel.searchGifs(editable.toString(), 0, true)
            }
        }

        viewModel.observeBundle(viewLifecycleOwner) {
        }
    }
}