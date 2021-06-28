package com.duyts.weatherapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.TypedValue
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

    private var textSize = 14F

    companion object {
        class WeatherViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val dateTextView: TextView = v.findViewById(R.id.dateTextView)
            val pressureTextView: TextView = v.findViewById(R.id.pressureTextView)
            val temperatureTextView: TextView = v.findViewById(R.id.temperatureTextView)

            @SuppressLint("SetTextI18n")
            fun onBind(context: Context, item: Weather) {
                dateTextView.text = "Date: " + Utils.secondToDate(item.dt)
                temperatureTextView.text = "Average temperature: " + item.temp?.day.toString()
                pressureTextView.text = "Pressure: " + item.pressure.toString()
            }

            fun onConfigure(textSize: Float) {
                dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
                temperatureTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
                pressureTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
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
        holder.onBind(context, item)
        holder.onConfigure(textSize)
    }

    override fun getItemCount(): Int {
        return weathers.size
    }


    fun setTextSize(textSize: Float) {
        this.textSize = textSize
        notifyDataSetChanged()
    }
}