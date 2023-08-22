package com.example.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.ViewModel.Weatherviewmodel
import com.example.weatherapp.data.models.Item
import com.example.weatherapp.databinding.ActivityMainBinding

class MyAdapter (private val data: List<Item>, private val vm: Weatherviewmodel, private val bind: ActivityMainBinding) : RecyclerView.Adapter<MyAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //To Create View each time//
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemview,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = data[position].address.label
        holder.text.setOnClickListener {
            vm.getCurrentWeather(data[position].address.city)
            bind.recyclerview.visibility= View.GONE
        }

    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val text: TextView = itemView.findViewById(R.id.itemviewtext)

    }



}