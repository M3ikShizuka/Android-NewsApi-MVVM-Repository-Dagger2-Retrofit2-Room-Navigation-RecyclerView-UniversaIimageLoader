package com.m3ikshizuka.worldnews.repository

import com.m3ikshizuka.worldnews.ThreadsManager
import com.m3ikshizuka.worldnews.vo.Resource
import com.m3ikshizuka.worldnews.vo.Status

abstract class NetworkBoundResource<ResultType, RequestType> (private val threadsManager: ThreadsManager) {
    fun getDataFromServer() {
        this.threadsManager.execute(object : Runnable {
            override fun run() {
                processDataFromServerAndSaveToStorage()
            }
        })
    }

    fun getDataFromStorage() {
        this.threadsManager.execute(object : Runnable {
            override fun run() {
                processDataFromStorage()
            }
        })
    }

    private fun processDataFromServerAndSaveToStorage() {
        // Set loading state.
        setValue(Resource.loading(null))
        // Get data from DB.
        var resultDataFromStorage = this.fetchDataFromStorage()
        if (resultDataFromStorage.status == Status.SUCCESS) {
            this.setValue(resultDataFromStorage)
        }

        // Request API for get data.
        val resultData =  this.fetchDataFromNetwork()
        if (resultData.status != Status.SUCCESS) {
            if (resultData.data == null && resultDataFromStorage.data != null) {
                // Set old stored data.
                resultData.data = resultDataFromStorage.data
            }

            this.setValue(resultData)
            return
        }

        // Save new data from sever to database.
        this.saveDataToDatabase(resultData)
        resultDataFromStorage = this.fetchDataFromStorage()
        this.setValue(resultDataFromStorage)
    }

    private fun processDataFromStorage() {
        // Set loading state.
        setValue(Resource.loading(null))
        // Get data from DB.
        val resultData = this.fetchDataFromStorage()
        setValue(resultData)
    }

    private fun fetchDataFromNetwork(): Resource<ResultType> {
        val response = this.request()
        if (response.status != Status.SUCCESS) {
            return Resource.error(response.message ?: "[WARNING] fetchDataFromNetwork().status != SUCCESS", null)
        }

        val resultData = this.responseDataHandler(response)
        return resultData
    }

    private fun fetchDataFromStorage(): Resource<ResultType> {
       return this.requestToDatabase()
    }

    private fun setValue(value: Resource<ResultType>) {
        this.setChange(value)
    }

    // Request to server.
    abstract fun request(): Resource<RequestType>
    // Convert RequestType to ResultType.
    abstract fun responseDataHandler(data: Resource<RequestType>): Resource<ResultType>
    // Save data to local storage.
    abstract fun saveDataToDatabase(data: Resource<ResultType>)
    // Get data from local storage.
    abstract fun requestToDatabase(): Resource<ResultType>
    // Set value to ViewModel LiveData.
    abstract fun setChange(value: Resource<ResultType>)
}