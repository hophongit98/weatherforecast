package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.WeatherApplication
import com.example.weatherapp.model.WeatherForecast
import com.example.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    companion object {
        private val TAG = WeatherViewModel::class.java.simpleName
    }

    private val mRepository = WeatherRepository.getInstance(WeatherApplication.instance)
    private val mUiLiveData: MutableLiveData<UiWrapper> = MutableLiveData()
    private var mLatestWeatherLiveData: LiveData<WeatherForecast?> = MutableLiveData()

    fun getUiSate() = mUiLiveData

    init {
        mLatestWeatherLiveData = getLatestForecastLive()
        mLatestWeatherLiveData.observeForever {
            if (it == null) {
                fetchData()
            } else {
                emitUiData(it, false)
            }
        }
    }

    fun fetchData(days: Int = 7, location: String = "saigon") {
        Log.d(TAG, "fetchData")

        emitUiData(isShowLoading = true)
        viewModelScope.launch {
            val result = mRepository.fetchData(days, location)
            if (result is WeatherRepository.ResponseFail) {
                emitUiData(isFetchDataSuccess = false, errorCode = result.errorCode, errroMsg = result.errorMsg)
            }
        }
        Log.d(TAG, "fetchData")
    }

    private fun getLatestForecastLive(): LiveData<WeatherForecast?> {
        Log.d(TAG, "getLatestForecastLive")
        return mRepository.getLatestForecastLive()
    }

    private fun emitUiData(data: WeatherForecast? = null, isShowLoading: Boolean? = null,
                           isFetchDataSuccess: Boolean? = null, errorCode: Int? = null, errroMsg: String? = null) {
        val uiWrapper = UiWrapper(data, isShowLoading, isFetchDataSuccess, errorCode, errroMsg)
        mUiLiveData.postValue(uiWrapper)
    }

    data class UiWrapper(
        val data: WeatherForecast?,
        val isShowLoading: Boolean?,
        val isFetchDataSuccess: Boolean?,
        val errorCode: Int?,
        val errroMsg: String?
    )
}