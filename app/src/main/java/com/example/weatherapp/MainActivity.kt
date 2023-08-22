package com.example.weatherapp
import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.ViewModel.Weatherviewmodel
import com.example.weatherapp.data.models.CurrentWeather
import com.example.weatherapp.data.models.Item
import com.example.weatherapp.databinding.ActivityMainBinding

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity :AppCompatActivity(){
private lateinit var binding: ActivityMainBinding
private var city: String = "berlin"
private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
private lateinit var viewModel:Weatherviewmodel
private lateinit var textView: TextView


override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)


    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    viewModel = ViewModelProvider(this).get(Weatherviewmodel::class.java)



    binding.search.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String?): Boolean {

            if (query!= null){
                city = query
            }
            viewModel.getCurrentWeather(city)
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            Log.d("check",newText)
            viewModel.getsuggestions(newText)
            return true
        }

    })


    viewModel.getCurrentWeather(city)



    binding.location.setOnClickListener {
        fetchLocation()
    }

    viewModel.data.observe(this, Observer {
        renderUI(it)
    })

    viewModel.data1.observe(this, Observer {
        renderUI2(it)
    })
}


private fun renderUI2(data:List<Item>){
    binding.recyclerview.layoutManager = LinearLayoutManager(this)
    val adapter = MyAdapter(data,viewModel,binding)
    binding.recyclerview.visibility= View.VISIBLE
    binding.recyclerview.adapter = adapter
}



private fun renderUI(data: CurrentWeather){
    val iconId = data.weather[0].icon
    val imgUrl = "https://openweathermap.org/img/wn/$iconId@4x.png"





    binding.apply {

        location.text = "${data.name}\n${data.sys.country}"
        degree.text = "${data.main.temp.toInt()}°C"

        mintemp.text = "Min temp: ${data.main.temp_min.toInt()}°C"
        maxtemp.text = "Max temp: ${data.main.temp_max.toInt()}°C"

       updated.text = "Last Update: ${
            dateFormatConverter(
                data.dt.toLong()
            )
        }"


    }
}


private fun fetchLocation() {
    val task: Task<Location> = fusedLocationProviderClient.lastLocation


    if (ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),101
        )
        return
    }

    task.addOnSuccessListener {
        val geocoder= Geocoder(this, Locale.getDefault())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            geocoder.getFromLocation(it.latitude,it.longitude,1, object: Geocoder.GeocodeListener{
                override fun onGeocode(addresses: MutableList<Address>) {
                    city = addresses[0].locality
                }

            })
        }else{
            val address = geocoder.getFromLocation(it.latitude,it.longitude,1) as List<Address>

            city = address[0].locality
        }

        viewModel.getCurrentWeather(city)
    }
}




}
private fun dateFormatConverter(date: Long): String {

    return SimpleDateFormat(
        "hh:mm a",
        Locale.ENGLISH
    ).format(Date(date * 1000))
}

