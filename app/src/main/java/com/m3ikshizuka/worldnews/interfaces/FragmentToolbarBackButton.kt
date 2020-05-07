package com.m3ikshizuka.worldnews.interfaces

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity

interface FragmentToolbarBackButton {
    fun getActivity(): Activity?
    fun setHasOptionsMenu(b: Boolean)

    fun enableToolBar() {
        // Turn on toolbar up button
        (this.getActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Turn ob menu option
        this.setHasOptionsMenu(true)
    }

    fun disableToolBar() {
        // Turn off toolbar up button
        (this.getActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        // Turn off menu option
        this.setHasOptionsMenu(false)
    }
}