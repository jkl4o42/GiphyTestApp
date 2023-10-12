package com.jkl.giphytestapp.giphy.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jkl.giphytestapp.R
import com.jkl.giphytestapp.giphy.data.cloud.model.Gif

interface GifItemAdapter {
    fun addGifs(newGifs: List<Gif>)
    fun clearGifs()

    class Base : GifItemAdapter, ListAdapter<Gif, Base.GifViewHolder>(GifDiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.gift_item, parent, false)
            return GifViewHolder(view)
        }

        override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
            holder.loadGif(getItem(position).images.original.url)

        }

        override fun addGifs(newGifs: List<Gif>) {
            val currentList = currentList.toMutableList()
            currentList.addAll(newGifs)
            submitList(currentList)
        }

        override fun clearGifs() {
            submitList(emptyList())
        }

        class GifViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private val gifImageView: ImageView = view.findViewById(R.id.gifImageView)

            fun loadGif(url: String) {
                Glide.with(gifImageView.context).asGif().load(url).into(gifImageView)
                gifImageView.setOnClickListener {
                    it.findFragment<Fragment>().parentFragmentManager.commit {
                        add(R.id.mainContainer, GiphyDetailFragment().apply {
                            arguments = Bundle().apply {
                                putString("url", url)
                            }
                        })
                        addToBackStack(null)
                    }
                }

            }
        }

        class GifDiffCallback : DiffUtil.ItemCallback<Gif>() {
            override fun areItemsTheSame(oldItem: Gif, newItem: Gif): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Gif, newItem: Gif): Boolean {
                return oldItem == newItem
            }
        }
    }
}