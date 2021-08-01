package com.example.weatherapp.utils

abstract class DataResponse<T>

data class Success<T>(val data: T?) : DataResponse<T>()

data class Failure<T>(val errorCode: Int, val errorMsg: String) : DataResponse<T>()

interface Response

interface ResponseValue : Response

interface ResponseError : Response
