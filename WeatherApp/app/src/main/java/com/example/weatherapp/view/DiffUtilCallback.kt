package com.example.weatherapp.view

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.example.weatherapp.model.DateWeather

class DiffUtilCallback(newList: ArrayList<DateWeather>, oldList: ArrayList<DateWeather>) : DiffUtil.Callback() {
    private val mNewList = arrayListOf<DateWeather>()
    private val mOldList = arrayListOf<DateWeather>()

    init {
        mNewList.clear()
        mNewList.addAll(newList)
        mOldList.clear()
        mOldList.addAll(oldList)
    }

    override fun getOldListSize(): Int {
        return mOldList.size
    }

    override fun getNewListSize(): Int {
        return mNewList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mNewList[newItemPosition].id == mOldList[oldItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mNewList[newItemPosition] === mOldList[oldItemPosition]
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}