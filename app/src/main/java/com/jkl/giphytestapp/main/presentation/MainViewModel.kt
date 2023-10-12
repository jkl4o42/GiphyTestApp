package com.jkl.giphytestapp.main.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


interface MainViewModel {
    @HiltViewModel
    class Base @Inject constructor() : ViewModel(), MainViewModel {
    }
}

