@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.m3ikshizuka.worldnews.common

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object Time {
    @SuppressLint("SimpleDateFormat")
    fun convertTimeFormat(date: String, outputFormat: String = "HH:mm dd-MMM-yyyy", inputFormat: String = "yyyy-MM-dd'T'HH:mm:ss'Z'"): String {
        val inputFormatData = SimpleDateFormat(inputFormat)
        val outputFormatData = SimpleDateFormat(outputFormat)
        val dateData: Date = inputFormatData.parse(date)
        val formattedDate: String = outputFormatData.format(dateData)
        return formattedDate
    }
}