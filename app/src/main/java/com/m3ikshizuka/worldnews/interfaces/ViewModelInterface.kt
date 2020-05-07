package com.m3ikshizuka.worldnews.interfaces

import androidx.lifecycle.ViewModel

abstract class ViewModelInterface : ViewModel(){
    abstract val view: ViewInterface
    abstract fun onDestroy()
}