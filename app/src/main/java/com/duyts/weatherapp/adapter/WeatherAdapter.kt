package com.duyts.weatherapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.duyts.weatherapp.R
import com.duyts.weatherapp.model.Weather
import com.duyts.weatherapp.util.Utils


class WeatherAdapter(private val context: Context, private val weathers: List<Weather>) :
    RecyclerView.Adapter<WeatherAdapter.Companion.WeatherViewHolder>() {

    companion object {
        class WeatherViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val dateTextView: TextView = v.findViewById(R.id.dateTextView)
            val pressureTextView: TextView = v.findViewById(R.id.pressureTextView)
            val temperatureTextView: TextView = v.findViewById(R.id.temperatureTextView)

            @SuppressLint("SetTextI18n")
            fun onBindViewHolder(context: Context, item: Weather) {
                dateTextView.text = "Date: " + Utils.secondToDate(item.dt)
                temperatureTextView.text = "Average temperature: " +item.temp?.day.toString()
                pressureTextView.text = "Pressure: " +item.pressure.toString()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.weather_viewholder, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item = weathers[position]
        holder.onBindViewHolder(context, item)
    }

    override fun getItemCount(): Int {
        return weathers.size
    }

}