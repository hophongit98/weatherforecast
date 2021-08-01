package com.example.weatherapp.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ItemRowBinding
import com.example.weatherapp.model.DateWeather
import com.example.weatherapp.model.WeatherForecast
import com.example.weatherapp.utils.StringUtils
import java.util.Date
import kotlin.collections.ArrayList

class WeatherAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private val TAG = WeatherAdapter::class.java.simpleName
    }

    private val mListData = arrayListOf<DateWeather>()
    private var mListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WeatherViewHolder(ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (itemCount> position && position != RecyclerView.NO_POSITION) {
            val weatherHolder = holder as WeatherViewHolder
            val item = mListData[position]
            weatherHolder.build(item)
        }
    }

    override fun getItemCount(): Int {
        return mListData.size
    }

    fun setData(data: List<DateWeather>) {
        Log.i(TAG, "setData - data=$data")

        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(data.toCollection(ArrayList()), mListData))
        diffResult.dispatchUpdatesTo(this)
        mListData.clear()
        mListData.addAll(data)
        notifyDataSetChanged()
    }

    inner class WeatherViewHolder(private val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun build(item: DateWeather) {
            Log.i(TAG, "WeatherViewHolder - build - item=$item")
            binding.tvDate.text = "Date: ${StringUtils.convertEEEDDMMMYYYY(Date(item.datetime * 1000L))}"
            binding.tvHumidity.text = "Humidity: ${item.humidity} %"
            binding.tvPressure.text = "Pressure: ${item.pressure} \u2103"
            binding.tvAvgTemp.text = "Average temperature: ${item.temp.eve}"
            binding.tvDescription.text = "Description: ${item.description}"
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(item: WeatherForecast)
    }

}