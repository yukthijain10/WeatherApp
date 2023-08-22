package com.example.weatherapp.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.models.CurrentWeather
import com.example.weatherapp.data.models.Item
import com.example.weatherapp.data.models.RetrofitInstance
import com.example.weatherapp.data.models.Retrofitinstance2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Weatherviewmodel : ViewModel(){
private lateinit var result: CurrentWeather
private lateinit var result1: List<Item>
var data = MutableLiveData<CurrentWeather>()

var data1 = MutableLiveData<List<Item>>()

fun getCurrentWeather(city: String) {
    val apiKey = "6fc51d9293b77491e9874491c6344f52"
    GlobalScope.launch(Dispatchers.IO) {
        try{
            val apiData = RetrofitInstance.api.getCurrentWeather(city,"metric",apiKey)
            apiData.body()?.let {
                result = it
                data.postValue(result)
            }
        }
        catch (e:Exception){
            Log.d("error",e.toString())
        }
    }
}

fun getsuggestions(pattern:String){
    val apiKey1 = "XguHXTZyncj5eNvu2bEJk22lxrV8BrWN0dUJXEFVe_0"
    val endpoint = "autocomplete?q=$pattern&apiKey=$apiKey1"
    GlobalScope.launch(Dispatchers.IO) {
        try{
            val newapidata = Retrofitinstance2.api2.getsuggestion(endpoint)
            newapidata.body()?.items?.let{
                result1 = it
                data1.postValue(result1)
                Log.d("API Response",result1.toString())
            }
        }catch (e:Exception){
            Log.d("error:",e.toString())
        }
    }

}

}