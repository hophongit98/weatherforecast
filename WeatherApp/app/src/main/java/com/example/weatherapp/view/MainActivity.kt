package com.example.weatherapp.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.model.DateWeather
import com.example.weatherapp.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var mViewModel : WeatherViewModel
    private lateinit var mBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        mBinding = binding

        Log.d(TAG, "onCreate")
        mViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        btnSeach.setOnClickListener {
            showLoading(true)
            val str = etLocation.text.toString()
            val location = if (str.isEmpty()) "saigon" else str
            mViewModel.fetchData(location = location)
        }

        mBinding.rvWeather.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        observeData()
    }

    private fun observeData() {
        showLoading(true)
        mViewModel.getUiSate().observe(this, { uiWrapper ->
            Log.i(TAG, "observeData - uiWrapper=$uiWrapper")
            uiWrapper.data?.let {
                updateTextViewSearch(it.city)
                loadData(it.dateWeather)
            }

            uiWrapper.isShowLoading?.let { showLoading(it) }

            if (uiWrapper.isFetchDataSuccess == false) {
                showLoading(false)

                handleFailure(uiWrapper.errorCode, uiWrapper.errroMsg)
            }
        })
    }

    private fun updateTextViewSearch(location: String) {
        etLocation.setText(location)
    }

    private fun loadData(dateWeather: MutableList<DateWeather>) {
        val weatherAdapter = WeatherAdapter().apply {
            setData(dateWeather)
        }

        mBinding.rvWeather.apply {
            adapter = weatherAdapter
        }
    }

    private fun showLoading(isShow: Boolean) {
        mBinding.pbLoading.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun handleFailure(errorCode: Int?, errorMsg: String?) {
        if (errorCode == null && errorMsg == null) return
        Toast.makeText(this, "$errorCode - $errorMsg", Toast.LENGTH_SHORT).show()
    }
}