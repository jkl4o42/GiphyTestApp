package com.jkl.giphytestapp.giphy.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.jkl.giphytestapp.R
import com.jkl.giphytestapp.main.presentation.BaseFragment

class GiphyDetailFragment : BaseFragment() {
    override val layoutId: Int = R.layout.fragment_giphy_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString("url")
        val gifImageView = view.findViewById<AppCompatImageView>(R.id.gifImageView)
        Glide.with(gifImageView.context).asGif().load(url).into(gifImageView)
    }
}